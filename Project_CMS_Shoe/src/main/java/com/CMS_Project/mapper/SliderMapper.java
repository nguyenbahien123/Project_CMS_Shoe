package com.CMS_Project.mapper;

import com.CMS_Project.dto.request.ColorRequest;
import com.CMS_Project.dto.request.RoleRequest;
import com.CMS_Project.dto.request.SliderRequest;
import com.CMS_Project.dto.response.RoleResponse;
import com.CMS_Project.dto.response.SliderResponse;
import com.CMS_Project.entity.Colors;
import com.CMS_Project.entity.Roles;
import com.CMS_Project.entity.Sliders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface SliderMapper {
    @Mapping(target = "shoe", ignore = true)
    Sliders toSlider(SliderRequest sliderRequest);

    SliderResponse toSliderResponse(Sliders sliders);

    @Mapping(target = "shoe", ignore = true)
    void updateSlider(@MappingTarget Sliders sliders, SliderRequest sliderRequest);
}
