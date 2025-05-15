package com.CMS_Project.controller;



import com.CMS_Project.dto.request.SliderRequest;
import com.CMS_Project.dto.response.ApiResponse;


import com.CMS_Project.dto.response.SliderPageResponse;
import com.CMS_Project.dto.response.SliderResponse;
import com.CMS_Project.service.SliderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sliders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SliderController {

    SliderService sliderService;

    @PostMapping("/add")
    ApiResponse<SliderResponse> create(@RequestBody SliderRequest sliderRequest){
        return ApiResponse.<SliderResponse>builder()
                .result(sliderService.create(sliderRequest))
                .build();
    }

    @PutMapping("/{sliderId}")
    ApiResponse<SliderResponse> update(@PathVariable Integer sliderId, @RequestBody SliderRequest sliderRequest){
        return ApiResponse.<SliderResponse>builder()
                .result(sliderService.update(sliderId,sliderRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<SliderResponse>> getAll() {
        return ApiResponse.<List<SliderResponse>>builder()
                .result(sliderService.getAll())
                .build();
    }

    @DeleteMapping("/{sliderId}")
    ApiResponse<Void> delete(@PathVariable Integer sliderId) {
        sliderService.delete(sliderId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/list")
    ApiResponse<SliderPageResponse> findAll(@RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String sort,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<SliderPageResponse>builder()
                .result(sliderService.findAll(keyword,sort,page,size))
                .build();
    }
}
