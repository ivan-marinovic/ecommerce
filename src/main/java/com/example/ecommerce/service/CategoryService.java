package com.example.ecommerce.service;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getCategories(Category category) {
        return categoryRepository.findAll();
    }


    public void updateCategory(Long categoryId, Category updatedCategory) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new IllegalStateException("category with id " + categoryId + "does not exist"));
        category.setCategoryName(updatedCategory.getCategoryName());
        category.setDescription(updatedCategory.getDescription());
        category.setImageUrl(updatedCategory.getImageUrl());
        categoryRepository.save(category);
    }

    public boolean isExists(Long categoryId) {
        return categoryRepository.findById(categoryId).isPresent();
    }
}
