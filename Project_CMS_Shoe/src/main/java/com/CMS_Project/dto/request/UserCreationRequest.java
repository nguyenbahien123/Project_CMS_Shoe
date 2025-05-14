package com.CMS_Project.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
     @Size(min = 3, message = "USERNAME_INVALID")
     String username;
     @Size(min = 6, message = "INVALID_PASSWORD")
     String passwordHash;
     String email;
     String address;
     String phoneNumber;
}
