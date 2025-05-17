package com.CMS_Project.controller;


import com.CMS_Project.dto.response.*;
import com.CMS_Project.service.CommentService;
import com.CMS_Project.service.impl.CommentServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentController {

    CommentService commentService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<CommentResponse>> getAll() {
        return ApiResponse.<List<CommentResponse>>builder()
                .result(commentService.getAll())
                .build();
        }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    ApiResponse<CommentPageResponse> findAll(@RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) String sort,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<CommentPageResponse>builder()
                .result(commentService.findAll(keyword,sort,page,size))
                .build();
    }
}
