package com.CMS_Project.service;




import com.CMS_Project.dto.request.ImageRequest;
import com.CMS_Project.dto.response.ImageResponse;
import com.CMS_Project.entity.*;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;

import com.CMS_Project.mapper.ImageMapper;
import com.CMS_Project.repository.ImageRepository;
import com.CMS_Project.repository.ShoeVariantRepository;
import com.CMS_Project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {

    ImageRepository imageRepository;
    UserRepository userRepository;
    ShoeVariantRepository shoeVariantRepository;
    ImageMapper imageMapper;

    public ImageResponse create(ImageRequest imageRequest) {
        Images image = imageMapper.toImages(imageRequest);
        image.setCreatedAt(LocalDateTime.now());
        image.setUpdatedAt(LocalDateTime.now());
        Users user = userRepository.findById(1).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        image.setCreatedBy(user.getEmail());
        image.setUpdatedBy(user.getEmail());
        ShoeVariants shoeVariants = shoeVariantRepository.findById(imageRequest.getVariant()).orElseThrow(() -> new AppException(ErrorCode.SHOE_VARIANT_NOT_EXISTED));
        image.setVariant(shoeVariants);
        imageRepository.save(image);
        return imageMapper.toImageResponse(image);
    }

    public ImageResponse update(Integer imageId, ImageRequest imageRequest) {
        Images images = imageRepository.findById(imageId).orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_EXISTED));
        imageMapper.updateShoe(images, imageRequest);
        Users users = userRepository.findById(1).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        images.setUpdatedAt(LocalDateTime.now());
        images.setUpdatedBy(users.getEmail());
        ShoeVariants shoeVariants = shoeVariantRepository.findById(imageRequest.getVariant()).orElseThrow(() -> new AppException(ErrorCode.SHOE_VARIANT_NOT_EXISTED));
        images.setVariant(shoeVariants);
        imageRepository.save(images);
        return imageMapper.toImageResponse(images);
    }

    public List<ImageResponse> getAll() {
        return imageRepository.findAll().stream().map(imageMapper::toImageResponse).toList();
    }

    public void delete(Integer imageId) {
        imageRepository.deleteById(imageId);
    }
}
