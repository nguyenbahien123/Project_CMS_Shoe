package com.CMS_Project.service;





import com.CMS_Project.dto.request.SliderRequest;
import com.CMS_Project.dto.response.SliderPageResponse;
import com.CMS_Project.dto.response.SliderResponse;
import com.CMS_Project.entity.*;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;

import com.CMS_Project.mapper.SliderMapper;
import com.CMS_Project.repository.*;
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
public class SliderService {

    ShoeVariantRepository shoeVariantRepository;
    UserRepository userRepository;
    SliderMapper sliderMapper;
    ShoeRepository shoeRepository;
    SliderRepository sliderRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public SliderResponse create(SliderRequest sliderRequest) {
        Sliders sliders = sliderMapper.toSlider(sliderRequest);
        sliders.setCreatedAt(LocalDateTime.now());
        sliders.setUpdatedAt(LocalDateTime.now());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        sliders.setCreatedBy(user.getEmail());
        sliders.setUpdatedBy(user.getEmail());
        Shoes shoes = shoeRepository.findByName(sliderRequest.getShoe()).orElseThrow(() -> new AppException(ErrorCode.SHOE_NOT_EXISTED));
        sliders.setShoe(shoes);
        sliderRepository.save(sliders);
        return sliderMapper.toSliderResponse(sliders);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public SliderResponse update(Integer sliderId, SliderRequest sliderRequest) {
        Sliders sliders = sliderRepository.findById(sliderId).orElseThrow(() -> new AppException(ErrorCode.SLIDER_NOT_EXISTED));
        sliderMapper.updateSlider(sliders, sliderRequest);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        sliders.setUpdatedBy(users.getEmail());
        sliders.setUpdatedAt(LocalDateTime.now());
        Shoes shoes = shoeRepository.findByName(sliderRequest.getShoe()).orElseThrow(() -> new AppException(ErrorCode.SHOE_NOT_EXISTED));
        sliders.setShoe(shoes);
        shoeRepository.save(shoes);
        return sliderMapper.toSliderResponse(sliders);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<SliderResponse> getAll() {
        return sliderRepository.findAll().stream().map(sliderMapper::toSliderResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Integer sliderId) {
        sliderRepository.deleteById(sliderId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public SliderPageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"sliderId");
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

        Page<Sliders> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = sliderRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = sliderRepository.findAll(pageable);
        }

        List<SliderResponse> sliderResponses = entityPage.stream().map(sliderMapper::toSliderResponse).toList();
        SliderPageResponse sliderPageResponse = new SliderPageResponse();
        sliderPageResponse.setSliders(sliderResponses);
        sliderPageResponse.setPageNumber(entityPage.getNumber());
        sliderPageResponse.setPageSize(entityPage.getSize());
        sliderPageResponse.setTotalElements(entityPage.getTotalElements());
        sliderPageResponse.setTotalPages(entityPage.getTotalPages());
        return sliderPageResponse;
    }
}
