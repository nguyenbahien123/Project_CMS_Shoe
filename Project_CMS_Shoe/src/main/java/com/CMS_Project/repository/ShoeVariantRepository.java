package com.CMS_Project.repository;

import com.CMS_Project.entity.ShoeVariants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeVariantRepository extends JpaRepository<ShoeVariants, Integer> {
    @Query(value = "SELECT u FROM ShoeVariants u WHERE u.isActive = true AND (" +
            "LOWER(u.createdBy) LIKE :keyword OR " +
            "LOWER(u.updatedBy) LIKE :keyword OR " +
            "LOWER(u.color.name) LIKE :keyword OR " +
            "LOWER(u.shoe.name) LIKE :keyword OR " +
            "LOWER(u.shoe.brands.brandName) LIKE :keyword OR " +
            "LOWER(u.shoe.category.categoryName) LIKE :keyword OR " +
            "LOWER(u.size.name) LIKE :keyword)")
    Page<ShoeVariants> searchByKeyword(String keyword, Pageable pageable);
}
