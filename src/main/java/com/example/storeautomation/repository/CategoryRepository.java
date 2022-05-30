package com.example.storeautomation.repository;

import com.example.storeautomation.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Transactional
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByCategoryName(String name);
    Optional<Category> deleteCategoryByCategoryName(String name);
    boolean existsCategoryByCategoryName(String name);
    void deleteByCategoryName(String name);


}
