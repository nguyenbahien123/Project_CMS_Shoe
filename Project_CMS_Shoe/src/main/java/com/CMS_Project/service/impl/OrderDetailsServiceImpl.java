package com.CMS_Project.service.impl;


import com.CMS_Project.dto.response.OrderDetailsPageResponse;
import com.CMS_Project.dto.response.OrderDetailsResponse;
import com.CMS_Project.entity.OrderDetails;
import com.CMS_Project.mapper.OrderDetailsMapper;
import com.CMS_Project.repository.OrderDetailRepository;
import com.CMS_Project.service.OrderDetailsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailsServiceImpl implements OrderDetailsService {

    OrderDetailRepository orderDetailRepository;
    OrderDetailsMapper orderDetailsMapper;

    public List<OrderDetailsResponse> getAll()  {
        return orderDetailRepository.findAll().stream().map(orderDetailsMapper::toOrderDetailsResponse).toList();
    }

    public OrderDetailsPageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"orderDetailId");
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

        Page<OrderDetails> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = orderDetailRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = orderDetailRepository.findAll(pageable);
        }

        List<OrderDetailsResponse> orderDetailsResponseList = entityPage.stream().map(orderDetailsMapper::toOrderDetailsResponse).toList();
        OrderDetailsPageResponse orderDetailsPageResponse = new OrderDetailsPageResponse();
        orderDetailsPageResponse.setPageNumber(entityPage.getNumber());
        orderDetailsPageResponse.setPageSize(entityPage.getSize());
        orderDetailsPageResponse.setTotalElements(entityPage.getTotalElements());
        orderDetailsPageResponse.setTotalPages(entityPage.getTotalPages());
        orderDetailsPageResponse.setOrderDetails(orderDetailsResponseList);
        return orderDetailsPageResponse;
    }
}
