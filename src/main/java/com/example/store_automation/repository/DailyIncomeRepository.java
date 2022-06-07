package com.example.store_automation.repository;

import com.example.store_automation.model.entity.Branch;
import com.example.store_automation.model.entity.DailyIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.desktop.OpenFilesEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyIncomeRepository extends JpaRepository<DailyIncome,Long> {

    @Query("SELECT inc FROM DailyIncome inc " +
            "WHERE inc.branch.id = :branchId " +
            "AND inc.date BETWEEN :startDate AND :endDate" )
    List<DailyIncome> getIncomeByBranchBetweenDates(@Param("branchId") Long branchId,
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);

    Optional<DailyIncome> getDailyIncomeByBranchAndDate(Branch branch, LocalDate date);
}
