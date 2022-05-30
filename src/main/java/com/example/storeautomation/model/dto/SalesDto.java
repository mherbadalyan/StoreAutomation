package com.example.storeautomation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
@Data
@AllArgsConstructor
@Builder
public class SalesDto {

    private Long id;

    private Integer quantity;

    private Date salesDate;

    private Long price;

    private BranchDto branchDto;

    private ProductDto productDto;
}
