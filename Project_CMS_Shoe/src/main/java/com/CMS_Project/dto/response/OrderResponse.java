package com.CMS_Project.dto.response;

import com.CMS_Project.entity.OrderStatuses;
import com.CMS_Project.entity.Users;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
     int orderId;
     Users user;
     String receivedName;
     String receivedPhone;
     String receivedAddress;
     OrderStatuses status;
     BigDecimal totalPrice;
}
