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
public class OrderDetails extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private int orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "shoe_id", nullable = false)
    private Shoes shoes;

    @ManyToOne
    @JoinColumn(name = "shoe_variant_id")
    private ShoeVariants shoeVariants;

    @ManyToOne
    @JoinColumn(name = "shoe_image_id")
    private Images images;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price_shoe", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceShoe;
}
