package com.CMS_Project.service;




import com.CMS_Project.dto.request.ImageRequest;
import com.CMS_Project.dto.response.ImagePageResponse;
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
public class ImageService {

    ImageRepository imageRepository;
    UserRepository userRepository;
    ShoeVariantRepository shoeVariantRepository;
    ImageMapper imageMapper;

    public ImageResponse create(ImageRequest imageRequest) {
        Images image = imageMapper.toImages(imageRequest);
        image.setCreatedAt(LocalDateTime.now());
        image.setUpdatedAt(LocalDateTime.now());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
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

    public ImagePageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"imageId");
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

        Page<Images> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = imageRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = imageRepository.findAll(pageable);
        }

        List<ImageResponse> imageResponseList = entityPage.stream().map(imageMapper::toImageResponse).toList();
        ImagePageResponse imagePageResponse = new ImagePageResponse();
        imagePageResponse.setImages(imageResponseList);
        imagePageResponse.setPageNumber(entityPage.getNumber());
        imagePageResponse.setPageSize(entityPage.getSize());
        imagePageResponse.setTotalElements(entityPage.getTotalElements());
        imagePageResponse.setTotalPages(entityPage.getTotalPages());
        return imagePageResponse;
    }
}
