package com.CMS_Project.mapper;

import com.CMS_Project.dto.request.RoleRequest;
import com.CMS_Project.dto.response.RoleResponse;
import com.CMS_Project.entity.Roles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Roles toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Roles role);
}
