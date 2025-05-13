package com.CMS_Project.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Roles extends AuditModel{
    @Id
    private String name;

    @Column( unique = true, nullable = false, length = 50)
    private String description;

    @ManyToMany
    Set<Permissions> permissions;
}
