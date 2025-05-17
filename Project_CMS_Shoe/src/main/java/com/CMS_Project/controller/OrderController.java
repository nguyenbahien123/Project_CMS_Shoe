package com.CMS_Project.controller;


import com.CMS_Project.dto.request.OrderUpdateRequest;
import com.CMS_Project.dto.response.*;
import com.CMS_Project.service.OrderService;
import com.CMS_Project.service.impl.OrderServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {

    OrderService orderService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<OrderResponse>> getAll() {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getAll())
                .build();
        }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{orderId}")
    ApiResponse<OrderResponse> update(@PathVariable("orderId") Integer orderId, @RequestBody OrderUpdateRequest orderUpdateRequest) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.update(orderId, orderUpdateRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    ApiResponse<OrderPageResponse> findAll(@RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) String sort,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<OrderPageResponse>builder()
                .result(orderService.findAll(keyword,sort,page,size))
                .build();
    }
}
