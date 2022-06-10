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
public class CategoryDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(value = "category name")
    private String categoryName;

    @Override
    public String toString() {
        return "Category " +
                "id = " + id +
                ", category name = " + categoryName ;
    }
}
