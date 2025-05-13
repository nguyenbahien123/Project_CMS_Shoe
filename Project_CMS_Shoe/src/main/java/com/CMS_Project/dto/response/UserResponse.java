package com.CMS_Project.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    int id;
    String username;
    String email;
    String address;
    String phoneNumber;
    RoleResponse roles;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
