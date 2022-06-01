package com.example.store_automation.model.mapper;

import com.example.store_automation.model.dto.ProductDto;
import com.example.store_automation.model.entity.Product;
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
        product.setPercent(dto.getPercent());
        product.setPriceToSale(dto.getPriceToSale());
        product.setCategory(categoryMapper.convertToEntity(dto.getCategoryDto()));
        return product;
    }

    @Override
    public ProductDto convertToDto(Product entity) {
        return ProductDto.builder().
                id(entity.getId()).
                productName(entity.getProductName()).
                price(entity.getPrice()).
                percent(entity.getPercent()).
                priceToSale(entity.getPriceToSale()).
                categoryDto(categoryMapper.convertToDto(entity.getCategory())).
                build();
    }
}
