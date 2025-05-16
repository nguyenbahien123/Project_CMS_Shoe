package com.CMS_Project.controller;

import com.CMS_Project.dto.request.UserCreationRequest;
import com.CMS_Project.dto.request.UserUpdateRequest;
import com.CMS_Project.dto.response.ApiResponse;
import com.CMS_Project.dto.response.UserPageResponse;
import com.CMS_Project.dto.response.UserResponse;
import com.CMS_Project.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping("/add")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers(){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAll())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    ApiResponse<UserPageResponse> findAll(@RequestParam(required = false) String keyword,
                                                @RequestParam(required = false) String sort,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size){
        return ApiResponse.<UserPageResponse>builder()
                .result(userService.findAll(keyword,sort,page,size))
                .build();
    }


    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable Integer userId, @RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .result("User has been deleted")
                .build();
    }
}
