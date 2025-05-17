package com.CMS_Project.service;

import com.CMS_Project.dto.request.SliderRequest;
import com.CMS_Project.dto.response.SliderPageResponse;
import com.CMS_Project.dto.response.SliderResponse;

import java.util.List;

public interface SliderService {
    SliderResponse create(SliderRequest sliderRequest);
    SliderResponse update(Integer sliderId, SliderRequest sliderRequest);
    List<SliderResponse> getAll();
    void delete(Integer sliderId);
    SliderPageResponse findAll(String keyword, String sort, int page, int size);
}
