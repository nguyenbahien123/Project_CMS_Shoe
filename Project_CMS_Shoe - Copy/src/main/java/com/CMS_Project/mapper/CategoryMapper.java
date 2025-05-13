package com.CMS_Project.mapper;




import com.CMS_Project.dto.request.CategoryRequest;
import com.CMS_Project.dto.response.CategoryResponse;
import com.CMS_Project.entity.Categories;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface CategoryMapper {

    Categories toCategory(CategoryRequest categoryRequest);

    CategoryResponse toCategoryResponse(Categories categories);

    void updateCategory(@MappingTarget Categories categories, CategoryRequest categoryRequest);
}
