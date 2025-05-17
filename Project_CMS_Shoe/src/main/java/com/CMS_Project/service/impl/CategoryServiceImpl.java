package com.CMS_Project.service.impl;




import com.CMS_Project.dto.request.CategoryRequest;
import com.CMS_Project.dto.response.CategoryPageResponse;
import com.CMS_Project.dto.response.CategoryResponse;
import com.CMS_Project.entity.Categories;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;


import com.CMS_Project.mapper.CategoryMapper;
import com.CMS_Project.repository.CategoryRepository;
import com.CMS_Project.repository.ColorRepository;
import com.CMS_Project.repository.UserRepository;
import com.CMS_Project.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {


    UserRepository userRepository;
    CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryResponse create(CategoryRequest categoryRequest) {
        Categories category = categoryMapper.toCategory(categoryRequest);
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public CategoryResponse update(Integer categoryId, CategoryRequest categoryRequest) {
        Categories category = categoryRepository.findById(categoryId).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        categoryMapper.updateCategory(category, categoryRequest);
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
    }

    public void delete(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public CategoryPageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"categoryId");
        if(StringUtils.hasLength(sort)){
            Pattern pattern = Pattern.compile("^(\\w+):(asc|desc)$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sort);
            if(matcher.find()){
                String columnName = matcher.group(1);
                if(matcher.group(2).equalsIgnoreCase("asc")){
                    order = new Sort.Order(Sort.Direction.ASC,columnName);
                }else{
                    order = new Sort.Order(Sort.Direction.DESC,columnName);
                }
            }
        }

        int pageNo = 0;
        if(page > 0){
            pageNo = page - 1;
        }

        Pageable pageable = PageRequest.of(pageNo,size,Sort.by(order));

        Page<Categories> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = categoryRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = categoryRepository.findAll(pageable);
        }

        List<CategoryResponse> categoryResponseList = entityPage.stream().map(categoryMapper::toCategoryResponse).toList();
        CategoryPageResponse categoryPageResponse = new CategoryPageResponse();
        categoryPageResponse.setPageNumber(entityPage.getNumber());
        categoryPageResponse.setPageSize(entityPage.getSize());
        categoryPageResponse.setTotalElements(entityPage.getTotalElements());
        categoryPageResponse.setTotalPages(entityPage.getTotalPages());
        categoryPageResponse.setCategories(categoryResponseList);
        return categoryPageResponse;
    }
}
