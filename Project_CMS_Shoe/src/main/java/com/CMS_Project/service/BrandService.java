package com.CMS_Project.service;

import com.CMS_Project.dto.request.BrandRequest;
import com.CMS_Project.dto.response.BrandPageResponse;
import com.CMS_Project.dto.response.BrandResponse;
import com.CMS_Project.entity.Blogs;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class BrandService {

    BrandsRepository brandsRepository;
    UserRepository userRepository;
    BrandMapper brandMapper;

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    public List<BrandResponse> getAll() {
        return brandsRepository.findAll().stream().map(brandMapper::toBrandResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Integer brandId) {
        brandsRepository.deleteById(brandId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public BrandPageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"brandId");
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

        Page<Brands> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = brandsRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = brandsRepository.findAll(pageable);
        }

        List<BrandResponse> brandResponseList = entityPage.stream().map(brandMapper::toBrandResponse).toList();
        BrandPageResponse brandPageResponse = new BrandPageResponse();
        brandPageResponse.setPageNumber(entityPage.getNumber());
        brandPageResponse.setPageSize(entityPage.getSize());
        brandPageResponse.setTotalElements(entityPage.getTotalElements());
        brandPageResponse.setTotalPages(entityPage.getTotalPages());
        brandPageResponse.setBrands(brandResponseList);
        return brandPageResponse;
    }
}
