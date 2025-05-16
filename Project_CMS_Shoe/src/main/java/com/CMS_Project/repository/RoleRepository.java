package com.CMS_Project.repository;


import com.CMS_Project.entity.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, String> {
    @Query(value = "SELECT u FROM Roles u WHERE u.isActive = true AND (" +
            "LOWER(u.createdBy) LIKE :keyword OR " +
            "LOWER(u.updatedBy) LIKE :keyword OR " +
            "LOWER(u.name) LIKE :keyword OR " +
            "LOWER(u.description) LIKE :keyword)")
    Page<Roles> searchByKeyword(String keyword, Pageable pageable);
}
