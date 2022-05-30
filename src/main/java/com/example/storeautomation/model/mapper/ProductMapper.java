package com.example.storeautomation.model.mapper;

import com.example.storeautomation.model.dto.ProductDto;
import com.example.storeautomation.model.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductMapper implements BaseMapper<Product, ProductDto>{

    private final CategoryMapper categoryMapper;
    @Override
    public Product convertToEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setProductName(dto.getProductName());
        product.setPrice(dto.getPrice());
        product.setCategory(categoryMapper.convertToEntity(dto.getCategoryDto()));
        return product;
    }

    @Override
    public ProductDto convertToDto(Product entity) {
        return ProductDto.builder().
                id(entity.getId()).
                productName(entity.getProductName()).
                price(entity.getPrice()).
                categoryDto(categoryMapper.convertToDto(entity.getCategory())).
                build();
    }
}
