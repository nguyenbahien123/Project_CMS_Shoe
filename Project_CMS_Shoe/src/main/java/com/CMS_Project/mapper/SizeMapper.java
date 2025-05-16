package com.CMS_Project.mapper;

import com.CMS_Project.dto.request.SizeRequest;
import com.CMS_Project.dto.response.SizeResponse;
import com.CMS_Project.entity.Sizes;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface SizeMapper {

    Sizes toSize(SizeRequest sizeRequest);

    SizeResponse toSizeResponse(Sizes sizes);

    void updateSize(@MappingTarget Sizes sizes, SizeRequest sizeRequest);
}
