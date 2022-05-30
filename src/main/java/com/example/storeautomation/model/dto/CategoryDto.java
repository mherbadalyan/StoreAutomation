package com.example.storeautomation.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class CategoryDto {

    private Long id;

    private String categoryName;
}
