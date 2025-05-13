package com.CMS_Project.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailsResponse {
     int orderDetailId;
     int orderId;
     int shoeId;
     int quantity;
     BigDecimal priceShoe;
}
