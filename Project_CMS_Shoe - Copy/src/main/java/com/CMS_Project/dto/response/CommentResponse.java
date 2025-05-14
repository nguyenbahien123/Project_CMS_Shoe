package com.CMS_Project.dto.response;

import com.CMS_Project.entity.OrderStatuses;
import com.CMS_Project.entity.Shoes;
import com.CMS_Project.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
     int id;
     ShoeResponse shoe;
     UserResponse user;
     String content;
}
