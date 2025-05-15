package com.CMS_Project.dto.request;

import com.CMS_Project.entity.Shoes;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SliderRequest {

     String shoe;
     String imageUrl;
     String title;
     String description;
}
