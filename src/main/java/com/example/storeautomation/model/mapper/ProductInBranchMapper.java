package com.example.storeautomation.model.mapper;

import com.example.storeautomation.model.dto.ProductInBranchDto;
import com.example.storeautomation.model.entity.ProductInBranch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class ProductInBranchMapper implements BaseMapper<ProductInBranch, ProductInBranchDto>{
    private BranchMapper branchMapper;
    private ProductMapper productMapper;
    @Override
    public ProductInBranch convertToEntity(ProductInBranchDto dto) {
        ProductInBranch productInBranch =new ProductInBranch();
        productInBranch.setId(dto.getId());
        productInBranch.setQuantity(dto.getQuantity());
        productInBranch.setDate(dto.getDate());
        productInBranch.setBranch(branchMapper.convertToEntity(dto.getBranchDto()));
        productInBranch.setProduct(productMapper.convertToEntity(dto.getProductDto()));
        return productInBranch;
    }

    @Override
    public ProductInBranchDto convertToDto(ProductInBranch entity) {
        return ProductInBranchDto.builder().id(entity.getId()).
                quantity(entity.getQuantity()).
                date(entity.getDate()).
                branchDto(branchMapper.convertToDto(entity.getBranch())).
                productDto(productMapper.convertToDto(entity.getProduct())).
                build();
    }
}
