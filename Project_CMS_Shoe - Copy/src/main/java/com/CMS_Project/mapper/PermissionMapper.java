package com.CMS_Project.mapper;

import com.CMS_Project.dto.request.ColorRequest;
import com.CMS_Project.dto.request.PermissionRequest;
import com.CMS_Project.dto.response.PermissionResponse;
import com.CMS_Project.entity.Colors;
import com.CMS_Project.entity.Permissions;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface PermissionMapper {
    Permissions toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permissions permission);

    void updatePermission(@MappingTarget Permissions permissions, PermissionRequest permissionRequest);
}
