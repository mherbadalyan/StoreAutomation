package com.example.store_automation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    private String productName;

    private Long price;

    private CategoryDto categoryDto;
}
