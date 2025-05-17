package com.CMS_Project.service;

import com.CMS_Project.dto.response.CommentPageResponse;
import com.CMS_Project.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    List<CommentResponse> getAll();
    CommentPageResponse findAll(String keyword, String sort, int page, int size);
}
