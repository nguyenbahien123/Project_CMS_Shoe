package com.CMS_Project.controller;

import java.util.List;

import com.CMS_Project.dto.request.RoleRequest;
import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.RolePageResponse;
import com.CMS_Project.dto.response.RoleResponse;
import com.CMS_Project.service.RoleService;
import com.CMS_Project.service.impl.RoleServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest roleRequest) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(roleRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{roleId}")
    ApiResponse<Void> delete(@PathVariable String roleId) {
        roleService.delete(roleId);
        return ApiResponse.<Void>builder().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    ApiResponse<RolePageResponse> findAll(@RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) String sort,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<RolePageResponse>builder()
                .result(roleService.findAll(keyword,sort,page,size))
                .build();
    }
}
