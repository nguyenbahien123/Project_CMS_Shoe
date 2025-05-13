package com.CMS_Project.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "shoe_variant",
        uniqueConstraints = @UniqueConstraint(columnNames = {"shoe_id", "color_id", "size_id"}))
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoeVariants extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int variantId;

    @ManyToOne()
    @JoinColumn(name = "shoe_id", nullable = false)
    private Shoes shoe;

    @ManyToOne()
    @JoinColumn(name = "color_id", nullable = false)
    private Colors color;

    @ManyToOne()
    @JoinColumn(name = "size_id", nullable = false)
    private Sizes size;

    @Column(nullable = false)
    private Integer quantity;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL)
    private List<Images> images;
}
