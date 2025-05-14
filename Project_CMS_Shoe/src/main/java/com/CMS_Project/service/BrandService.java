package com.CMS_Project.service;

import com.CMS_Project.dto.request.BrandRequest;
import com.CMS_Project.dto.response.BlogResponse;
import com.CMS_Project.dto.response.BrandResponse;
import com.CMS_Project.entity.Brands;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;
import com.CMS_Project.mapper.BrandMapper;
import com.CMS_Project.repository.BrandsRepository;
import com.CMS_Project.repository.UserRepository;
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
public class BrandService {

    BrandsRepository brandsRepository;
    UserRepository userRepository;
    BrandMapper brandMapper;

    public BrandResponse create(BrandRequest brandRequest) {
        Brands brand = brandMapper.toBrand(brandRequest);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());
        brand.setCreatedBy(user.getEmail());
        brand.setUpdatedBy(user.getEmail());
        brandsRepository.save(brand);
        return brandMapper.toBrandResponse(brand);
    }

    public BrandResponse update(Integer brandId, BrandRequest brandRequest) {
        Brands brand = brandsRepository.findById(brandId).orElseThrow(()-> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        brandMapper.updateBrand(brand, brandRequest);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        brand.setUpdatedAt(LocalDateTime.now());
        brand.setUpdatedBy(user.getEmail());
        brandsRepository.save(brand);
        return brandMapper.toBrandResponse(brand);
    }

    public List<BrandResponse> getAll() {
        return brandsRepository.findAll().stream().map(brandMapper::toBrandResponse).toList();
    }

    public void delete(Integer brandId) {
        brandsRepository.deleteById(brandId);
    }
}
