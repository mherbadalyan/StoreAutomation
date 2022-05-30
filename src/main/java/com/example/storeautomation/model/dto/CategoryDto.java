package com.example.storeautomation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long id;

    private String categoryName;
}
