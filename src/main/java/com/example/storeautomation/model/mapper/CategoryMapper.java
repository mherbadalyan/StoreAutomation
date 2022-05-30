package com.example.storeautomation.model.mapper;

import com.example.storeautomation.model.dto.CategoryDto;
import com.example.storeautomation.model.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper implements BaseMapper<Category, CategoryDto>{
    @Override
    public Category convertToEntity(CategoryDto dto) {
        Category category=new Category();
        category.setId(dto.getId());
        category.setCategoryName(dto.getCategoryName());
        return category;
    }

    @Override
    public CategoryDto convertToDto(Category entity) {
        return CategoryDto.builder().id(entity.getId()).
                categoryName(entity.getCategoryName()).build();
    }
}
