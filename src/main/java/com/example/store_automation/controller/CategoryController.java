package com.example.store_automation.controller;

import com.example.store_automation.internationalization.services.LocaleService;
import com.example.store_automation.model.dto.CategoryDto;
import com.example.store_automation.model.entity.Category;
import com.example.store_automation.response.EntityCreatingResponse;
import com.example.store_automation.response.EntityDeletingResponse;
import com.example.store_automation.response.EntityLookupResponse;
import com.example.store_automation.response.EntityUpdatingResponse;
import com.example.store_automation.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("store-automation/category")
@SecurityRequirement(name = "store_automation")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final LocaleService localeService;

    @Operation(summary = "Create category",
            description = "Creating category"
    )
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto, HttpServletRequest request) {
        logger.info("Received a request to create a Category.");
        Optional<CategoryDto> optionalCategoryDto = categoryService.createCategory(categoryDto);

        if (optionalCategoryDto.isEmpty()) {
            logger.warn("There is a Category with name " + categoryDto.getCategoryName());
            String message = localeService.getMessage("Can_not_create_category", request);
            return new EntityCreatingResponse<Category>().onFailure(message);
        }
        logger.info("Category is created.");
        return new EntityCreatingResponse<CategoryDto>().onSuccess(optionalCategoryDto.get());
    }

    @Operation(summary = "Get category",
            description = "Getting category by name"
    )
    @GetMapping("/{name}")
    public ResponseEntity<?> getCategory(@PathVariable("name") String name, HttpServletRequest request) {
        logger.info("Received a request to get a Category with name " + name);
        Optional<CategoryDto> categoryDto = categoryService.getCategory(name);

        if (categoryDto.isPresent()) {
            logger.info("Category with given name found");
            return new EntityLookupResponse<CategoryDto>().onSuccess(categoryDto.get());
        }
        logger.warn("There is not category with given name");
        String message = localeService.getMessage("Category_not_found", request);
        return new EntityLookupResponse<CategoryDto>().onFailure(message);
    }

    @Operation(summary = "Delete category",
            description = "Deleting category by name"
    )
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteCategory(@PathVariable("name") String name, HttpServletRequest request) {
        logger.info("Received a request to delete a Category with name " + name);
        Optional<CategoryDto> optionalCategoryDto = categoryService.getCategory(name);

        if (optionalCategoryDto.isPresent()) {
            categoryService.deleteCategory(name);
            return new EntityDeletingResponse<CategoryDto>()
                    .onSuccess(optionalCategoryDto.get(), "Category");
        }
        logger.warn("There is not category with given name");
        String message = localeService.getMessage("Category_not_found", request);
        return new EntityLookupResponse<CategoryDto>().onFailure(message);
    }

    @Operation(summary = "Update category",
            description = "Updating category by name"
    )
    @PutMapping("/{name}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDto categoryDto,
                                            @PathVariable("name") String name,
                                            HttpServletRequest request) {
        logger.info("Received a request to update a category with name " + name);
        Optional<CategoryDto> optionalCategoryDto = categoryService.updateCategory(categoryDto, name);

        if (optionalCategoryDto.isEmpty()) {
            logger.warn("There is not a category with this name.");
            String message = localeService.getMessage("Category_not_found", request);
            return new EntityLookupResponse<Category>().onFailure(message);
        }
        logger.info("Category successfully updated.");
        return new EntityUpdatingResponse<CategoryDto>().onSuccess(optionalCategoryDto.get());
    }
}
