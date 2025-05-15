package com.CMS_Project.repository;


import com.CMS_Project.entity.Blogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blogs, Integer> {

    @Query(value = "SELECT u FROM Blogs u WHERE u.isActive = true AND (" +
            "LOWER(u.title) LIKE :keyword OR " +
            "LOWER(u.imageUrl) LIKE :keyword OR " +
            "LOWER(u.content) LIKE :keyword OR " +
            "LOWER(u.createdBy) LIKE :keyword OR " +
            "LOWER(u.updatedBy) LIKE :keyword OR " +
            "LOWER(u.shortDescription) LIKE :keyword)")
    Page<Blogs> searchByKeyword(String keyword, Pageable pageable);
}
