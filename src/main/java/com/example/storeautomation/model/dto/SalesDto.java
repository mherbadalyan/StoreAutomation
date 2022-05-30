package com.example.storeautomation.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
@Data
@RequiredArgsConstructor
@Builder
public class SalesDto {

    private Long id;

    private Integer quantity;

    private Date salesDate;

    private Long price;

    private BranchDto branchDto;

    private ProductDto productDto;
}
