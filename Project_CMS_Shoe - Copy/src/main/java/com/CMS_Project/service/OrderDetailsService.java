package com.CMS_Project.service;


import com.CMS_Project.dto.response.OrderDetailsResponse;
import com.CMS_Project.mapper.OrderDetailsMapper;
import com.CMS_Project.repository.OrderDetailRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailsService {

    OrderDetailRepository orderDetailRepository;
    OrderDetailsMapper orderDetailsMapper;

    public List<OrderDetailsResponse> getAll() {
        return orderDetailRepository.findAll().stream().map(orderDetailsMapper::toOrderDetailsResponse).toList();
    }

}
