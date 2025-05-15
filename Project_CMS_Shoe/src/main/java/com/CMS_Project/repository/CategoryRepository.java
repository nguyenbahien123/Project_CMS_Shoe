package com.CMS_Project.repository;

import com.CMS_Project.entity.Blogs;
import com.CMS_Project.entity.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Integer> {

    @Query(value= "select u from Categories u where u.isActive=true AND u.categoryName LIKE:keyword")
    Page<Categories> searchByKeyword(String keyword, Pageable pageable);

    Optional<Categories> findByCategoryName(String categoryName);
}
