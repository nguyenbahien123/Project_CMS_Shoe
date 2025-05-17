package com.CMS_Project.service;

import com.CMS_Project.dto.request.BrandRequest;
import com.CMS_Project.dto.response.BrandPageResponse;
import com.CMS_Project.dto.response.BrandResponse;

import java.util.List;

public interface BrandService {
    BrandResponse create(BrandRequest brandRequest);
    BrandResponse update(Integer brandId, BrandRequest brandRequest);
    List<BrandResponse> getAll();
    void delete(Integer brandId);
    BrandPageResponse findAll(String keyword, String sort, int page, int size);
}
