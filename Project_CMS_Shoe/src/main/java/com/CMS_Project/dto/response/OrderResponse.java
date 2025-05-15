package com.CMS_Project.dto.response;

import com.CMS_Project.entity.OrderStatuses;
import com.CMS_Project.entity.Users;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
     int orderId;
     UserResponse user;
     String receivedName;
     String receivedPhone;
     String receivedAddress;
     OrderStatusResponse status;
     BigDecimal totalPrice;
     LocalDateTime createdAt;
     LocalDateTime updatedAt;
     String createdBy;
     String updatedBy;
     Boolean isActive;
}
