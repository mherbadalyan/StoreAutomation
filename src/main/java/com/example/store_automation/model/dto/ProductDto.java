package com.example.store_automation.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(value = "product name")
    private String productName;

    private Double price;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Double percent;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double priceToSale;

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
