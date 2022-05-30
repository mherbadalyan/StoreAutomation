package com.example.storeautomation.model.mapper;

import com.example.storeautomation.model.dto.CategoryDto;
import com.example.storeautomation.model.entity.Category;

public class CategoryMapper implements BaseMapper<Category, CategoryDto>{
    @Override
    public Category convertToEntity(CategoryDto dto) {
        return null;
    }

    @Override
    public CategoryDto convertToDto(Category entity) {
        return null;
    }
}
