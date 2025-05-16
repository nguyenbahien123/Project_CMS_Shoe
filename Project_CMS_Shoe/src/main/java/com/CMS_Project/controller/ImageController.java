package com.CMS_Project.controller;



import com.CMS_Project.dto.request.ImageRequest;
import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.ImagePageResponse;
import com.CMS_Project.dto.response.ImageResponse;
import com.CMS_Project.service.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImageController {

    ImageService imageService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    ApiResponse<ImageResponse> create(@RequestBody ImageRequest imageRequest){
        return ApiResponse.<ImageResponse>builder()
                .result(imageService.create(imageRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{imageId}")
    ApiResponse<ImageResponse> update(@PathVariable Integer imageId, @RequestBody ImageRequest shoeVariantRequest){
        return ApiResponse.<ImageResponse>builder()
                .result(imageService.update(imageId,shoeVariantRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<ImageResponse>> getAll() {
        return ApiResponse.<List<ImageResponse>>builder()
                .result(imageService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{imageId}")
    ApiResponse<Void> delete(@PathVariable Integer imageId) {
        imageService.delete(imageId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/list")
    ApiResponse<ImagePageResponse> findAll(@RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) String sort,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<ImagePageResponse>builder()
                .result(imageService.findAll(keyword,sort,page,size))
                .build();
    }
}
