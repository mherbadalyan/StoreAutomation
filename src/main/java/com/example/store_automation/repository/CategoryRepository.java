package com.example.store_automation.repository;

import com.example.store_automation.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Transactional
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByCategoryName(String name);
    boolean existsCategoryByCategoryName(String name);
    void deleteByCategoryName(String name);
}
