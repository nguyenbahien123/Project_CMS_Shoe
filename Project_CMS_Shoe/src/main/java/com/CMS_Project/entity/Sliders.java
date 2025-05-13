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
public class Sliders extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slider_id")
    private int sliderId;

    @ManyToOne
    @JoinColumn(name = "shoe_id", nullable = false)
    private Shoes shoe;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "description")
    private String description;
}
