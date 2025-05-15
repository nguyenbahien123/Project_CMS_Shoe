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

    @Query(value= "select u from Colors u where u.isActive=true AND u.name LIKE:keyword")
    Page<Colors> searchByKeyword(String keyword, Pageable pageable);

    Optional<Colors> findByName(String colorName);
}
