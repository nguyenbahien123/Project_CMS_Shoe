package com.CMS_Project.mapper;

import com.CMS_Project.dto.request.OrderStatusRequest;
import com.CMS_Project.dto.response.OrderStatusResponse;
import com.CMS_Project.entity.OrderStatuses;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface OrderStatusMapper {
    OrderStatuses toOrderStatus(OrderStatusRequest orderStatusRequest);

    OrderStatusResponse toOrderStatusResponse(OrderStatuses orderStatuses);

    void updateOrderStatus(@MappingTarget OrderStatuses orderStatuses, OrderStatusRequest orderStatusRequest);
}
