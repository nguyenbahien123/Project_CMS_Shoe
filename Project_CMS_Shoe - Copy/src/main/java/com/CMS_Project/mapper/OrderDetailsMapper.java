package com.CMS_Project.mapper;

import com.CMS_Project.dto.response.OrderDetailsResponse;
import com.CMS_Project.dto.response.OrderResponse;
import com.CMS_Project.entity.OrderDetails;
import com.CMS_Project.entity.Orders;
import org.mapstruct.Mapper;

@Mapper
public interface OrderDetailsMapper {
    OrderDetailsResponse toOrderDetailsResponse(OrderDetails orderDetails);
}
