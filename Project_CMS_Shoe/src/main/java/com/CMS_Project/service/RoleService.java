package com.CMS_Project.service;

import com.CMS_Project.dto.request.RoleRequest;
import com.CMS_Project.dto.response.RolePageResponse;
import com.CMS_Project.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest roleRequest);
    List<RoleResponse> getAll();
    void delete(String roleId);
    RolePageResponse findAll(String keyword, String sort, int page, int size);
}
