package com.CMS_Project.repository;

import com.CMS_Project.entity.OrderDetails;
import com.CMS_Project.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query(value = "SELECT u FROM Orders u WHERE u.isActive = true AND (" +
            "LOWER(u.receivedAddress) LIKE :keyword OR " +
            "LOWER(u.receivedName) LIKE :keyword OR " +
            "LOWER(u.receivedPhone) LIKE :keyword OR " +
            "LOWER(u.createdBy) LIKE :keyword OR " +
            "LOWER(u.updatedBy) LIKE :keyword OR " +
            "LOWER(u.status.statusName) LIKE :keyword OR " +
            "LOWER(u.user.email) LIKE :keyword)")
    Page<Orders> searchByKeyword(String keyword, Pageable pageable);
}
