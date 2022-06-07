package com.example.store_automation.model.dto;

import com.example.store_automation.model.entity.Branch;

import javax.persistence.*;
import java.time.LocalDate;

public class DailyIncomeDto {


    private Long id;

    private LocalDate date;

    private Double dailyIncome;

    private BranchDto branchDto;
}
