package com.CMS_Project.repository;

import com.CMS_Project.entity.Blogs;
import com.CMS_Project.entity.Comments;
import com.CMS_Project.entity.Sliders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SliderRepository extends JpaRepository<Sliders, Integer> {

    @Query(value = "SELECT u FROM Sliders u WHERE u.isActive = true AND (" +
            "LOWER(u.createdBy) LIKE :keyword OR " +
            "LOWER(u.updatedBy) LIKE :keyword OR " +
            "LOWER(u.shoe.name) LIKE :keyword OR " +
            "LOWER(u.description) LIKE :keyword OR " +
            "LOWER(u.title) LIKE :keyword OR " +
            "LOWER(u.imageUrl) LIKE :keyword)")
    Page<Sliders> searchByKeyword(String keyword, Pageable pageable);

    Optional<Sliders> findByTitle(String title);
}
