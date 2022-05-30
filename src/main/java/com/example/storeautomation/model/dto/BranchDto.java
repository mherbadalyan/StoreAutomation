package com.example.storeautomation.model.dto;

import com.example.storeautomation.model.enums.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@Builder
public class BranchDto {

    private Long id;


    private String name;


    private String address;


    private StoreStatus status;
}
