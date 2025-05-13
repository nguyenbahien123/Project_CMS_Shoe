package com.CMS_Project.controller;


import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.OrderDetailsResponse;
import com.CMS_Project.service.OrderDetailsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderdetails")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderDetailsController {

    OrderDetailsService orderDetailsService;

    @GetMapping
    ApiResponse<List<OrderDetailsResponse>> getAllColor() {
        return ApiResponse.<List<OrderDetailsResponse>>builder()
                .result(orderDetailsService.getAll())
                .build();
    }

}
