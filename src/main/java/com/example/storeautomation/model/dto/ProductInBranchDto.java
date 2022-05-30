package com.example.storeautomation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@Builder
public class ProductInBranchDto {

    private Long id;

    private Integer quantity;

    private Date date;

    private ProductDto productDto;

    private BranchDto branchDto;
}
