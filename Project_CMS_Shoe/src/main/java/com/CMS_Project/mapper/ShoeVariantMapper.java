package com.CMS_Project.mapper;


import com.CMS_Project.dto.request.ShoeVariantRequest;
import com.CMS_Project.dto.response.ShoeVariantResponse;
import com.CMS_Project.entity.ShoeVariants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ShoeVariantMapper {
    @Mapping(target = "shoe", ignore = true)
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "size", ignore = true)
    ShoeVariants toShoeVariant(ShoeVariantRequest shoeVariantRequest);

    ShoeVariantResponse toShoeVariantResponse(ShoeVariants shoeVariants);

    @Mapping(target = "shoe", ignore = true)
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "size", ignore = true)
    void updateShoe(@MappingTarget ShoeVariants shoeVariants, ShoeVariantRequest shoeRequest);
}
