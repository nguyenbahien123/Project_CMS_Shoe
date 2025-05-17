package com.CMS_Project.service;

import com.CMS_Project.dto.request.SizeRequest;
import com.CMS_Project.dto.response.SizePageResponse;
import com.CMS_Project.dto.response.SizeResponse;

import java.util.List;

public interface SizeService {
    SizeResponse create(SizeRequest sizeRequest);
    SizeResponse update(Integer sizeId, SizeRequest sizeRequest);
    List<SizeResponse> getAll();
    void delete(Integer sizeId);
    SizePageResponse findAll(String keyword, String sort, int page, int size);
}
