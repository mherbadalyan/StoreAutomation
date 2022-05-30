package com.example.storeautomation.model.mapper;

import com.example.storeautomation.model.dto.ProductDto;
import com.example.storeautomation.model.entity.Product;

public class ProductMapper implements BaseMapper<Product, ProductDto>{
    @Override
    public Product convertToEntity(ProductDto dto) {
        return null;
    }

    @Override
    public ProductDto convertToDto(Product entity) {
        return null;
    }
}
