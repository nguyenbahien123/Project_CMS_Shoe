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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sliders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SliderController {

    SliderService sliderService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    ApiResponse<SliderResponse> create(@RequestBody SliderRequest sliderRequest){
        return ApiResponse.<SliderResponse>builder()
                .result(sliderService.create(sliderRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{sliderId}")
    ApiResponse<SliderResponse> update(@PathVariable Integer sliderId, @RequestBody SliderRequest sliderRequest){
        return ApiResponse.<SliderResponse>builder()
                .result(sliderService.update(sliderId,sliderRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<SliderResponse>> getAll() {
        return ApiResponse.<List<SliderResponse>>builder()
                .result(sliderService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{sliderId}")
    ApiResponse<Void> delete(@PathVariable Integer sliderId) {
        sliderService.delete(sliderId);
        return ApiResponse.<Void>builder().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
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
