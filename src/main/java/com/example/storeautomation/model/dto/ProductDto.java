package com.example.storeautomation.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    private String productName;

    private Long price;

    private CategoryDto categoryDto;
}
