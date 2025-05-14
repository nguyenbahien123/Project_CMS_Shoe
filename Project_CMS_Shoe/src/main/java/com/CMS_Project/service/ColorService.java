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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    public List<ColorResponse> getAll() {
        return colorRepository.findAll().stream().map(colorMapper::toColorResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Integer colorId) {
        colorRepository.deleteById(colorId);
    }
}
