package com.CMS_Project.service;





import com.CMS_Project.dto.request.OrderStatusRequest;
import com.CMS_Project.dto.response.OrderStatusResponse;
import com.CMS_Project.entity.OrderStatuses;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;


import com.CMS_Project.mapper.OrderStatusMapper;
import com.CMS_Project.repository.OrderStatusRepository;
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
public class OrderStatusService {

    OrderStatusRepository orderStatusRepository;
    UserRepository userRepository;
    OrderStatusMapper orderStatusMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public OrderStatusResponse create(OrderStatusRequest orderStatusRequest) {
        OrderStatuses orderStatuses = orderStatusMapper.toOrderStatus(orderStatusRequest);
        orderStatuses.setCreatedAt(LocalDateTime.now());
        orderStatuses.setUpdatedAt(LocalDateTime.now());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        orderStatuses.setCreatedBy(user.getEmail());
        orderStatuses.setUpdatedBy(user.getEmail());
        orderStatusRepository.save(orderStatuses);
        return orderStatusMapper.toOrderStatusResponse(orderStatuses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public OrderStatusResponse update(Integer statusId, OrderStatusRequest orderStatusRequest) {
        OrderStatuses orderStatuses = orderStatusRepository.findById(statusId).orElseThrow(() -> new AppException(ErrorCode.ORDER_STATUS_NOT_EXISTED));
        orderStatusMapper.updateOrderStatus(orderStatuses,orderStatusRequest);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        orderStatuses.setUpdatedBy(user.getEmail());
        orderStatuses.setUpdatedAt(LocalDateTime.now());
        orderStatusRepository.save(orderStatuses);
        return orderStatusMapper.toOrderStatusResponse(orderStatuses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderStatusResponse> getAll() {
        return orderStatusRepository.findAll().stream().map(orderStatusMapper::toOrderStatusResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Integer statusId) {
        orderStatusRepository.deleteById(statusId);
    }
}
