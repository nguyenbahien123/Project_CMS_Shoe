package com.CMS_Project.service;

import com.CMS_Project.dto.request.AuthenticationRequest;
import com.CMS_Project.dto.request.IntrospectRequest;
import com.CMS_Project.dto.request.LogoutRequest;
import com.CMS_Project.dto.request.RefreshRequest;
import com.CMS_Project.dto.response.AuthenticationResponse;
import com.CMS_Project.dto.response.IntrospectResponse;
import com.CMS_Project.entity.InvalidatedToken;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;
import com.CMS_Project.repository.InvalidTokenRepository;
import com.CMS_Project.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    InvalidTokenRepository invalidTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        userRepository.existsByEmail(authenticationRequest.getEmail());
        Users user = userRepository.findByEmail(authenticationRequest.getEmail())
                    .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPasswordHash(), user.getPasswordHash());
        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String accesstoken = generateAccessToken(user);
        String refreshtoken = generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accesstoken)
                .refreshToken(refreshtoken)
                .authenticated(true)
                .build();
    }




    private String generateAccessToken(Users user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issueTime(new Date())
                .issuer("nbh.com")
                .expirationTime(new Date(Instant.now().plus(24,ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        }catch (Exception e) {
            log.error("Cannot create access token", e);
            throw new RuntimeException(e);
        }
    }
    private String generateRefreshToken(Users user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issueTime(new Date())
                .issuer("nbh.com")
                .expirationTime(new Date(Instant.now().plus(14,ChronoUnit.DAYS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope","")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        }catch (Exception e) {
            log.error("Cannot create access token", e);
            throw new RuntimeException(e);
        }
    }

    private Object buildScope(Users user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(user.getRoles() != null){
            stringJoiner.add("ROLE_" + user.getRoles().getName());
            if(!CollectionUtils.isEmpty(user.getRoles().getPermissions())){
                user.getRoles().getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            }
        }
        return stringJoiner.toString();
    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) {
        String accessToken = introspectRequest.getAccessToken();
        boolean isValid = true;

        try{
            verifyAccessToken(accessToken);
        }catch (Exception e) {
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }


    public void logout(LogoutRequest logoutRequest) {
        String accessToken  = logoutRequest.getAccessToken();
        String refreshToken  = logoutRequest.getRefreshToken();
        try {
            var signAccessToken = verifyAccessToken(accessToken);
            var signRefreshToken = verifyRefeshToken(refreshToken);

            String jitAccessToken = signAccessToken.getJWTClaimsSet().getJWTID();
            String jitRefreshToken = signRefreshToken.getJWTClaimsSet().getJWTID();

            Date expiryTimeAccessToken = signAccessToken.getJWTClaimsSet().getExpirationTime();
            Date expiryTimeRefreshToken = signRefreshToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidAccessToken = InvalidatedToken.builder()
                    .id(jitAccessToken)
                    .expiryTime(expiryTimeAccessToken)
                    .build();

            invalidTokenRepository.save(invalidAccessToken);

            InvalidatedToken invalidRefreshToken = InvalidatedToken.builder()
                    .id(jitRefreshToken)
                    .expiryTime(expiryTimeRefreshToken)
                    .build();

            invalidTokenRepository.save(invalidRefreshToken);
        }catch (AppException | JOSEException | ParseException exception) {
            log.info("Token already expired");
        }
    }

    public AuthenticationResponse refreshToken(RefreshRequest refreshRequest) throws ParseException, JOSEException {
        var signedJWT = verifyRefeshToken(refreshRequest.getRefreshToken());
        if(new Date(Instant.now().plus(24,ChronoUnit.HOURS).toEpochMilli())
                .after(signedJWT.getJWTClaimsSet().getExpirationTime())){
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        String email = signedJWT.getJWTClaimsSet().getSubject();

        Users user = userRepository.findByEmail(email).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        String accessToken = generateAccessToken(user);

        return  AuthenticationResponse.builder()
                .accessToken(accessToken)
                .authenticated(true)
                .build();
    }

//    public AuthenticationResponse refreshToken(RefreshRequest refreshRequest) throws ParseException, JOSEException {
//        var signedJWT = verifyRefeshToken(refreshRequest.getRefreshToken());
//
//        var jitRefreshToken = signedJWT.getJWTClaimsSet().getJWTID();
//        var expiryTimeRefreshToken = signedJWT.getJWTClaimsSet().getExpirationTime();
//
//        if(signedJWT.getJWTClaimsSet().getClaim("scope").toString().length() > 6 ){
//            throw new AppException(ErrorCode.UNAUTHENTICATED);
//        }
//
//        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
//                .expiryTime(expiryTimeRefreshToken)
//                .id(jitRefreshToken)
//                .build();
//
//        invalidTokenRepository.save(invalidatedToken);
//
//        String email = signedJWT.getJWTClaimsSet().getSubject();
//
//        Users user = userRepository.findByEmail(email).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
//
//        String accessToken = generateAccessToken(user);
//        String refreshToken = generateRefreshToken(user);
//
//        return  AuthenticationResponse.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .authenticated(true)
//                .build();
//    }

    private SignedJWT verifyAccessToken(String accessToken) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(accessToken);

        var verified = signedJWT.verify(verifier);

        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();


        if(signedJWT.getJWTClaimsSet().getClaim("scope").toString().length() < 6  ){
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        if(!verified && expityTime.after(new Date())){
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        if (invalidTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    private SignedJWT verifyRefeshToken(String refreshToken) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(refreshToken);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if(signedJWT.getJWTClaimsSet().getClaim("scope").toString().length() > 6  ){
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        if(!verified && expiryTime.after(new Date())){
            throw  new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (invalidTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

}
