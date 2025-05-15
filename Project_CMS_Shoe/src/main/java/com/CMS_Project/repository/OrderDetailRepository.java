package com.CMS_Project.repository;

import com.CMS_Project.entity.Blogs;
import com.CMS_Project.entity.OrderDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Integer> {
    @Query(value = "SELECT u FROM OrderDetails u WHERE u.isActive = true AND (" +
            "LOWER(u.images.url) LIKE :keyword OR " +
            "LOWER(u.order.receivedName) LIKE :keyword OR " +
            "LOWER(u.order.receivedAddress) LIKE :keyword OR " +
            "LOWER(u.createdBy) LIKE :keyword OR " +
            "LOWER(u.updatedBy) LIKE :keyword OR " +
            "LOWER(u.shoes.name) LIKE :keyword)")
    Page<OrderDetails> searchByKeyword(String keyword, Pageable pageable);
}
