package com.CMS_Project.dto.response;

import com.CMS_Project.entity.Images;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailsResponse {
     int orderDetailId;
     OrderResponse order;
     ShoeResponse shoes;
     ShoeVariantResponse shoeVariants;
     ImageResponse images;
     int quantity;
     BigDecimal priceShoe;
     LocalDateTime createdAt;
     LocalDateTime updatedAt;
     String createdBy;
     String updatedBy;
     Boolean isActive;
}
