package com.CMS_Project.mapper;

import com.CMS_Project.dto.request.OrderUpdateRequest;
import com.CMS_Project.dto.request.PermissionRequest;
import com.CMS_Project.dto.request.SizeRequest;
import com.CMS_Project.dto.response.OrderResponse;
import com.CMS_Project.dto.response.PermissionResponse;
import com.CMS_Project.entity.Orders;
import com.CMS_Project.entity.Permissions;
import com.CMS_Project.entity.Sizes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface OrderMapper {
    OrderResponse toOrderResponse(Orders orders);
}
