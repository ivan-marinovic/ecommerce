package com.example.ecommerce.controller;

import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.utils.Helper;
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

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    ResponseEntity<List<Category>> getCategories() {
        List<Category> body = categoryService.getAllCategories();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category) {
         if(Helper.notNull(categoryService.findCategoryByName(category.getCategoryName()))) {
             return new ResponseEntity<>(new ApiResponse(0, "category already exists"),HttpStatus.CONFLICT);
         }
         categoryService.createCategory(category);
         return new ResponseEntity<>(new ApiResponse(1, "category created"), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") Long categoryId, @Valid @RequestBody Category category) {
        if(Helper.notNull(categoryService.findCategoryById(categoryId))) {
            categoryService.updateCategory(categoryId, category);
            return new ResponseEntity<>(new ApiResponse(1, "category updated"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(0, "category does not exists"), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        if(!Helper.notNull(categoryService.findCategoryById(categoryId))) {
            return new ResponseEntity<>(new ApiResponse(0, "category does not exists"), HttpStatus.NOT_FOUND);
        }
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity<>(new ApiResponse(1, "category deleted"), HttpStatus.OK);
    }

}
