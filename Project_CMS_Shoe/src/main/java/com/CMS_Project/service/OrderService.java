package com.CMS_Project.service;


import com.CMS_Project.dto.request.SizeRequest;
import com.CMS_Project.dto.response.OrderResponse;
import com.CMS_Project.dto.response.SizeResponse;
import com.CMS_Project.entity.Sizes;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;
import com.CMS_Project.mapper.OrderMapper;
import com.CMS_Project.mapper.SizeMapper;
import com.CMS_Project.repository.OrderRepository;
import com.CMS_Project.repository.SizeRepository;
import com.CMS_Project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;
    UserRepository userRepository;
    OrderMapper orderMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getAll() {
        return orderRepository.findAll().stream().map(orderMapper::toOrderResponse).toList();
    }

}
