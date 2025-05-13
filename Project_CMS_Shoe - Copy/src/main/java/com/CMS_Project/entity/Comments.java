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
public class Comments extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="shoeId", nullable = false)
    private Shoes shoe;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

}
