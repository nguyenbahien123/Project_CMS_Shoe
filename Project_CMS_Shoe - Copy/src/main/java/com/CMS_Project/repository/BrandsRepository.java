package com.CMS_Project.repository;

import com.CMS_Project.entity.Blogs;
import com.CMS_Project.entity.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends JpaRepository<Brands, Integer> {
}
