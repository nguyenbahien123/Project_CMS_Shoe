package com.CMS_Project.controller;



import com.CMS_Project.dto.request.ColorRequest;
import com.CMS_Project.dto.response.ApiResponse;

import com.CMS_Project.dto.response.ColorResponse;

import com.CMS_Project.service.ColorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ColorController {

    ColorService colorService;

    @PostMapping("/add")
    ApiResponse<ColorResponse> createColor(@RequestBody ColorRequest colorRequest){
        return ApiResponse.<ColorResponse>builder()
                .result(colorService.create(colorRequest))
                .build();
    }

    @PutMapping("/{colorId}")
    ApiResponse<ColorResponse> updateColors(@PathVariable Integer colorId, @RequestBody ColorRequest colorRequest){
        return ApiResponse.<ColorResponse>builder()
                .result(colorService.update(colorId,colorRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<ColorResponse>> getAllColor() {
        return ApiResponse.<List<ColorResponse>>builder()
                .result(colorService.getAll())
                .build();
    }

    @DeleteMapping("/{colorId}")
    ApiResponse<Void> deleteColor(@PathVariable Integer colorId) {
        colorService.delete(colorId);
        return ApiResponse.<Void>builder().build();
    }
}
