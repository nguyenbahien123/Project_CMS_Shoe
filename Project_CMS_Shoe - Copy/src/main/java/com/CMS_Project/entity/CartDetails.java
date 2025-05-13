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
public class CartDetails extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_detail_id")
    private int cartDetailId;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Carts cart;

    @ManyToOne
    @JoinColumn(name = "shoe_variant_id", nullable = false)
    private ShoeVariants shoeVariants;

    @Column(name = "quantity", nullable = false)
    private int quantity = 1;

    @Column(name = "price_shoe", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceShoe;
}
