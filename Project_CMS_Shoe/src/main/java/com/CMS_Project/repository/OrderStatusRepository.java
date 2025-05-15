package com.CMS_Project.repository;

import com.CMS_Project.entity.Comments;
import com.CMS_Project.entity.OrderStatuses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatuses, Integer> {

    @Query(value = "SELECT u FROM OrderStatuses u WHERE u.isActive = true AND (" +
            "LOWER(u.createdBy) LIKE :keyword OR " +
            "LOWER(u.updatedBy) LIKE :keyword OR " +
            "LOWER(u.statusName) LIKE :keyword)")
    Page<OrderStatuses> searchByKeyword(String keyword, Pageable pageable);

    Optional<OrderStatuses> findByStatusName(String statusName);
}
