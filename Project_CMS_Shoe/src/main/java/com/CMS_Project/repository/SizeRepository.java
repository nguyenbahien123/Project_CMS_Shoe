package com.CMS_Project.repository;

import com.CMS_Project.entity.Blogs;
import com.CMS_Project.entity.Images;
import com.CMS_Project.entity.Sizes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Sizes, Integer> {

    @Query(value = "SELECT u FROM Sizes u WHERE u.isActive = true AND (" +
            "LOWER(u.createdBy) LIKE :keyword OR " +
            "LOWER(u.updatedBy) LIKE :keyword OR " +
            "LOWER(u.name) LIKE :keyword)")
    Page<Sizes> searchByKeyword(String keyword, Pageable pageable);

    Optional<Sizes> findByName(String nameSize);
}
