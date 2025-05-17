package com.CMS_Project.service;

import com.CMS_Project.dto.request.CategoryRequest;
import com.CMS_Project.dto.response.CategoryPageResponse;
import com.CMS_Project.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse create(CategoryRequest categoryRequest);
    CategoryResponse update(Integer categoryId, CategoryRequest categoryRequest);
    List<CategoryResponse> getAll();
    void delete(Integer categoryId);
    CategoryPageResponse findAll(String keyword, String sort, int page, int size);
}
