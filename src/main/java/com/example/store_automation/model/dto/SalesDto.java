package com.example.store_automation.model.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
@Data

@Builder
public class SalesDto {

    private Long id;

    private Integer quantity;

    private Date salesDate;

    private Long price;

    private BranchDto branchDto;

    private ProductDto productDto;
}
