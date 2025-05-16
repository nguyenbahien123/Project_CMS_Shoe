package com.CMS_Project.mapper;

import com.CMS_Project.dto.response.OrderResponse;
import com.CMS_Project.entity.Orders;
import org.mapstruct.Mapper;


@Mapper
public interface OrderMapper {
    OrderResponse toOrderResponse(Orders orders);
}
