package com.CMS_Project.service;

import com.CMS_Project.dto.request.ColorRequest;
import com.CMS_Project.dto.response.ColorPageResponse;
import com.CMS_Project.dto.response.ColorResponse;

import java.util.List;

public interface ColorService {
    ColorResponse create(ColorRequest colorRequest);
    ColorResponse update(Integer colorId, ColorRequest colorRequest);
    List<ColorResponse> getAll();
    void delete(Integer colorId);
    ColorPageResponse findAll(String keyword, String sort, int page, int size);
}
