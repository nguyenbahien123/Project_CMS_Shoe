package com.CMS_Project.repository;

import com.CMS_Project.entity.Blogs;
import com.CMS_Project.entity.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Integer> {

    @Query(value = "SELECT u FROM Categories u WHERE u.isActive = true AND (" +
            "LOWER(u.createdBy) LIKE :keyword OR " +
            "LOWER(u.updatedBy) LIKE :keyword OR " +
            "LOWER(u.categoryName) LIKE :keyword)")
    Page<Categories> searchByKeyword(String keyword, Pageable pageable);

    Optional<Categories> findByCategoryName(String categoryName);
}
