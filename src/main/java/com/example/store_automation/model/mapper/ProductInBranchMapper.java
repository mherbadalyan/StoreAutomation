package com.example.store_automation.model.mapper;

import com.example.store_automation.model.dto.ProductInBranchDto;
import com.example.store_automation.model.entity.ProductInBranch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductInBranchMapper implements BaseMapper<ProductInBranch, ProductInBranchDto>{
    private final BranchMapper branchMapper;
    private final ProductMapper productMapper;
    @Override
    public ProductInBranch convertToEntity(ProductInBranchDto dto) {
        ProductInBranch productInBranch = new ProductInBranch();
        productInBranch.setId(dto.getId());
        productInBranch.setQuantity(dto.getQuantity());
        productInBranch.setDate(dto.getDate());
        productInBranch.setExpDate(dto.getExpDate());
        productInBranch.setPriceIn(dto.getPriceIn());
        productInBranch.setBranch(branchMapper.convertToEntity(dto.getBranchDto()));
        productInBranch.setProduct(productMapper.convertToEntity(dto.getProductDto()));
        return productInBranch;
    }

    @Override
    public ProductInBranchDto convertToDto(ProductInBranch entity) {
        return ProductInBranchDto.builder().id(entity.getId()).
                quantity(entity.getQuantity()).
                date(entity.getDate()).
                expDate(entity.getExpDate()).
                priceIn(entity.getPriceIn()).
                branchDto(branchMapper.convertToDto(entity.getBranch())).
                productDto(productMapper.convertToDto(entity.getProduct())).
                build();
    }
}
