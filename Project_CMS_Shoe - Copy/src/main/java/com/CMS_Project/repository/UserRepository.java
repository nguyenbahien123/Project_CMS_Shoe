package com.CMS_Project.repository;


import com.CMS_Project.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {


    @Query(value = "SELECT u FROM Users u WHERE u.isActive = true AND (" +
            "LOWER(u.username) LIKE :keyword OR " +
            "LOWER(u.email) LIKE :keyword OR " +
            "LOWER(u.address) LIKE :keyword OR " +
            "LOWER(u.phoneNumber) LIKE :keyword)")
    Page<Users> searchByKeyword(String keyword, Pageable pageable);

    Boolean existsByEmail(String email);
    Optional<Users> findByEmail(String email);
}
