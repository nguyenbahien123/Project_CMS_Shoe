package com.CMS_Project.service.impl;





import com.CMS_Project.dto.request.ShoeRequest;
import com.CMS_Project.dto.response.ShoePageResponse;
import com.CMS_Project.dto.response.ShoeResponse;
import com.CMS_Project.entity.*;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;
import com.CMS_Project.mapper.ShoeMapper;
import com.CMS_Project.repository.BrandsRepository;
import com.CMS_Project.repository.CategoryRepository;
import com.CMS_Project.repository.ShoeRepository;
import com.CMS_Project.repository.UserRepository;
import com.CMS_Project.service.ShoeService;
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
public class ShoeServiceImpl implements ShoeService {

    ShoeRepository shoeRepository;
    UserRepository userRepository;
    ShoeMapper shoeMapper;
    BrandsRepository brandsRepository;
    CategoryRepository categoryRepository;

    @Override
    public ShoeResponse create(ShoeRequest shoeRequest) {
        Shoes shoe = shoeMapper.toShoes(shoeRequest);
        shoe.setCreatedAt(LocalDateTime.now());
        shoe.setUpdatedAt(LocalDateTime.now());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        shoe.setCreatedBy(user.getEmail());
        shoe.setUpdatedBy(user.getEmail());
        Brands brands = brandsRepository.findByBrandName(shoeRequest.getBrands()).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        Categories categories = categoryRepository.findByCategoryName(shoeRequest.getCategory()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        shoe.setBrands(brands);
        shoe.setCategory(categories);
        shoeRepository.save(shoe);
        return shoeMapper.toShoeResponse(shoe);
    }

    @Override
    public ShoeResponse update(Integer shoeId, ShoeRequest shoeRequest) {
        Shoes shoes = shoeRepository.findById(shoeId).orElseThrow(() -> new AppException(ErrorCode.SHOE_NOT_EXISTED));
        shoeMapper.updateShoe(shoes, shoeRequest);
        Brands brands = brandsRepository.findByBrandName(shoeRequest.getBrands()).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        Categories categories = categoryRepository.findByCategoryName(shoeRequest.getCategory()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        shoes.setBrands(brands);
        shoes.setCategory(categories);
        shoeRepository.save(shoes);
        return shoeMapper.toShoeResponse(shoes);
    }

    @Override
    public List<ShoeResponse> getAll() {
        return shoeRepository.findAll().stream().map(shoeMapper::toShoeResponse).toList();
    }

    @Override
    public void delete(Integer shoeId) {
        shoeRepository.deleteById(shoeId);
    }

    @Override
    public ShoePageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"shoeId");
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

        Page<Shoes> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = shoeRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = shoeRepository.findAll(pageable);
        }

        List<ShoeResponse> shoeResponseList = entityPage.stream().map(shoeMapper::toShoeResponse).toList();
        ShoePageResponse shoePageResponse = new ShoePageResponse();
        shoePageResponse.setTotalElements(entityPage.getTotalElements());
        shoePageResponse.setTotalPages(entityPage.getTotalPages());
        shoePageResponse.setPageNumber(entityPage.getNumber());
        shoePageResponse.setPageSize(entityPage.getSize());
        shoePageResponse.setShoes(shoeResponseList);
        return shoePageResponse;
    }
}
