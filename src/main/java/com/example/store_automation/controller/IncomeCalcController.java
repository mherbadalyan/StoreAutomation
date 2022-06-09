package com.example.store_automation.controller;

import com.example.store_automation.model.entity.Branch;
import com.example.store_automation.response.EntityLookupResponse;
import com.example.store_automation.service.BranchService;
import com.example.store_automation.service.IncomeCalcService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/store-automation/incomeCalc")
@SecurityRequirement(name = "store_automation")
@AllArgsConstructor
public class IncomeCalcController {

    private static final Logger logger = LoggerFactory.getLogger(IncomeCalcController.class);
    private final IncomeCalcService incomeCalcService;

    private final BranchService branchService;

    @Operation(summary = "Get income by branch id and date",
            description = "Getting income by branch id and date"
    )
    @GetMapping("/{branchId}/{startDate}/{endDate}")
    private ResponseEntity<?> getIncomeByDays(@PathVariable("branchId") Long branchId,
                                              @PathVariable("startDate")
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                              @PathVariable("endDate")
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        logger.info("Received request to get income in branch with id " + branchId +
                "between date " + startDate + " - " + endDate);

        if (!branchService.existById(branchId)) {
            logger.warn("There is not branch with id = " + branchId);
            return new EntityLookupResponse<Branch>().onFailure("Branch");
        }

        Optional<Double> opIncome = incomeCalcService.getIncomeByBranchAndDate(branchId,
                startDate,
                endDate);

        if (opIncome.isEmpty()) {
            logger.error("There is not income in selected period of date");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("There is not income in selected period of date.");
        }

        logger.info("Income in branch with id " + branchId +
                " between date " + startDate + " - " + endDate + " = " + String.valueOf(opIncome.get()));
        return ResponseEntity.status(HttpStatus.OK).body(opIncome.get());
    }
}
