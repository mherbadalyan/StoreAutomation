package com.example.store_automation.service;

import com.example.store_automation.model.dto.ProductDto;
import com.example.store_automation.model.entity.Category;
import com.example.store_automation.model.entity.Product;
import com.example.store_automation.model.mapper.CategoryMapper;
import com.example.store_automation.model.mapper.ProductMapper;
import com.example.store_automation.repository.CategoryRepository;
import com.example.store_automation.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public Optional<ProductDto> createProduct(ProductDto productDto) {
        if (productRepository.existsByProductNameAndPrice(
                productDto.getProductName(),productDto.getPrice())) {
            return Optional.empty();
        }

        Optional<Category> categoryFromData = categoryRepository.findByCategoryName(
                productDto.getCategoryDto().getCategoryName());

        Product productToSave = productMapper.convertToEntity(productDto);
        productToSave.setCategory(categoryFromData.get());

        Product savedProduct = productRepository.save(productToSave);
        return Optional.of(productMapper.convertToDto(savedProduct));
    }

    public Optional<ProductDto> getProductById(Long id) {
        Optional<Product> productFromData = productRepository.findById(id);
        if (productFromData.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(productMapper.convertToDto(productFromData.get()));
    }

    public Optional<List<ProductDto>> getProductsByCategoryId(Long id) {
        Optional<List<Product>> productsByCategoryId = productRepository.findProductsByCategoryId(id);

        if (productsByCategoryId.isEmpty()) {
            return Optional.empty();
        }

        List<ProductDto> productDtos =(List<ProductDto>) productMapper.convertToDtoColl(productsByCategoryId.get());
        return Optional.of(productDtos);
    }

    public boolean existById(Long id) {
        return productRepository.existsById(id);
    }

    public Optional<ProductDto> updateProduct(Long id, Double price, Double percent) {

        Optional<Product> opProductFromData = productRepository.findById(id);
        opProductFromData.get().setPrice(price);
        opProductFromData.get().setPercent(percent);

        Product savedProduct = productRepository.save(opProductFromData.get());
        return Optional.of(productMapper.convertToDto(savedProduct));
    }

    public Optional<List<ProductDto>> getAllProducts() {

        List<Product> allProducts = productRepository.findAll();

        if (allProducts.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of((List<ProductDto>) productMapper.convertToDtoColl(allProducts));
    }
}
