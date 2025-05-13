package com.CMS_Project.controller;


import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.CommentResponse;
import com.CMS_Project.dto.response.OrderResponse;
import com.CMS_Project.service.CommentService;
import com.CMS_Project.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentController {

    CommentService commentService;

        @GetMapping
        ApiResponse<List<CommentResponse>> getAll() {
            return ApiResponse.<List<CommentResponse>>builder()
                    .result(commentService.getAll())
                    .build();
        }
}
