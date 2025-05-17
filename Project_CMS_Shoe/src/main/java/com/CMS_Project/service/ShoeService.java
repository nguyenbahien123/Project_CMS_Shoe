package com.CMS_Project.service;

import com.CMS_Project.dto.request.ShoeRequest;
import com.CMS_Project.dto.response.ShoePageResponse;
import com.CMS_Project.dto.response.ShoeResponse;

import java.util.List;

public interface ShoeService {
    ShoeResponse create(ShoeRequest shoeRequest);
    ShoeResponse update(Integer shoeId, ShoeRequest shoeRequest);
    List<ShoeResponse> getAll();
    void delete(Integer shoeId);
    ShoePageResponse findAll(String keyword, String sort, int page, int size);
}
