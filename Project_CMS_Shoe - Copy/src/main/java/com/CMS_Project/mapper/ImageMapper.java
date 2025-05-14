package com.CMS_Project.mapper;


import com.CMS_Project.dto.request.ImageRequest;
import com.CMS_Project.dto.response.ImageResponse;
import com.CMS_Project.entity.Images;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ImageMapper {
    @Mapping(target = "variant", ignore = true)
    Images toImages(ImageRequest imageRequest);

    ImageResponse toImageResponse(Images images);

    @Mapping(target = "variant", ignore = true)
    void updateShoe(@MappingTarget Images images, ImageRequest imageRequest);
}
