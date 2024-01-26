package com.hihi.square.domain.category.repository;

import com.hihi.square.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT * FROM category ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Category> findRandomCategory();
}
