package com.CMS_Project.mapper;

import com.CMS_Project.dto.request.PermissionRequest;
import com.CMS_Project.dto.response.OrderResponse;
import com.CMS_Project.dto.response.PermissionResponse;
import com.CMS_Project.entity.Orders;
import com.CMS_Project.entity.Permissions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface OrderMapper {
    OrderResponse toOrderResponse(Orders orders);
}
