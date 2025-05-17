package com.CMS_Project.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Images extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    @ManyToOne()
    @JoinColumn(name = "variant_id", nullable = false)
    private ShoeVariants variant;


    private String imagePath;
}
