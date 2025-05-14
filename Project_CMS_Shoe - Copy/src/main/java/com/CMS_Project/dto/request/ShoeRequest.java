package com.CMS_Project.dto.request;

import com.CMS_Project.entity.Brands;
import com.CMS_Project.entity.Categories;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoeRequest {
     String brands;
     String name;
     String description;
     String category;
     int discount;
     String shortDescription;
     BigDecimal price;
}
