package com.CMS_Project.service;


import com.CMS_Project.dto.request.OrderUpdateRequest;
import com.CMS_Project.dto.request.SizeRequest;
import com.CMS_Project.dto.response.OrderResponse;
import com.CMS_Project.dto.response.SizeResponse;
import com.CMS_Project.entity.OrderStatuses;
import com.CMS_Project.entity.Orders;
import com.CMS_Project.entity.Sizes;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;
import com.CMS_Project.mapper.OrderMapper;
import com.CMS_Project.mapper.SizeMapper;
import com.CMS_Project.repository.OrderRepository;
import com.CMS_Project.repository.OrderStatusRepository;
import com.CMS_Project.repository.SizeRepository;
import com.CMS_Project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    OrderStatusRepository orderStatusRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getAll() {
        return orderRepository.findAll().stream().map(orderMapper::toOrderResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public OrderResponse update(Integer orderId, OrderUpdateRequest orderUpdateRequest) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(()-> new AppException(ErrorCode.ORDER_NOT_EXISTED));
        String status = orderUpdateRequest.getStatus();
        OrderStatuses statuses = orderStatusRepository.findByStatusName(status).orElseThrow(()-> new AppException(ErrorCode.ORDER_STATUS_NOT_EXISTED));
        orders.setStatus(statuses);
        orders.setUpdatedAt(LocalDateTime.now());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        orders.setUpdatedBy(user.getEmail());
        orderRepository.save(orders);
        return orderMapper.toOrderResponse(orders);
    }
}
