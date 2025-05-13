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
public class Orders extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "received_name", nullable = false, length = 100)
    private String receivedName;

    @Column(name = "received_phone", nullable = false, length = 20)
    private String receivedPhone;

    @Column(name = "received_address", nullable = false, columnDefinition = "NVARCHAR(4000)")
    private String receivedAddress;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private OrderStatuses status;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

}
