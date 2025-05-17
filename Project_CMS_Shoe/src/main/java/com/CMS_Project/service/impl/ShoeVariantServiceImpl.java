package com.CMS_Project.service.impl;




import com.CMS_Project.dto.request.ShoeVariantRequest;
import com.CMS_Project.dto.response.ShoeVariantPageResponse;
import com.CMS_Project.dto.response.ShoeVariantResponse;
import com.CMS_Project.entity.*;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;
import com.CMS_Project.mapper.ShoeVariantMapper;
import com.CMS_Project.repository.*;
import com.CMS_Project.service.ShoeVariantService;
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
public class ShoeVariantServiceImpl implements ShoeVariantService {

    ShoeVariantRepository shoeVariantRepository;
    UserRepository userRepository;
    ShoeVariantMapper shoeVariantMapper;
    ColorRepository colorRepository;
    ShoeRepository shoeRepository;
    SizeRepository sizeRepository;

    @Override
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

    @Override
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

    @Override
    public List<ShoeVariantResponse> getAll() {
        return shoeVariantRepository.findAll().stream().map(shoeVariantMapper::toShoeVariantResponse).toList();
    }

    @Override
    public void delete(Integer variantId) {
        shoeVariantRepository.deleteById(variantId);
    }

    @Override
    public ShoeVariantPageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"variantId");
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

        Page<ShoeVariants> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = shoeVariantRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = shoeVariantRepository.findAll(pageable);
        }

        List<ShoeVariantResponse> shoeVariantResponseList = entityPage.stream().map(shoeVariantMapper::toShoeVariantResponse).toList();
        ShoeVariantPageResponse shoeVariantPageResponse = new ShoeVariantPageResponse();
        shoeVariantPageResponse.setPageNumber(entityPage.getNumber());
        shoeVariantPageResponse.setPageSize(entityPage.getSize());
        shoeVariantPageResponse.setTotalElements(entityPage.getTotalElements());
        shoeVariantPageResponse.setTotalPages(entityPage.getTotalPages());
        shoeVariantPageResponse.setShoeVariants(shoeVariantResponseList);
        return shoeVariantPageResponse;

    }
}
