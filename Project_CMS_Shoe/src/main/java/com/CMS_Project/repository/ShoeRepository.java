package com.CMS_Project.repository;

import com.CMS_Project.entity.Blogs;
import com.CMS_Project.entity.Images;
import com.CMS_Project.entity.Shoes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoeRepository extends JpaRepository<Shoes, Integer> {

    @Query(value = "SELECT u FROM Shoes u WHERE u.isActive = true AND (" +
            "LOWER(u.createdBy) LIKE :keyword OR " +
            "LOWER(u.updatedBy) LIKE :keyword OR " +
            "LOWER(u.name) LIKE :keyword OR " +
            "LOWER(u.shortDescription) LIKE :keyword OR " +
            "LOWER(u.brands.brandName) LIKE :keyword OR " +
            "LOWER(u.category.categoryName) LIKE :keyword OR " +
            "LOWER(u.description) LIKE :keyword)")
    Page<Shoes> searchByKeyword(String keyword, Pageable pageable);

    Optional<Shoes> findByName(String shoeName);
}
