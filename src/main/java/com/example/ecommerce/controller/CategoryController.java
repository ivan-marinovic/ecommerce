package com.example.ecommerce.controller;

import com.example.ecommerce.dto.category.CategoryDto;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.presentation.CategoryPresentationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryPresentationService categoryPresentationService;

    public CategoryController(CategoryService categoryService, CategoryPresentationService categoryPresentationService) {
        this.categoryService = categoryService;
        this.categoryPresentationService = categoryPresentationService;
    }

    @GetMapping
    ResponseEntity<List<CategoryDto>> getCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDto> categoriesDto = categoryPresentationService.convertModelToDtoList(categories);
        return new ResponseEntity<>(categoriesDto, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
         Category newCategory = categoryPresentationService.convertDtoToModel(categoryDto);
         categoryService.createCategory(newCategory);
         return new ResponseEntity<>(new ApiResponse(1, "category created"), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") Long categoryId, @Valid @RequestBody CategoryDto categoryDto) throws Exception {
        Category updatedCategory = categoryPresentationService.convertDtoToModel(categoryDto);
        categoryService.updateCategory(categoryId, updatedCategory);
        return new ResponseEntity<>(new ApiResponse(1, "category has been updated"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Long categoryId) throws Exception {
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity<>(new ApiResponse(1, "category has been deleted"), HttpStatus.OK);
    }

}
