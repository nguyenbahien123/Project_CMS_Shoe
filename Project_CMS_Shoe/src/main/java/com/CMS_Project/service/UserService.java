package com.CMS_Project.service;

import com.CMS_Project.dto.request.UserCreationRequest;
import com.CMS_Project.dto.request.UserUpdateRequest;
import com.CMS_Project.dto.response.UserResponse;
import com.CMS_Project.entity.Roles;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;
import com.CMS_Project.mapper.UserMapper;
import com.CMS_Project.repository.RoleRepository;
import com.CMS_Project.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;

    public UserResponse createUser( UserCreationRequest request) {
        Users user = userMapper.toUser(request);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        Roles role = roleRepository.findById("USER").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        user.setRoles(role);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse updateUser(int userId, UserUpdateRequest request) {
        Users users = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(users, request);
        Roles role = roleRepository.findById(request.getRoles()).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        users.setRoles(role);
        users.setUpdatedAt(LocalDateTime.now());
        userRepository.save(users);
        return userMapper.toUserResponse(users);
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
}
