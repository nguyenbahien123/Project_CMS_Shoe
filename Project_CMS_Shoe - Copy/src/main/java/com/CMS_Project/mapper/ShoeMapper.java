package com.CMS_Project.mapper;

import com.CMS_Project.dto.request.ShoeRequest;
import com.CMS_Project.dto.response.ShoeResponse;
import com.CMS_Project.entity.Shoes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ShoeMapper {
    @Mapping(target = "brands", ignore = true)
    @Mapping(target = "category", ignore = true)
    Shoes toShoes(ShoeRequest shoeRequest);

    ShoeResponse toShoeResponse(Shoes shoes);

    @Mapping(target = "brands", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateShoe(@MappingTarget Shoes shoes, ShoeRequest shoeRequest);
}
