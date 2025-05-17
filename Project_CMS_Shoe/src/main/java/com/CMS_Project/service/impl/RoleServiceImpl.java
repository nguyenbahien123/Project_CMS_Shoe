package com.CMS_Project.service.impl;





import com.CMS_Project.dto.request.RoleRequest;
import com.CMS_Project.dto.response.RolePageResponse;
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
import com.CMS_Project.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    UserRepository userRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    @Override
    public RoleResponse create(RoleRequest roleRequest) {
        Roles role = roleMapper.toRole(roleRequest);
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());
        List<Permissions> permissions = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        role.setCreatedBy(user.getEmail());
        role.setUpdatedBy(user.getEmail());
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }


    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @Override
    public void delete(String roleId) {
        roleRepository.deleteById(roleId);
    }


    @Override
    public RolePageResponse findAll(String keyword, String sort, int page, int size) {
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

        Page<Roles> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = roleRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = roleRepository.findAll(pageable);
        }

        List<RoleResponse> roleResponseList = entityPage.stream().map(roleMapper::toRoleResponse).toList();
        RolePageResponse rolePageResponse = new RolePageResponse();
        rolePageResponse.setRoles(roleResponseList);
        rolePageResponse.setPageNumber(entityPage.getNumber());
        rolePageResponse.setPageSize(entityPage.getSize());
        rolePageResponse.setTotalElements(entityPage.getTotalElements());
        rolePageResponse.setTotalPages(entityPage.getTotalPages());
        return rolePageResponse;
    }
}
