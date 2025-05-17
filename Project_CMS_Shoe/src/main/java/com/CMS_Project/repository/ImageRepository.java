package com.CMS_Project.repository;

import com.CMS_Project.entity.Images;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Images, Integer> {

    @Query(value = "SELECT u FROM Images u WHERE u.isActive = true AND (" +
            "LOWER(u.createdBy) LIKE :keyword OR " +
            "LOWER(u.updatedBy) LIKE :keyword OR " +
            "LOWER(u.variant.shoe.name) LIKE :keyword OR " +
            "LOWER(u.imagePath) LIKE :keyword)")
    Page<Images> searchByKeyword(String keyword, Pageable pageable);
}
