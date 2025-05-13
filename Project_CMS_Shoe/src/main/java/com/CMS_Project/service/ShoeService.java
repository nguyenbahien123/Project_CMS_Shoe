package com.CMS_Project.service;





import com.CMS_Project.dto.request.ShoeRequest;
import com.CMS_Project.dto.response.ShoeResponse;
import com.CMS_Project.entity.Brands;
import com.CMS_Project.entity.Categories;
import com.CMS_Project.entity.Shoes;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;


import com.CMS_Project.mapper.ShoeMapper;
import com.CMS_Project.repository.BrandsRepository;
import com.CMS_Project.repository.CategoryRepository;
import com.CMS_Project.repository.ShoeRepository;
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
public class ShoeService {

    ShoeRepository shoeRepository;
    UserRepository userRepository;
    ShoeMapper shoeMapper;
    BrandsRepository brandsRepository;
    CategoryRepository categoryRepository;

    public ShoeResponse create(ShoeRequest shoeRequest) {
        Shoes shoe = shoeMapper.toShoes(shoeRequest);
        shoe.setCreatedAt(LocalDateTime.now());
        shoe.setUpdatedAt(LocalDateTime.now());
        Users user = userRepository.findById(1).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        shoe.setCreatedBy(user.getEmail());
        shoe.setUpdatedBy(user.getEmail());
        Brands brands = brandsRepository.findByBrandName(shoeRequest.getBrands()).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        Categories categories = categoryRepository.findByCategoryName(shoeRequest.getCategory()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        shoe.setBrands(brands);
        shoe.setCategory(categories);
        shoeRepository.save(shoe);
        return shoeMapper.toShoeResponse(shoe);
    }

    public ShoeResponse update(Integer shoeId, ShoeRequest shoeRequest) {
        Shoes shoes = shoeRepository.findById(shoeId).orElseThrow(() -> new AppException(ErrorCode.SHOE_NOT_EXISTED));
        shoeMapper.updateShoe(shoes, shoeRequest);
        Users users = userRepository.findById(1).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        shoes.setUpdatedBy(users.getEmail());
        shoes.setUpdatedAt(LocalDateTime.now());
        Brands brands = brandsRepository.findByBrandName(shoeRequest.getBrands()).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        Categories categories = categoryRepository.findByCategoryName(shoeRequest.getCategory()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        shoes.setBrands(brands);
        shoes.setCategory(categories);
        shoeRepository.save(shoes);
        return shoeMapper.toShoeResponse(shoes);
    }

    public List<ShoeResponse> getAll() {
        return shoeRepository.findAll().stream().map(shoeMapper::toShoeResponse).toList();
    }

    public void delete(Integer shoeId) {
        shoeRepository.deleteById(shoeId);
    }
}
