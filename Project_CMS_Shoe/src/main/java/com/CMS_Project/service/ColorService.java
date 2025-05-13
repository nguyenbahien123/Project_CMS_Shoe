package com.CMS_Project.service;




import com.CMS_Project.dto.request.ColorRequest;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        Users user = userRepository.findById(1).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        colors.setCreatedBy(user.getEmail());
        colors.setUpdatedBy(user.getEmail());
        colorRepository.save(colors);
        return colorMapper.toColorResponse(colors);
    }

    public ColorResponse update(Integer colorId, ColorRequest colorRequest) {
        Colors color = colorRepository.findById(colorId).orElseThrow(()-> new AppException(ErrorCode.COLOR_NOT_EXISTED));
        colorMapper.updateColor(color, colorRequest);
        Users user = userRepository.findById(1).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        color.setUpdatedAt(LocalDateTime.now());
        color.setUpdatedBy(user.getEmail());
        colorRepository.save(color);
        return colorMapper.toColorResponse(color);
    }

    public List<ColorResponse> getAll() {
        return colorRepository.findAll().stream().map(colorMapper::toColorResponse).toList();
    }

    public void delete(Integer sizeId) {
        colorRepository.deleteById(sizeId);
    }
}
