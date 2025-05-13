package com.CMS_Project.controller;



import com.CMS_Project.dto.request.CategoryRequest;
import com.CMS_Project.dto.response.ApiResponse;


import com.CMS_Project.dto.response.CategoryResponse;
import com.CMS_Project.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {

    CategoryService categoryService;

    @PostMapping("/add")
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.create(categoryRequest))
                .build();
    }

    @PutMapping("/{categoryId}")
    ApiResponse<CategoryResponse> updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryRequest categoryRequest){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.update(categoryId,categoryRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAll())
                .build();
    }

    @DeleteMapping("/{categoryId}")
    ApiResponse<Void> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.delete(categoryId);
        return ApiResponse.<Void>builder().build();
    }
}
