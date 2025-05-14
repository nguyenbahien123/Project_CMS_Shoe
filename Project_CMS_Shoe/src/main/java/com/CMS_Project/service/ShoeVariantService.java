package com.CMS_Project.service;




import com.CMS_Project.dto.request.ShoeVariantRequest;
import com.CMS_Project.dto.response.ShoeVariantResponse;
import com.CMS_Project.entity.*;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;


import com.CMS_Project.mapper.ShoeVariantMapper;
import com.CMS_Project.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShoeVariantService {

    ShoeVariantRepository shoeVariantRepository;
    UserRepository userRepository;
    ShoeVariantMapper shoeVariantMapper;
    ColorRepository colorRepository;
    ShoeRepository shoeRepository;
    SizeRepository sizeRepository;

    public ShoeVariantResponse create(ShoeVariantRequest shoeVariantRequest) {
        ShoeVariants shoeVariants = shoeVariantMapper.toShoeVariant(shoeVariantRequest);
        shoeVariants.setCreatedAt(LocalDateTime.now());
        shoeVariants.setUpdatedAt(LocalDateTime.now());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        shoeVariants.setCreatedBy(user.getEmail());
        shoeVariants.setUpdatedBy(user.getEmail());
        Colors colors = colorRepository.findByName(shoeVariantRequest.getColor()).orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_EXISTED));
        Sizes sizes = sizeRepository.findByName(shoeVariantRequest.getSize()).orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_EXISTED));
        Shoes shoes = shoeRepository.findByName(shoeVariantRequest.getShoe()).orElseThrow(() -> new AppException(ErrorCode.SHOE_NOT_EXISTED));
        shoeVariants.setColor(colors);
        shoeVariants.setSize(sizes);
        shoeVariants.setShoe(shoes);
        shoeVariantRepository.save(shoeVariants);
        return shoeVariantMapper.toShoeVariantResponse(shoeVariants);
    }

    public ShoeVariantResponse update(Integer variantId, ShoeVariantRequest shoeVariantRequest) {
        ShoeVariants shoeVariants = shoeVariantRepository.findById(variantId).orElseThrow(()-> new AppException(ErrorCode.SHOE_VARIANT_NOT_EXISTED));
        shoeVariantMapper.updateShoe(shoeVariants,shoeVariantRequest);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        shoeVariants.setUpdatedBy(users.getEmail());
        shoeVariants.setUpdatedAt(LocalDateTime.now());
        Colors colors = colorRepository.findByName(shoeVariantRequest.getColor()).orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_EXISTED));
        Sizes sizes = sizeRepository.findByName(shoeVariantRequest.getSize()).orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_EXISTED));
        Shoes shoes = shoeRepository.findByName(shoeVariantRequest.getShoe()).orElseThrow(() -> new AppException(ErrorCode.SHOE_NOT_EXISTED));
        shoeVariants.setColor(colors);
        shoeVariants.setSize(sizes);
        shoeVariants.setShoe(shoes);
        shoeVariantRepository.save(shoeVariants);
        return shoeVariantMapper.toShoeVariantResponse(shoeVariants);
    }

    public List<ShoeVariantResponse> getAll() {
        return shoeVariantRepository.findAll().stream().map(shoeVariantMapper::toShoeVariantResponse).toList();
    }

    public void delete(Integer variantId) {
        shoeVariantRepository.deleteById(variantId);
    }
}
