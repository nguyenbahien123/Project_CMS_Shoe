package com.CMS_Project.repository;

import com.CMS_Project.entity.Brands;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandsRepository extends JpaRepository<Brands, Integer> {

    @Query(value = "Select u from Brands u where u.isActive = True And lower(u.brandName) Like :keyword ")
    Page<Brands> searchByKeyword(String keyword, Pageable pageable);


    Optional<Brands> findByBrandName(String brandName);
}
