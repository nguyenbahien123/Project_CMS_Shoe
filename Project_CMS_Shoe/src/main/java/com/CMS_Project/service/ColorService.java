package com.CMS_Project.service;




import com.CMS_Project.dto.request.ColorRequest;
import com.CMS_Project.dto.response.ColorPageResponse;
import com.CMS_Project.dto.response.ColorResponse;
import com.CMS_Project.entity.Colors;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;
import com.CMS_Project.mapper.ColorMapper;
import com.CMS_Project.repository.ColorRepository;
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
public class ColorService {

    ColorRepository colorRepository;
    UserRepository userRepository;
    ColorMapper colorMapper;

    public ColorResponse create(ColorRequest colorRequest) {
        Colors colors = colorMapper.toColor(colorRequest);
        colors.setCreatedAt(LocalDateTime.now());
        colors.setUpdatedAt(LocalDateTime.now());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        colors.setCreatedBy(user.getEmail());
        colors.setUpdatedBy(user.getEmail());
        colorRepository.save(colors);
        return colorMapper.toColorResponse(colors);
    }

    public ColorResponse update(Integer colorId, ColorRequest colorRequest) {
        Colors color = colorRepository.findById(colorId).orElseThrow(()-> new AppException(ErrorCode.COLOR_NOT_EXISTED));
        colorMapper.updateColor(color, colorRequest);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        color.setUpdatedAt(LocalDateTime.now());
        color.setUpdatedBy(user.getEmail());
        colorRepository.save(color);
        return colorMapper.toColorResponse(color);
    }

    public List<ColorResponse> getAll() {
        return colorRepository.findAll().stream().map(colorMapper::toColorResponse).toList();
    }

    public void delete(Integer colorId) {
        colorRepository.deleteById(colorId);
    }

    public ColorPageResponse findAll(String keyword, String sort, int page, int size) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"colorId");
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

        Page<Colors> entityPage;

        if (StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = colorRepository.searchByKeyword(keyword, pageable);
        }else{
            entityPage = colorRepository.findAll(pageable);
        }

        List<ColorResponse> colorResponseList = entityPage.stream().map(colorMapper::toColorResponse).toList();
        ColorPageResponse colorPageResponse = new ColorPageResponse();
        colorPageResponse.setPageNumber(entityPage.getNumber());
        colorPageResponse.setPageSize(entityPage.getSize());
        colorPageResponse.setTotalElements(entityPage.getTotalElements());
        colorPageResponse.setTotalPages(entityPage.getTotalPages());
        colorPageResponse.setColors(colorResponseList);
        return colorPageResponse;

    }
}
