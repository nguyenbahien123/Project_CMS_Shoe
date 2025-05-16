package com.CMS_Project.controller;


import com.CMS_Project.dto.request.BrandRequest;
import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.BrandPageResponse;
import com.CMS_Project.dto.response.BrandResponse;
import com.CMS_Project.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BrandController {

    BrandService brandService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    ApiResponse<BrandResponse> createBrand(@RequestBody BrandRequest brandRequest){
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.create(brandRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{brandId}")
    ApiResponse<BrandResponse> updateBrand(@PathVariable Integer brandId, @RequestBody BrandRequest brandRequest){
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.update(brandId,brandRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<BrandResponse>> getAllBrands() {
        return ApiResponse.<List<BrandResponse>>builder()
                .result(brandService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{brandId}")
    ApiResponse<Void> deleteBrand(@PathVariable Integer brandId) {
        brandService.delete(brandId);
        return ApiResponse.<Void>builder().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    ApiResponse<BrandPageResponse> findAll(@RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) String sort,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<BrandPageResponse>builder()
                .result(brandService.findAll(keyword,sort,page,size))
                .build();
    }
}
