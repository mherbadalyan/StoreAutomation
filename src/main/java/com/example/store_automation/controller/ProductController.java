package com.example.store_automation.controller;

import com.example.store_automation.internationalization.services.LocaleService;
import com.example.store_automation.model.dto.*;
import com.example.store_automation.model.entity.*;
import com.example.store_automation.response.*;
import com.example.store_automation.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("store-automation/product")
@RequiredArgsConstructor
@SecurityRequirement(name = "store_automation")
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final LocaleService localeService;

    @Operation(summary = "Create product",
            description = "Creating product"
    )
    @PostMapping
    private ResponseEntity<?> createProduct(@RequestBody ProductDto productDto, HttpServletRequest request) {
        logger.info("Received request to create product");
        Optional<CategoryDto> optionalCategoryDto = categoryService.getCategory(
                productDto.getCategoryDto().getCategoryName());
        String message;
        if (optionalCategoryDto.isEmpty()) {
            logger.error("There is not category with given parameter");
            message = localeService.getMessage("Category_not_found", request);
            return new EntityLookupResponse<Category>().onFailure(message);
        }

        Optional<ProductDto> optionalProductDto = productService.createProduct(productDto);
        if (optionalProductDto.isEmpty()) {
            logger.error("There is a product with given parameter");
            message = localeService.getMessage("Can_not_create_product", request);
            return new EntityCreatingResponse<Product>().onFailure(message);
        }
        logger.info("Product successfully created");
        return new EntityCreatingResponse<ProductDto>().onSuccess(optionalProductDto.get());
    }

    @Operation(summary = "Get product",
            description = "Getting product by id"
    )
    @GetMapping("/{id}")
    private ResponseEntity<?> getProduct(@PathVariable("id") Long id, HttpServletRequest request) {
        logger.info("Received request to get product by id "+id);
        Optional<ProductDto> productDto = productService.getProductById(id);
        String message;
        if (productDto.isEmpty()) {
            logger.error("There is not product with given id "+id);
            message = localeService.getMessage("Product_not_found", request);
            return new EntityLookupResponse<ProductDto>().onFailure(message);
        }
        logger.info("Product with given id found");
        return new EntityLookupResponse<ProductDto>().onSuccess(productDto.get());
    }

    @Operation(summary = "Get product by category id",
            description = "Getting product by category id"
    )
    @GetMapping("/byCategoryId/{id}")
    private ResponseEntity<?> getProductsByCategoryId(@PathVariable("id") Long id, HttpServletRequest request) {
        logger.info("Received request to get products by category id "+id);
        Optional<List<ProductDto>> productsByCategoryId = productService.getProductsByCategoryId(id);

        if (productsByCategoryId.isEmpty()) {
            logger.error("There is not products with given category id "+id);
            String message = localeService.getMessage("Products_not_found_by_category_id", request);
            return new EntityLookupResponse<ProductDto>().onFailure(message);
        }
        logger.info("Products with given category id found");
        return new EntityLookupResponse<List<ProductDto>>().onSuccess(productsByCategoryId.get());
    }

    @Operation(summary = "Update product",
            description = "Update product"
    )
    @PutMapping("/update/{id}/{price}/{percent}")
    private ResponseEntity<?> updateProduct(@PathVariable("id") Long id,
                                            @PathVariable("price") Double price,
                                            @PathVariable("percent") Double percent,
                                            HttpServletRequest request){
        logger.info("Received request to update product by id = " + id);

        if (!productService.existById(id)) {
            logger.error("There is not product with given id "+id);
            String message = localeService.getMessage("Product_not_found", request);
            return new EntityLookupResponse<ProductDto>().onFailure(message);
        }
        Optional<ProductDto> productDto = productService.updateProduct(id,price,percent);

        if (productDto.isEmpty()) {
            logger.error("There is problem with updating product with given id "+ id);
            return new EntityUpdatingResponse<ProductDto>().onFailure("Product");
        }
        logger.info("Updated product with id = " + id);
        return new EntityUpdatingResponse<ProductDto>().onSuccess(productDto.get());
    }

    @Operation(summary = "Get all product",
            description = "Getting all product"
    )
    @GetMapping
    private ResponseEntity<?> getAllProducts (HttpServletRequest request){
        logger.info("Received request to get all products.");
        Optional<List<ProductDto>> allProducts = productService.getAllProducts();

        if (allProducts.isEmpty()) {
            logger.info("There is not products.");
            String message = localeService.getMessage("Products_not_found", request);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        logger.info("Products found");
        return new EntityLookupResponse<List<ProductDto>>().onSuccess(allProducts.get());
    }
}
