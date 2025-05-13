package com.CMS_Project.service;





import com.CMS_Project.dto.request.RoleRequest;
import com.CMS_Project.dto.response.RoleResponse;
import com.CMS_Project.entity.Permissions;
import com.CMS_Project.entity.Roles;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;


import com.CMS_Project.mapper.RoleMapper;
import com.CMS_Project.repository.PermissionRepository;
import com.CMS_Project.repository.RoleRepository;
import com.CMS_Project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepository roleRepository;
    UserRepository userRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest roleRequest) {
        Roles role = roleMapper.toRole(roleRequest);
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());
        List<Permissions> permissions = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        Users user = userRepository.findById(1).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        role.setCreatedBy(user.getEmail());
        role.setUpdatedBy(user.getEmail());
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }


    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String roleId) {
        roleRepository.deleteById(roleId);
    }
}
