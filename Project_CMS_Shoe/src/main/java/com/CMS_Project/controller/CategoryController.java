package com.CMS_Project.controller;



import com.CMS_Project.dto.request.CategoryRequest;
import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.CategoryPageResponse;
import com.CMS_Project.dto.response.CategoryResponse;
import com.CMS_Project.service.CategoryService;
import com.CMS_Project.service.impl.CategoryServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {

    CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.create(categoryRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}")
    ApiResponse<CategoryResponse> updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryRequest categoryRequest){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.update(categoryId,categoryRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    ApiResponse<Void> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.delete(categoryId);
        return ApiResponse.<Void>builder().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    ApiResponse<CategoryPageResponse> findAll(@RequestParam(required = false) String keyword,
                                              @RequestParam(required = false) String sort,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<CategoryPageResponse>builder()
                .result(categoryService.findAll(keyword,sort,page,size))
                .build();
    }
}
