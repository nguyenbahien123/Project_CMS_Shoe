package com.CMS_Project.controller;


import com.CMS_Project.dto.request.SizeRequest;
import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.BlogPageResponse;
import com.CMS_Project.dto.response.SizePageResponse;
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
    ApiResponse<SizeResponse> createSize(@RequestBody SizeRequest sizeRequest){
        return ApiResponse.<SizeResponse>builder()
                .result(sizeService.create(sizeRequest))
                .build();
    }

    @PutMapping("/{sizeId}")
    ApiResponse<SizeResponse> updateSize(@PathVariable Integer sizeId, @RequestBody SizeRequest sizeRequest){
        return ApiResponse.<SizeResponse>builder()
                .result(sizeService.update(sizeId,sizeRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<SizeResponse>> getAllSizes() {
        return ApiResponse.<List<SizeResponse>>builder()
                .result(sizeService.getAll())
                .build();
    }

    @DeleteMapping("/{sizeId}")
    ApiResponse<Void> deleteSize(@PathVariable Integer sizeId) {
        sizeService.delete(sizeId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/list")
    ApiResponse<SizePageResponse> findAll(@RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) String sort,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<SizePageResponse>builder()
                .result(sizeService.findAll(keyword,sort,page,size))
                .build();
    }
}
