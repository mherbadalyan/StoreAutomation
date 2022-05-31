package com.example.store_automation.controller;

import com.example.store_automation.model.dto.*;
import com.example.store_automation.model.entity.*;
import com.example.store_automation.response.*;
import com.example.store_automation.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("store-automation/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    private ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) {
        logger.info("Received request to create product");
        Optional<CategoryDto> optionalCategoryDto = categoryService.getCategory(
                productDto.getCategoryDto().getCategoryName());

        if (optionalCategoryDto.isEmpty()) {
            logger.error("There is not category with given parameter");
            return new EntityLookupResponse<Category>().onFailure("Category");
        }

        Optional<ProductDto> optionalProductDto = productService.createProduct(productDto);
        if (optionalProductDto.isEmpty()) {
            logger.error("There is a product with given parameter");
            return new EntityCreatingResponse<Product>().onFailure("Product");
        }
        logger.info("Product successfully created");
        return new EntityCreatingResponse<ProductDto>().onSuccess(optionalProductDto.get());
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getProduct(@PathVariable("id") Long id) {
        logger.info("Received request to get product by id");
        Optional<ProductDto> productDto = productService.getProductById(id);

        if (productDto.isEmpty()) {
            logger.error("There is not product with given parameter");
            return new EntityLookupResponse<ProductDto>().onFailure("Product");
        }
        logger.info("Product with given id found");
        return new EntityLookupResponse<ProductDto>().onSuccess(productDto.get());
    }

    @GetMapping("/byCategoryId/{id}")
    private ResponseEntity<?> getProductsByCategoryId(@PathVariable("id") Long id) {
        logger.info("Received request to get products by category id");
        Optional<List<ProductDto>> productsByCategoryId = productService.getProductsByCategoryId(id);

        if (productsByCategoryId.isEmpty()) {
            logger.error("There is not products with given parameter");
            return new EntityLookupResponse<ProductDto>().onFailure("Products");
        }
        logger.info("Products with given category id found");
        return new EntityLookupResponse<List<ProductDto>>().onSuccess(productsByCategoryId.get());
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteProductByID(@PathVariable("id") Long id ) {
        logger.info("Received a request to delete an product.");
        Optional<ProductDto> productById = productService.getProductById(id);

        if (productById.isPresent()) {
            productService.deleteProductById(id);
            return new EntityDeletingResponse<ProductDto>().onSuccess(productById.get(),"Product");
        }
        logger.warn("There is not product with given id");
        return new EntityLookupResponse<ProductDto>().onFailure("Product");
    }

}
