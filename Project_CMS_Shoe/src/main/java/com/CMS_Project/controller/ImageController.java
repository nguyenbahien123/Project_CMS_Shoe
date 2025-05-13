package com.CMS_Project.controller;



import com.CMS_Project.dto.request.ImageRequest;
import com.CMS_Project.dto.response.ApiResponse;


import com.CMS_Project.dto.response.ImageResponse;
import com.CMS_Project.service.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImageController {

    ImageService imageService;

    @PostMapping("/add")
    ApiResponse<ImageResponse> create(@RequestBody ImageRequest imageRequest){
        return ApiResponse.<ImageResponse>builder()
                .result(imageService.create(imageRequest))
                .build();
    }

    @PutMapping("/{imageId}")
    ApiResponse<ImageResponse> update(@PathVariable Integer imageId, @RequestBody ImageRequest shoeVariantRequest){
        return ApiResponse.<ImageResponse>builder()
                .result(imageService.update(imageId,shoeVariantRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<ImageResponse>> getAll() {
        return ApiResponse.<List<ImageResponse>>builder()
                .result(imageService.getAll())
                .build();
    }

    @DeleteMapping("/{imageId}")
    ApiResponse<Void> delete(@PathVariable Integer imageId) {
        imageService.delete(imageId);
        return ApiResponse.<Void>builder().build();
    }
}
