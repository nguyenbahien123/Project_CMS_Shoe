package com.CMS_Project.repository;

import com.CMS_Project.entity.ShoeVariants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeVariantRepository extends JpaRepository<ShoeVariants, Integer> {
}
