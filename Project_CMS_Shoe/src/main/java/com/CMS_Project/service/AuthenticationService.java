package com.CMS_Project.service;

import com.CMS_Project.dto.request.AuthenticationRequest;
import com.CMS_Project.dto.request.IntrospectRequest;
import com.CMS_Project.dto.request.LogoutRequest;
import com.CMS_Project.dto.request.RefreshRequest;
import com.CMS_Project.dto.response.AuthenticationResponse;
import com.CMS_Project.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService  {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    void logout(LogoutRequest logoutRequest);
    AuthenticationResponse refreshToken(RefreshRequest refreshRequest) throws ParseException, JOSEException;
    IntrospectResponse introspect(IntrospectRequest introspectRequest);
}
