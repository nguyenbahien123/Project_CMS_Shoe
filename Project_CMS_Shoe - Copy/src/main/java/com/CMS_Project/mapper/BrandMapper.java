package com.CMS_Project.mapper;

import com.CMS_Project.dto.request.BrandRequest;
import com.CMS_Project.dto.response.BrandResponse;
import com.CMS_Project.entity.Brands;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface BrandMapper {

    Brands toBrand(BrandRequest brandRequest);

    BrandResponse toBrandResponse(Brands brands);

    void updateBrand(@MappingTarget Brands brands, BrandRequest brandRequest);
}
