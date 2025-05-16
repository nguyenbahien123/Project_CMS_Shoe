package com.CMS_Project.service;


import com.CMS_Project.dto.request.OrderUpdateRequest;
import com.CMS_Project.dto.response.OrderPageResponse;
import com.CMS_Project.dto.response.OrderResponse;
import com.CMS_Project.entity.*;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;
import com.CMS_Project.mapper.OrderMapper;
import com.CMS_Project.repository.OrderRepository;
import com.CMS_Project.repository.OrderStatusRepository;
import com.CMS_Project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;
    UserRepository userRepository;
    OrderMapper orderMapper;
    OrderStatusRepository orderStatusRepository;

    public List<OrderResponse> getAll() {
        return orderRepository.findAll().stream().map(orderMapper::toOrderResponse).toList();
    }

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

    public OrderPageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"orderId");
        if(StringUtils.hasLength(sort)){
            Pattern pattern = Pattern.compile("^(\\w+):(asc|desc)$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sort);
            if(matcher.find()){
                String columnName = matcher.group(1);
                if(matcher.group(2).equalsIgnoreCase("asc")){
                    order = new Sort.Order(Sort.Direction.ASC,columnName);
                }else{
                    order = new Sort.Order(Sort.Direction.DESC,columnName);
                }
            }
        }

        int pageNo = 0;
        if(page > 0){
            pageNo = page - 1;
        }

        Pageable pageable = PageRequest.of(pageNo,size,Sort.by(order));

        Page<Orders> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = orderRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = orderRepository.findAll(pageable);
        }

        List<OrderResponse> orderResponseList = entityPage.stream().map(orderMapper::toOrderResponse).toList();
        OrderPageResponse orderPageResponse = new OrderPageResponse();
        orderPageResponse.setPageNumber(entityPage.getNumber());
        orderPageResponse.setPageSize(entityPage.getSize());
        orderPageResponse.setTotalPages(entityPage.getTotalPages());
        orderPageResponse.setTotalElements(entityPage.getTotalElements());
        orderPageResponse.setOrders(orderResponseList);
        return orderPageResponse;
    }
}
