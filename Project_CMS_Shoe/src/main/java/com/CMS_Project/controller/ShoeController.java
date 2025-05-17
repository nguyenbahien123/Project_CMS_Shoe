package com.CMS_Project.controller;


import com.CMS_Project.dto.request.ShoeRequest;
import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.ShoePageResponse;
import com.CMS_Project.dto.response.ShoeResponse;
import com.CMS_Project.service.ShoeService;
import com.CMS_Project.service.impl.ShoeServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/shoes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShoeController {

    ShoeService shoeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    ApiResponse<ShoeResponse> create(@RequestBody ShoeRequest shoeRequest){
        return ApiResponse.<ShoeResponse>builder()
                .result(shoeService.create(shoeRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{shoeId}")
    ApiResponse<ShoeResponse> update(@PathVariable Integer shoeId, @RequestBody ShoeRequest shoeRequest){
        return ApiResponse.<ShoeResponse>builder()
                .result(shoeService.update(shoeId,shoeRequest))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<ShoeResponse>> getAll() {
        return ApiResponse.<List<ShoeResponse>>builder()
                .result(shoeService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{shoeId}")
    ApiResponse<Void> delete(@PathVariable Integer shoeId) {
        shoeService.delete(shoeId);
        return ApiResponse.<Void>builder().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    ApiResponse<ShoePageResponse> findAll(@RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) String sort,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<ShoePageResponse>builder()
                .result(shoeService.findAll(keyword,sort,page,size))
                .build();
    }
}
