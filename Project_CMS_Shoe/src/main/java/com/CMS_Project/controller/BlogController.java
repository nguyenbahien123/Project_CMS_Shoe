package com.CMS_Project.controller;

import com.CMS_Project.dto.request.BlogRequest;
import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.BlogPageResponse;
import com.CMS_Project.dto.response.BlogResponse;
import com.CMS_Project.service.BlogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BlogController {
    BlogService blogService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    ApiResponse <BlogResponse> createBlogs(@RequestBody BlogRequest blogRequest){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.create(blogRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{blogId}")
    ApiResponse<BlogResponse> updateBlogs(@PathVariable Integer blogId, @RequestBody BlogRequest blogRequest){
        return ApiResponse.<BlogResponse>builder()
                .result(blogService.update(blogId,blogRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<BlogResponse>> getAllBlogs() {
        return ApiResponse.<List<BlogResponse>>builder()
                .result(blogService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{blogId}")
    ApiResponse<Void> deleteBlogs(@PathVariable Integer blogId) {
        blogService.delete(blogId);
        return ApiResponse.<Void>builder().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    ApiResponse<BlogPageResponse> findAll(@RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) String sort,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<BlogPageResponse>builder()
                .result(blogService.findAll(keyword,sort,page,size))
                .build();
    }
}
