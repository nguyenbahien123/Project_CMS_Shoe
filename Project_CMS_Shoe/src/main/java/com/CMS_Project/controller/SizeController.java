package com.CMS_Project.controller;


import com.CMS_Project.dto.request.SizeRequest;
import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.SizeResponse;
import com.CMS_Project.service.SizeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sizes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SizeController {

    SizeService sizeService;

    @PostMapping("/add")
    ApiResponse<SizeResponse> createColor(@RequestBody SizeRequest sizeRequest){
        return ApiResponse.<SizeResponse>builder()
                .result(sizeService.create(sizeRequest))
                .build();
    }

    @PutMapping("/{sizeId}")
    ApiResponse<SizeResponse> updateColors(@PathVariable Integer sizeId, @RequestBody SizeRequest sizeRequest){
        return ApiResponse.<SizeResponse>builder()
                .result(sizeService.update(sizeId,sizeRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<SizeResponse>> getAllColor() {
        return ApiResponse.<List<SizeResponse>>builder()
                .result(sizeService.getAll())
                .build();
    }

    @DeleteMapping("/{sizeId}")
    ApiResponse<Void> deleteColor(@PathVariable Integer sizeId) {
        sizeService.delete(sizeId);
        return ApiResponse.<Void>builder().build();
    }
}
