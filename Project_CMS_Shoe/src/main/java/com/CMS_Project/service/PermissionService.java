package com.CMS_Project.service;

import com.CMS_Project.dto.request.PermissionRequest;
import com.CMS_Project.dto.response.PermissionPageResponse;
import com.CMS_Project.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse create(PermissionRequest permissionRequest);
    PermissionResponse update(String permissionId, PermissionRequest permissionRequest);
    List<PermissionResponse> getAll();
    void delete(String permissionId);
    PermissionPageResponse findAll(String keyword, String sort, int page, int size);
}
