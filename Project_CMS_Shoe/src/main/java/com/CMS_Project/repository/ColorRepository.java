package com.CMS_Project.repository;

import com.CMS_Project.entity.Colors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Colors, Integer> {

    @Query(value = "SELECT u FROM Colors u WHERE u.isActive = true AND (" +
            "LOWER(u.createdBy) LIKE :keyword OR " +
            "LOWER(u.updatedBy) LIKE :keyword OR " +
            "LOWER(u.name) LIKE :keyword)")
    Page<Colors> searchByKeyword(String keyword, Pageable pageable);

    Optional<Colors> findByName(String colorName);
}
