package com.example.store_automation.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(value = {"productDto","branchDto"},allowGetters = true,allowSetters = true)
public class ProductInBranchDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Integer quantity;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate date;

    private LocalDate expDate;

    private Double priceIn;

    private ProductDto productDto;

    private BranchDto branchDto;
}
