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
public class Carts extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int cartId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "total_amount", precision = 10, scale = 2 )
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "shoe_variant_id", nullable = false)
    private ShoeVariants shoeVariants;

}
