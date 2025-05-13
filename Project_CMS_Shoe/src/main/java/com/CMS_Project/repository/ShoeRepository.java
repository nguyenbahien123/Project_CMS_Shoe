package com.CMS_Project.repository;

import com.CMS_Project.entity.Blogs;
import com.CMS_Project.entity.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeRepository extends JpaRepository<Shoes, Integer> {
}
