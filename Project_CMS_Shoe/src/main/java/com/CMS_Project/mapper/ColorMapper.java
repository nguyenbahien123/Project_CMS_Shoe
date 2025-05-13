package com.CMS_Project.mapper;

import com.CMS_Project.dto.request.ColorRequest;
import com.CMS_Project.dto.response.ColorResponse;
import com.CMS_Project.entity.Colors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface ColorMapper {

    Colors toColor(ColorRequest colorRequest);

    ColorResponse toColorResponse(Colors colors);

    void updateColor(@MappingTarget Colors colors, ColorRequest colorRequest);
}
