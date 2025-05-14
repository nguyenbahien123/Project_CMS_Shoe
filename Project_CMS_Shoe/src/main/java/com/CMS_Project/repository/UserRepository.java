package com.CMS_Project.repository;


import com.CMS_Project.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Boolean existsByEmail(String email);
    Optional<Users> findByEmail(String email);
}
