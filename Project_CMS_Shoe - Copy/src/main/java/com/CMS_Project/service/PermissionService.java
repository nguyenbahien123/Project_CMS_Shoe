package com.CMS_Project.service;





import com.CMS_Project.dto.request.PermissionRequest;
import com.CMS_Project.dto.response.PermissionResponse;
import com.CMS_Project.entity.Permissions;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;


import com.CMS_Project.mapper.PermissionMapper;
import com.CMS_Project.repository.PermissionRepository;
import com.CMS_Project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionRepository permissionRepository;
    UserRepository userRepository;
    PermissionMapper permissionMapper;


    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse create(PermissionRequest permissionRequest) {
        Permissions permissions = permissionMapper.toPermission(permissionRequest);
        permissions.setCreatedAt(LocalDateTime.now());
        permissions.setUpdatedAt(LocalDateTime.now());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        permissions.setCreatedBy(user.getEmail());
        permissions.setUpdatedBy(user.getEmail());
        permissionRepository.save(permissions);
        return permissionMapper.toPermissionResponse(permissions);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse update(String permissionId, PermissionRequest permissionRequest) {
        Permissions permissions = permissionRepository.findById(permissionId).orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        permissionMapper.updatePermission(permissions,permissionRequest);
        permissions.setUpdatedAt(LocalDateTime.now());
        Users users = userRepository.findById(1).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        permissions.setUpdatedBy(users.getEmail());
        permissions.setUpdatedAt(LocalDateTime.now());
        permissionRepository.save(permissions);
        return permissionMapper.toPermissionResponse(permissions);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String permissionId) {
        permissionRepository.deleteById(permissionId);
    }
}
