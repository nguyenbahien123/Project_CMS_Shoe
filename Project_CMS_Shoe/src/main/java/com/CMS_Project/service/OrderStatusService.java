package com.CMS_Project.service;

import com.CMS_Project.dto.request.OrderStatusRequest;
import com.CMS_Project.dto.response.OrderStatusPageResponse;
import com.CMS_Project.dto.response.OrderStatusResponse;

import java.util.List;

public interface OrderStatusService {
    OrderStatusResponse create(OrderStatusRequest orderStatusRequest);
    OrderStatusResponse update(Integer statusId, OrderStatusRequest orderStatusRequest);
    List<OrderStatusResponse> getAll();
    void delete(Integer statusId);
    OrderStatusPageResponse findAll(String keyword, String sort, int page, int size);
}
