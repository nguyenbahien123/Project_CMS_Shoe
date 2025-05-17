package com.CMS_Project.service;

import com.CMS_Project.dto.response.OrderDetailsPageResponse;
import com.CMS_Project.dto.response.OrderDetailsResponse;

import java.util.List;

public interface OrderDetailsService {
    List<OrderDetailsResponse> getAll();
    OrderDetailsPageResponse findAll(String keyword, String sort, int page, int size);
}
