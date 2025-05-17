package com.CMS_Project.controller;


import com.CMS_Project.dto.request.OrderStatusRequest;
import com.CMS_Project.dto.response.*;
import com.CMS_Project.service.OrderStatusService;
import com.CMS_Project.service.impl.OrderStatusServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderStatusController {

    OrderStatusService orderStatusService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    ApiResponse<OrderStatusResponse> create(@RequestBody OrderStatusRequest orderStatusRequest){
        return ApiResponse.<OrderStatusResponse>builder()
                .result(orderStatusService.create(orderStatusRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{statusId}")
    ApiResponse<OrderStatusResponse> update(@PathVariable int statusId, @RequestBody OrderStatusRequest orderStatusRequest){
        return ApiResponse.<OrderStatusResponse>builder()
                .result(orderStatusService.update(statusId,orderStatusRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<OrderStatusResponse>> getAll() {
        return ApiResponse.<List<OrderStatusResponse>>builder()
                .result(orderStatusService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{colorId}")
    ApiResponse<Void> delete(@PathVariable Integer colorId) {
        orderStatusService.delete(colorId);
        return ApiResponse.<Void>builder().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    ApiResponse<OrderStatusPageResponse> findAll(@RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false) String sort,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<OrderStatusPageResponse>builder()
                .result(orderStatusService.findAll(keyword,sort,page,size))
                .build();
    }
}
