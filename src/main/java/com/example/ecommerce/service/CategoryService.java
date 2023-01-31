package com.example.ecommerce.service;

import com.example.ecommerce.exception.CategoryNameExists;
import com.example.ecommerce.exception.CategoryNotFoundException;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(Category category) {
        if(isCategoryNameExists(category)) {
            throw new CategoryNameExists("category with name " + category.getCategoryName() + " already exists");
        }
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public boolean isCategoryNameExists(Category category) {
        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (optionalCategory.isPresent()) {
            return true;
        }
        return false;
    }

    public void updateCategory(Long categoryId, Category category){
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException("category with id " + categoryId  + " does not exists");
        }
        Category updatedCategory = optionalCategory.get();
        updatedCategory.setCategoryName(category.getCategoryName());
        updatedCategory.setDescription(category.getDescription());
        categoryRepository.save(updatedCategory);
    }

    public void deleteCategoryById(Long categoryId){
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException("category with id " + categoryId  + " does not exists");
        }
        categoryRepository.deleteById(categoryId);

    }
}
