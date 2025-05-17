package com.CMS_Project.service;

import com.CMS_Project.dto.request.ImageRequest;
import com.CMS_Project.dto.response.ImagePageResponse;
import com.CMS_Project.dto.response.ImageResponse;

import java.util.List;

public interface ImageService {
    ImageResponse create(int variant, ImageRequest imageRequest);
    ImageResponse update(Integer imageId, ImageRequest imageRequest);
    List<ImageResponse> getAll();
    ImagePageResponse findAll(String keyword, String sort, int page, int size);
    void delete(Integer imageId);
}
