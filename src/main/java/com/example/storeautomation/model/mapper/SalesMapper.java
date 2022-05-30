package com.example.storeautomation.model.mapper;

import com.example.storeautomation.model.dto.SalesDto;
import com.example.storeautomation.model.entity.Sales;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SalesMapper implements BaseMapper<Sales, SalesDto>{

    private final BranchMapper branchMapper;

    private final ProductMapper productMapper;
    @Override
    public Sales convertToEntity(SalesDto dto) {
        Sales sales = new Sales();
        sales.setId(dto.getId());
        sales.setPrice(dto.getPrice());
        sales.setSalesDate(dto.getSalesDate());
        sales.setQuantity(dto.getQuantity());
        sales.setBranch(branchMapper.convertToEntity(dto.getBranchDto()));
        sales.setProduct(productMapper.convertToEntity(dto.getProductDto()));
        return sales;
    }

    @Override
    public SalesDto convertToDto(Sales entity) {
        return SalesDto.builder().
                id(entity.getId()).
                price(entity.getPrice()).
                salesDate(entity.getSalesDate()).
                quantity(entity.getQuantity()).
                branchDto(branchMapper.convertToDto(entity.getBranch())).
                productDto(productMapper.convertToDto(entity.getProduct())).build();
    }
}
