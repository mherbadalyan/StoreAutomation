package com.example.store_automation.service;

import com.example.store_automation.model.entity.Branch;
import com.example.store_automation.model.entity.DailyIncome;
import com.example.store_automation.model.entity.Sales;
import com.example.store_automation.repository.BranchRepository;
import com.example.store_automation.repository.DailyIncomeRepository;
import com.example.store_automation.repository.SalesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IncomeCalcService {

    private final BranchRepository branchRepository;

    private final SalesRepository salesRepository;

    private final DailyIncomeRepository dailyIncomeRepository;

    public void calcDailyIncome() {

        List<Branch> allBranches = branchRepository.getBranches();
        DailyIncome dailyIncome;

        LocalDateTime startDate = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime endDate = LocalDate.now().atStartOfDay();

        double dailyIncomeResult;

        for (Branch branch : allBranches) {

            Optional<List<Sales>> salesByBranch = salesRepository.findSalesByBranch(branch.getId(),
                                                                                    startDate,
                                                                                    endDate);
            if (salesByBranch.isEmpty()) {
                continue;
            }

            dailyIncomeResult = 0.0;

            for (Sales sale : salesByBranch.get()) {
                Double price = sale.getPrice();
                double priceIn = sale.getPriceIn() * sale.getQuantity();
                dailyIncomeResult += (price - priceIn);
            }

            dailyIncome = new DailyIncome();
            dailyIncome.setDailyIncome(dailyIncomeResult);
            dailyIncome.setBranch(branch);
            dailyIncome.setDate(LocalDate.now().minusDays(1));

            dailyIncomeRepository.save(dailyIncome);
        }
    }

    public Optional<Double> getIncomeByBranchAndDate(Long branchId, LocalDate startDate, LocalDate endDate) {
        List<DailyIncome> dailyIncomes = dailyIncomeRepository.getIncomeByBranchBetweenDates(branchId,
                                                                                        startDate,
                                                                                        endDate);

        if (dailyIncomes.isEmpty()) {
            return Optional.empty();
        }

        double income = 0.0;

        for (DailyIncome dailyIncome : dailyIncomes) {
            income += dailyIncome.getDailyIncome();
        }

        return Optional.of(income);
    }

    public void changeIncomeInDateByBranchId(Double incomeToReturn, LocalDate saleDate, Long id) {

        Optional<Branch> branchFromData = branchRepository.findById(id);

        if (branchFromData.isEmpty()) {
            return;
        }
        Optional<DailyIncome> dailyIncome = dailyIncomeRepository.getDailyIncomeByBranchAndDate(branchFromData.get(), saleDate);

        if (dailyIncome.isEmpty()) {
            return;
        }

        Double newIncome = dailyIncome.get().getDailyIncome() - incomeToReturn;

        dailyIncome.get().setDailyIncome(newIncome);
        dailyIncomeRepository.save(dailyIncome.get());
    }
}
