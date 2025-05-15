package com.CMS_Project.controller;


import java.util.List;

import com.CMS_Project.dto.request.PermissionRequest;
import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.BlogPageResponse;
import com.CMS_Project.dto.response.PermissionPageResponse;
import com.CMS_Project.dto.response.PermissionResponse;
import com.CMS_Project.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping("/add")
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest permissionRequest) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(permissionRequest))
                .build();
    }

    @PutMapping("/{permissionId}")
    ApiResponse<PermissionResponse> update(@PathVariable("permissionId") String permissionId,@RequestBody PermissionRequest permissionRequest) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.update(permissionId, permissionRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permissionId}")
    ApiResponse<Void> delete(@PathVariable String permissionId) {
        permissionService.delete(permissionId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/list")
    ApiResponse<PermissionPageResponse> findAll(@RequestParam(required = false) String keyword,
                                                @RequestParam(required = false) String sort,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<PermissionPageResponse>builder()
                .result(permissionService.findAll(keyword,sort,page,size))
                .build();
    }
}
