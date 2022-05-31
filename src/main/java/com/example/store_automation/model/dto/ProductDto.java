package com.example.store_automation.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(value = "category")
    private CategoryDto categoryDto;

    @Override
    public String toString() {
        return "Product  \n" +
                "id = " + id +
                ", \nproductName = " + productName +
                ", \nprice = " + price +
                ", \ncategory = " + categoryDto.getCategoryName();
    }
}
