package com.CMS_Project.controller;



import com.CMS_Project.dto.request.ShoeVariantRequest;
import com.CMS_Project.dto.response.ApiResponse;


import com.CMS_Project.dto.response.BlogPageResponse;
import com.CMS_Project.dto.response.ShoeVariantPageResponse;
import com.CMS_Project.dto.response.ShoeVariantResponse;
import com.CMS_Project.service.ShoeVariantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/variants")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShoeVariantController {

    ShoeVariantService shoeVariantService;

    @PostMapping("/add")
    ApiResponse<ShoeVariantResponse> create(@RequestBody ShoeVariantRequest shoeVariantRequest){
        return ApiResponse.<ShoeVariantResponse>builder()
                .result(shoeVariantService.create(shoeVariantRequest))
                .build();
    }

    @PutMapping("/{variantId}")
    ApiResponse<ShoeVariantResponse> update(@PathVariable Integer variantId, @RequestBody ShoeVariantRequest shoeVariantRequest){
        return ApiResponse.<ShoeVariantResponse>builder()
                .result(shoeVariantService.update(variantId,shoeVariantRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<ShoeVariantResponse>> getAll() {
        return ApiResponse.<List<ShoeVariantResponse>>builder()
                .result(shoeVariantService.getAll())
                .build();
    }

    @DeleteMapping("/{variantId}")
    ApiResponse<Void> delete(@PathVariable Integer variantId) {
        shoeVariantService.delete(variantId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/list")
    ApiResponse<ShoeVariantPageResponse> findAll(@RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false) String sort,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<ShoeVariantPageResponse>builder()
                .result(shoeVariantService.findAll(keyword,sort,page,size))
                .build();
    }
}
