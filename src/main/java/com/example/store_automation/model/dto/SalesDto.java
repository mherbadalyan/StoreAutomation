package com.example.store_automation.model.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data

@Builder
public class SalesDto {

    private Long id;

    private Integer quantity;

    private LocalDateTime salesDate;

    private Double price;

    private BranchDto branchDto;

    private ProductDto productDto;
}
