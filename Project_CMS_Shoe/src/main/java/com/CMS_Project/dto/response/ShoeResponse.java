package com.CMS_Project.dto.response;

import com.CMS_Project.entity.Brands;
import com.CMS_Project.entity.Categories;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoeResponse {
    int shoeId;
    BrandResponse brands;
    String name;
    String description;
    CategoryResponse category;
    int discount;
    String shortDescription;
    BigDecimal price;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;
    Boolean isActive;
}
