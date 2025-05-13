package com.CMS_Project.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SizeResponse {
    int sizeId;
    String name;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;
    Boolean isActive;
}
