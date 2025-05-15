package com.CMS_Project.service;





import com.CMS_Project.dto.request.PermissionRequest;
import com.CMS_Project.dto.response.PermissionPageResponse;
import com.CMS_Project.dto.response.PermissionResponse;
import com.CMS_Project.entity.Comments;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public PermissionPageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"name");
        if(StringUtils.hasLength(sort)){
            Pattern pattern = Pattern.compile("^(\\w+):(asc|desc)$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sort);
            if(matcher.find()){
                String columnName = matcher.group(1);
                if(matcher.group(2).equalsIgnoreCase("asc")){
                    order = new Sort.Order(Sort.Direction.ASC,columnName);
                }else{
                    order = new Sort.Order(Sort.Direction.DESC,columnName);
                }
            }
        }

        int pageNo = 0;
        if(page > 0){
            pageNo = page - 1;
        }

        Pageable pageable = PageRequest.of(pageNo,size,Sort.by(order));

        Page<Permissions> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = permissionRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = permissionRepository.findAll(pageable);
        }

        List<PermissionResponse> permissionResponseList = entityPage.stream().map(permissionMapper::toPermissionResponse).toList();
        PermissionPageResponse permissionPageResponse = new PermissionPageResponse();
        permissionPageResponse.setPermissions(permissionResponseList);
        permissionPageResponse.setPageNumber(entityPage.getNumber());
        permissionPageResponse.setTotalPages(entityPage.getTotalPages());
        permissionPageResponse.setTotalElements(entityPage.getTotalElements());
        permissionPageResponse.setPageSize(entityPage.getSize());
        return permissionPageResponse;
    }
}
