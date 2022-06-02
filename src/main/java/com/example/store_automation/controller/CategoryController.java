package com.example.store_automation.controller;

import com.example.store_automation.model.dto.CategoryDto;
import com.example.store_automation.model.entity.Category;
import com.example.store_automation.response.EntityCreatingResponse;
import com.example.store_automation.response.EntityDeletingResponse;
import com.example.store_automation.response.EntityLookupResponse;
import com.example.store_automation.response.EntityUpdatingResponse;
import com.example.store_automation.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("store-automation/category")
@SecurityRequirement(name = "store_automation")
public class CategoryController {
    private final CategoryService categoryService;

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto) {
        logger.info("Received a request to create an Category.");
        Optional<CategoryDto> optionalCategoryDto = categoryService.createCategory(categoryDto);

        if (optionalCategoryDto.isEmpty()) {
            logger.warn("There is an Category.");
            return new EntityCreatingResponse<Category>().onFailure("Category");
        }
        logger.info("Category is created.");
        return new EntityCreatingResponse<CategoryDto>().onSuccess(optionalCategoryDto.get());
    }


    @GetMapping("/{name}")
    public ResponseEntity<?> getCategory(@PathVariable("name") String name) {
        logger.info("Received a request to get a Category.");
        Optional<CategoryDto> categoryDto = categoryService.getCategory(name);

        if (categoryDto.isPresent()) {
            logger.info("Category  with given name found");
            return new EntityLookupResponse<CategoryDto>().onSuccess(categoryDto.get());
        }
        logger.warn("There is not Category with given name");
        return new EntityLookupResponse<CategoryDto>().onFailure("category");
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteCategory(@PathVariable("name") String name) {
        logger.info("Received a request to delete a Category.");
        Optional<CategoryDto> optionalCategoryDto = categoryService.getCategory(name);

        if (optionalCategoryDto.isPresent()) {
            categoryService.deleteCategory(name);
            return new EntityDeletingResponse<CategoryDto>().onSuccess(optionalCategoryDto.get(),"Category");
        }
        logger.warn("There is not category with given name");
        return new EntityLookupResponse<CategoryDto>().onFailure("Category");
    }

    @PutMapping("/{name}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDto categoryDto ,
                                        @PathVariable("name") String name) {
        logger.info("Received a request to update an Category.");
        Optional<CategoryDto> optionalCategoryDto = categoryService.updateCategory(categoryDto,name);

        if (optionalCategoryDto.isEmpty()) {
            logger.warn("There is not a Category with this name.");
            return new EntityLookupResponse<Category>().onFailure("Category");
        }
        logger.info("Category successfully updated.");
        return new EntityUpdatingResponse<CategoryDto>().onSuccess(optionalCategoryDto.get());
    }

}
