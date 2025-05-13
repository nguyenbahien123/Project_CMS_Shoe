package com.CMS_Project.controller;


import com.CMS_Project.dto.request.ColorRequest;
import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.ColorResponse;
import com.CMS_Project.dto.response.OrderResponse;
import com.CMS_Project.service.ColorService;
import com.CMS_Project.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {

    OrderService orderService;

        @GetMapping
        ApiResponse<List<OrderResponse>> getAll() {
            return ApiResponse.<List<OrderResponse>>builder()
                    .result(orderService.getAll())
                    .build();
        }
}
