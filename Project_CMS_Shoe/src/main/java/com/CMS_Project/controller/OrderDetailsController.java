package com.CMS_Project.controller;


import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.OrderDetailsPageResponse;
import com.CMS_Project.dto.response.OrderDetailsResponse;
import com.CMS_Project.service.OrderDetailsService;
import com.CMS_Project.service.impl.OrderDetailsServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderdetails")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderDetailsController {

    OrderDetailsService orderDetailsService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<OrderDetailsResponse>> getAllColor() {
        return ApiResponse.<List<OrderDetailsResponse>>builder()
                .result(orderDetailsService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    ApiResponse<OrderDetailsPageResponse> findAll(@RequestParam(required = false) String keyword,
                                                  @RequestParam(required = false) String sort,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<OrderDetailsPageResponse>builder()
                .result(orderDetailsService.findAll(keyword,sort,page,size))
                .build();
    }
}
