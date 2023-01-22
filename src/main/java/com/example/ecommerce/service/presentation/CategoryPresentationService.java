package com.example.ecommerce.service.presentation;

import com.example.ecommerce.dto.category.CategoryDto;
import com.example.ecommerce.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryPresentationService {

    public CategoryDto convertModelToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setDescription(categoryDto.getDescription());
        return categoryDto;
    }

    public List<CategoryDto> convertModelToDtoList(List<Category> categories) {
        List<CategoryDto> categoriesDto = new ArrayList<>();
        for(Category category : categories) {
            categoriesDto.add(convertModelToDto(category));
        }
        return categoriesDto;
    }

    public Category convertDtoToModel(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryName(categoryDto.getCategoryName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }

}
