package com.CMS_Project.controller;


import com.CMS_Project.dto.request.OrderStatusRequest;
import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.OrderStatusResponse;
import com.CMS_Project.service.OrderStatusService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderStatusController {

    OrderStatusService orderStatusService;

    @PostMapping("/add")
    ApiResponse<OrderStatusResponse> create(@RequestBody OrderStatusRequest orderStatusRequest){
        return ApiResponse.<OrderStatusResponse>builder()
                .result(orderStatusService.create(orderStatusRequest))
                .build();
    }

    @PutMapping("/{statusId}")
    ApiResponse<OrderStatusResponse> update(@PathVariable int statusId, @RequestBody OrderStatusRequest orderStatusRequest){
        return ApiResponse.<OrderStatusResponse>builder()
                .result(orderStatusService.update(statusId,orderStatusRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<OrderStatusResponse>> getAll() {
        return ApiResponse.<List<OrderStatusResponse>>builder()
                .result(orderStatusService.getAll())
                .build();
    }

    @DeleteMapping("/{colorId}")
    ApiResponse<Void> delete(@PathVariable Integer colorId) {
        orderStatusService.delete(colorId);
        return ApiResponse.<Void>builder().build();
    }
}
