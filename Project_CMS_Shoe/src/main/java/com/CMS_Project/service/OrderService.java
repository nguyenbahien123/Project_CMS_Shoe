package com.CMS_Project.service;

import com.CMS_Project.dto.request.OrderUpdateRequest;
import com.CMS_Project.dto.response.OrderPageResponse;
import com.CMS_Project.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getAll();
    OrderResponse update(Integer orderId, OrderUpdateRequest orderUpdateRequest);
    OrderPageResponse findAll(String keyword, String sort, int page, int size);
}
