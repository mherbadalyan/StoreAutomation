package com.example.store_automation.repository;

import com.example.store_automation.model.entity.ProductInBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductInBranchRepository extends JpaRepository<ProductInBranch, Long> {

    Optional<List<ProductInBranch>> findProductInBranchesByProductId(Long id);


    @Query("SELECT pr FROM ProductInBranch pr " +
            "WHERE pr.branch.id = :branchId " +
            "and pr.product.id = :productId " +
            "and pr.date = :date " +
            "and pr.expDate = :expDate and pr.priceIn = :priceIn")
    Optional<ProductInBranch>  findUniqueProduct(@Param("branchId") Long branchId,
                                                 @Param("productId") Long productId,
                                                 @Param("date") LocalDate date,
                                                 @Param("expDate")LocalDate expDate,
                                                 @Param("priceIn")Double priceIn);

    @Query("SELECT pr FROM ProductInBranch pr " +
            "WHERE pr.quantity = 0 " +
            "and pr.zeroDate = :zeroDate")
    List<ProductInBranch> getProductsListToRemove(@Param("zeroDate") LocalDate zeroDate);
}
