package com.CMS_Project.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shoes extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shoeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brands brands;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;

    @Column(name = "discount")
    private int discount;

    @Column(name = "short_description", length = 255)
    private String shortDescription;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
