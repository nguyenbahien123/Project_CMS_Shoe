package com.CMS_Project.service;




import com.CMS_Project.dto.request.CategoryRequest;
import com.CMS_Project.dto.response.CategoryResponse;
import com.CMS_Project.entity.Categories;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;


import com.CMS_Project.mapper.CategoryMapper;
import com.CMS_Project.mapper.ColorMapper;
import com.CMS_Project.repository.CategoryRepository;
import com.CMS_Project.repository.ColorRepository;
import com.CMS_Project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {

    ColorRepository colorRepository;
    UserRepository userRepository;
    CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryResponse create(CategoryRequest categoryRequest) {
        Categories category = categoryMapper.toCategory(categoryRequest);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category.setCreatedBy(user.getEmail());
        category.setUpdatedBy(user.getEmail());
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public CategoryResponse update(Integer categoryId, CategoryRequest categoryRequest) {
        Categories category = categoryRepository.findById(categoryId).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        categoryMapper.updateCategory(category, categoryRequest);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        category.setUpdatedAt(LocalDateTime.now());
        category.setUpdatedBy(user.getEmail());
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
    }

    public void delete(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
