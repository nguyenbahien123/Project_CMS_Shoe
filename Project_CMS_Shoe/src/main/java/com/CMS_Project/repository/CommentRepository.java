package com.CMS_Project.repository;


import com.CMS_Project.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Integer> {

    @Query(value = "SELECT u FROM Comments u WHERE u.isActive = true AND (" +
            "LOWER(u.createdBy) LIKE :keyword OR " +
            "LOWER(u.updatedBy) LIKE :keyword OR " +
            "LOWER(u.user.email) LIKE :keyword OR " +
            "LOWER(u.content) LIKE :keyword)")
    Page<Comments> searchByKeyword(String keyword, Pageable pageable);

}
