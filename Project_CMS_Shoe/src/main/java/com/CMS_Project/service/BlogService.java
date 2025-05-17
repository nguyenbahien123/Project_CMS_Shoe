package com.CMS_Project.service;

import com.CMS_Project.dto.request.BlogRequest;
import com.CMS_Project.dto.response.BlogPageResponse;
import com.CMS_Project.dto.response.BlogResponse;

import java.util.List;

public interface BlogService {
    BlogResponse create(BlogRequest blogRequest);
    List<BlogResponse> getAll();
    BlogResponse update(int id,BlogRequest blogRequest);
    void delete(Integer blogId);
    BlogPageResponse findAll(String keyword, String sort, int page, int size);
}
