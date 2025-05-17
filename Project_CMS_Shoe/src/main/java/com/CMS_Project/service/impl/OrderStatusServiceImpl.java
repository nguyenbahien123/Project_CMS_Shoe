package com.CMS_Project.service.impl;



import com.CMS_Project.dto.request.OrderStatusRequest;
import com.CMS_Project.dto.response.OrderStatusPageResponse;
import com.CMS_Project.dto.response.OrderStatusResponse;
import com.CMS_Project.entity.OrderStatuses;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;
import com.CMS_Project.mapper.OrderStatusMapper;
import com.CMS_Project.repository.OrderStatusRepository;
import com.CMS_Project.repository.UserRepository;
import com.CMS_Project.service.OrderStatusService;
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
public class OrderStatusServiceImpl implements OrderStatusService {

    OrderStatusRepository orderStatusRepository;
    UserRepository userRepository;
    OrderStatusMapper orderStatusMapper;

    @Override
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

    @Override
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

    @Override
    public List<OrderStatusResponse> getAll() {
        return orderStatusRepository.findAll().stream().map(orderStatusMapper::toOrderStatusResponse).toList();
    }

    @Override
    public void delete(Integer statusId) {
        orderStatusRepository.deleteById(statusId);
    }

    @Override
    public OrderStatusPageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"statusId");
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

        Page<OrderStatuses> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = orderStatusRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = orderStatusRepository.findAll(pageable);
        }

        List<OrderStatusResponse> orderStatusResponseList = entityPage.stream().map(orderStatusMapper::toOrderStatusResponse).toList();
        OrderStatusPageResponse orderStatusPageResponse = new OrderStatusPageResponse();
        orderStatusPageResponse.setTotalPages(entityPage.getTotalPages());
        orderStatusPageResponse.setPageNumber(entityPage.getNumber());
        orderStatusPageResponse.setPageSize(entityPage.getSize());
        orderStatusPageResponse.setTotalElements(entityPage.getTotalElements());
        orderStatusPageResponse.setOrderStatuses(orderStatusResponseList);
        return orderStatusPageResponse;
    }
}
