package com.example.store_automation.repository;

import com.example.store_automation.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsByProductNameAndPrice(String productName,Long price);

    Optional<List<Product>> findProductsByCategoryId(Long id);
}
