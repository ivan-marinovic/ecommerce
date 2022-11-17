package com.example.ecommerce.controller;

import com.example.ecommerce.common.ApiResponse;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "all")
    public List<Category> getCategories(Category category) {
        return categoryService.getCategories(category);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
         categoryService.createCategory(category);
         return new ResponseEntity<>(new ApiResponse(true, "new category created"),HttpStatus.CREATED);
    }

    @PutMapping(path = "{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") Long categoryId,@RequestBody Category category) {
        if(!categoryService.isExists(categoryId)) {
            return new ResponseEntity<>(new ApiResponse(false, "category does not exists"), HttpStatus.NOT_FOUND);
        }
        categoryService.updateCategory(categoryId, category);
        return new ResponseEntity<>(new ApiResponse(true, "category has been updated"), HttpStatus.OK);
    }
}
