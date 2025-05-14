package com.CMS_Project.repository;

import com.CMS_Project.entity.OrderStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatuses, Integer> {

    Optional<OrderStatuses> findByStatusName(String statusName);
}
