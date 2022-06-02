package com.example.store_automation.repository;

import com.example.store_automation.model.entity.ProductInBranch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductInBranchRepository extends JpaRepository<ProductInBranch,Long> {

    Optional<List<ProductInBranch>> findProductInBranchesByProductId(Long id);
}
