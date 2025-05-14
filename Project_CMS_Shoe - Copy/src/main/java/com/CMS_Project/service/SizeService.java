package com.CMS_Project.service;





import com.CMS_Project.dto.request.SizeRequest;
import com.CMS_Project.dto.response.SizeResponse;
import com.CMS_Project.entity.Sizes;
import com.CMS_Project.entity.Users;
import com.CMS_Project.exception.AppException;
import com.CMS_Project.exception.ErrorCode;


import com.CMS_Project.mapper.SizeMapper;
import com.CMS_Project.repository.SizeRepository;
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
public class SizeService {

    SizeRepository sizeRepository;
    UserRepository userRepository;
    SizeMapper sizeMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public SizeResponse create(SizeRequest sizeRequest) {
        Sizes size = sizeMapper.toSize(sizeRequest);
        size.setCreatedAt(LocalDateTime.now());
        size.setUpdatedAt(LocalDateTime.now());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        size.setCreatedBy(user.getEmail());
        size.setUpdatedBy(user.getEmail());
        sizeRepository.save(size);
        return sizeMapper.toSizeResponse(size);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public SizeResponse update(Integer sizeId, SizeRequest sizeRequest) {
        Sizes sizes = sizeRepository.findById(sizeId).orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_EXISTED));
        sizeMapper.updateSize(sizes, sizeRequest);
        sizes.setUpdatedAt(LocalDateTime.now());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        sizes.setUpdatedBy(users.getEmail());
        sizeRepository.save(sizes);
        return sizeMapper.toSizeResponse(sizeRepository.save(sizes));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<SizeResponse> getAll() {
        return sizeRepository.findAll().stream().map(sizeMapper::toSizeResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Integer sizeId) {
        sizeRepository.deleteById(sizeId);
    }
}
