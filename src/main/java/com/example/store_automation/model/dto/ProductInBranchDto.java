package com.example.store_automation.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ProductInBranchDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Integer quantity;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime date;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ProductDto productDto;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BranchDto branchDto;
}
