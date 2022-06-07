package com.example.store_automation.repository;

import com.example.store_automation.model.entity.ProductInBranch;
import com.example.store_automation.model.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales,Long> {

        @Query("SELECT sl FROM Sales sl " +
                "WHERE sl.branch.id = :branchId " +
                "and sl.salesDate between :startDate and :endDate")
        Optional<List<Sales>> findSalesByBranch(@Param("branchId") Long branchId,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);
}
