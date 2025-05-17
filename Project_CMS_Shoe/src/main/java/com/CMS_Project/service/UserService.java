package com.CMS_Project.service;

import com.CMS_Project.dto.request.UserCreationRequest;
import com.CMS_Project.dto.request.UserUpdateRequest;
import com.CMS_Project.dto.response.UserPageResponse;
import com.CMS_Project.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreationRequest request);
    List<UserResponse> getAll();
    UserResponse updateUser(int userId, UserUpdateRequest request);
    void deleteUser(int userId);
    UserPageResponse findAll(String keyword, String sort, int page, int size);
}
