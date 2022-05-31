package com.example.store_automation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
@Builder
public class ProductInBranchDto {

    private Long id;

    private Integer quantity;

    private Date date;

    private Long priceToSale;

    private ProductDto productDto;

    private BranchDto branchDto;
}
