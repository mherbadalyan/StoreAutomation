package com.example.store_automation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class BranchDto {

    private Long id;


    private String name;


    private String address;

}
