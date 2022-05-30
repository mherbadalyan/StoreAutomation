package com.example.storeautomation.model.mapper;

import com.example.storeautomation.model.dto.SalesDto;
import com.example.storeautomation.model.entity.Sales;

public class SalesMapper implements BaseMapper<Sales, SalesDto>{
    @Override
    public Sales convertToEntity(SalesDto dto) {
        Sales sales = new Sales();
        return null;
    }

    @Override
    public SalesDto convertToDto(Sales entity) {
        return null;
    }
}
