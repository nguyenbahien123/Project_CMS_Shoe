package com.CMS_Project.dto.response;

import com.CMS_Project.entity.Images;
import com.CMS_Project.entity.Orders;
import com.CMS_Project.entity.ShoeVariants;
import com.CMS_Project.entity.Shoes;
import jakarta.persistence.*;
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
     Orders order;
     Shoes shoes;
     ShoeVariants shoeVariants;
     Images images;
     int quantity;
     BigDecimal priceShoe;
}
