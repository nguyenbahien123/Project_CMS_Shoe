package com.CMS_Project.service;

import com.CMS_Project.dto.request.ShoeVariantRequest;
import com.CMS_Project.dto.response.ShoeVariantPageResponse;
import com.CMS_Project.dto.response.ShoeVariantResponse;

import java.util.List;

public interface ShoeVariantService {
    ShoeVariantResponse create(ShoeVariantRequest shoeVariantRequest);
    ShoeVariantResponse update(Integer variantId, ShoeVariantRequest shoeVariantRequest);
    List<ShoeVariantResponse> getAll();
    void delete(Integer variantId);
    ShoeVariantPageResponse findAll(String keyword, String sort, int page, int size);
}
