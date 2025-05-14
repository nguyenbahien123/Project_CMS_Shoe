package com.CMS_Project.service;

import com.CMS_Project.dto.request.UserCreationRequest;
import com.CMS_Project.dto.request.UserUpdateRequest;
import com.CMS_Project.dto.response.UserPageResponse;
import com.CMS_Project.dto.response.UserResponse;
import com.CMS_Project.entity.Roles;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;
import com.CMS_Project.mapper.UserMapper;
import com.CMS_Project.repository.RoleRepository;
import com.CMS_Project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser( UserCreationRequest request) {
        Users user = userMapper.toUser(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        Roles role = roleRepository.findById("USER").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        user.setRoles(role);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }


    public UserResponse updateUser(int userId, UserUpdateRequest request) {
        Users users = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(users, request);
        users.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));
        Roles role = roleRepository.findById(request.getRoles()).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        users.setRoles(role);
        users.setUpdatedAt(LocalDateTime.now());
        userRepository.save(users);
        return userMapper.toUserResponse(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    public UserPageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"id");
        if(StringUtils.hasLength(sort)){
            Pattern pattern = Pattern.compile("(\\w+?):(:)(.*)");
            Matcher matcher = pattern.matcher(sort);
            if(matcher.find()){
                String columnName = matcher.group(1);
                if(matcher.group(3).equalsIgnoreCase("asc")){
                    order = Sort.Order.asc(columnName);
                }else{
                    order = Sort.Order.desc(columnName);
                }
            }
        }

        int pageNo = 0;
        if(page > 0){
            pageNo = page - 1;
        }

        Pageable pageable = PageRequest.of(pageNo,size,Sort.by(order));

        Page<Users> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = userRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = userRepository.findAll(pageable);
        }

        List<UserResponse> userResponses = entityPage.stream().map(userMapper::toUserResponse).toList();

        UserPageResponse userPageResponse = new UserPageResponse();
        userPageResponse.setPageNumber(entityPage.getNumber());
        userPageResponse.setPageSize(entityPage.getSize());
        userPageResponse.setTotalElements(entityPage.getTotalElements());
        userPageResponse.setTotalPages(entityPage.getTotalPages());
        userPageResponse.setUsers(userResponses);

        return userPageResponse;
    }
}
