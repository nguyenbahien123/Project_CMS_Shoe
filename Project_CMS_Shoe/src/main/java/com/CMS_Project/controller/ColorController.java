package com.CMS_Project.controller;


import com.CMS_Project.dto.request.ColorRequest;
import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.ColorPageResponse;
import com.CMS_Project.dto.response.ColorResponse;
import com.CMS_Project.service.ColorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ColorController {

    ColorService colorService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    ApiResponse<ColorResponse> createColor(@RequestBody ColorRequest colorRequest){
        return ApiResponse.<ColorResponse>builder()
                .result(colorService.create(colorRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{colorId}")
    ApiResponse<ColorResponse> updateColors(@PathVariable Integer colorId, @RequestBody ColorRequest colorRequest){
        return ApiResponse.<ColorResponse>builder()
                .result(colorService.update(colorId,colorRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<ColorResponse>> getAllColor() {
        return ApiResponse.<List<ColorResponse>>builder()
                .result(colorService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{colorId}")
    ApiResponse<Void> deleteColor(@PathVariable Integer colorId) {
        colorService.delete(colorId);
        return ApiResponse.<Void>builder().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    ApiResponse<ColorPageResponse> findAll(@RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) String sort,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<ColorPageResponse>builder()
                .result(colorService.findAll(keyword,sort,page,size))
                .build();
    }
}
