package com.example.store_automation.service;

import com.example.store_automation.model.dto.CategoryDto;
import com.example.store_automation.model.entity.Category;
import com.example.store_automation.model.mapper.CategoryMapper;
import com.example.store_automation.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public Optional<CategoryDto> createCategory(CategoryDto categoryDto){

        if (categoryRepository.existsCategoryByCategoryName(categoryDto.getCategoryName())) {
            return Optional.empty();
        }
        Category categoryToSave = this.categoryMapper.convertToEntity(categoryDto);

        Category savedCategory = this.categoryRepository.save(categoryToSave);

        return Optional.of(
                this.categoryMapper.convertToDto(savedCategory)
        );
    }

    public Optional<CategoryDto> updateCategory(CategoryDto categoryDto, String categoryName){

        if (!categoryRepository.existsCategoryByCategoryName(categoryName)) {
            return Optional.empty();
        }

        Optional<Category> categoryFromData = categoryRepository.findByCategoryName(categoryName);
        if (categoryFromData.isEmpty()) {
            return Optional.empty();
        }
        Category categoryToSave = categoryMapper.convertToEntity(categoryDto);

        categoryFromData.get().setCategoryName(categoryToSave.getCategoryName());

        Category savedCategory = categoryRepository.save(categoryFromData.get());
        return Optional.of(categoryMapper.convertToDto(savedCategory));
    }

    public void deleteCategory(String categoryName){
        categoryRepository.deleteByCategoryName(categoryName);
    }

    public Optional<CategoryDto> getCategory(String categoryName){
        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);

        if (category.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(categoryMapper.convertToDto(category.get()));    }
}
