package com.example.ecommerce.controller;

import com.example.ecommerce.common.ApiResponse;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.service.AuthenticationService;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.utils.Helper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/category/")
public class CategoryController {

    private final CategoryService categoryService;
    private final AuthenticationService authenticationService;
    public CategoryController(CategoryService categoryService, AuthenticationService authenticationService) {
        this.categoryService = categoryService;
        this.authenticationService = authenticationService;
    }

    @GetMapping(path = "all")
    ResponseEntity<List<Category>> getCategories() {
        List<Category> body = categoryService.getAllCategories();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping(path = "create")
    public ResponseEntity<ApiResponse> createCategory(@RequestParam("token") String token,@Valid @RequestBody Category category) {
         authenticationService.authenticate(token);
         if(!authenticationService.isAuthorized(token)) {
             return new ResponseEntity<>(new ApiResponse(false, "access denied"),HttpStatus.FORBIDDEN);
         }
         if(Helper.notNull(categoryService.findCategoryByName(category.getCategoryName()))) {
             return new ResponseEntity<>(new ApiResponse(false, "category already exists"),HttpStatus.CONFLICT);
         }
         categoryService.createCategory(category);
         return new ResponseEntity<>(new ApiResponse(true, "category created"), HttpStatus.CREATED);
    }

    @PutMapping(path = "update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestParam("token") String token, @PathVariable("categoryId") Long categoryId, @Valid @RequestBody Category category) {
        authenticationService.authenticate(token);
        if(!authenticationService.isAuthorized(token)) {
            return new ResponseEntity<>(new ApiResponse(false, "access denied"),HttpStatus.FORBIDDEN);
        }
        if(Helper.notNull(categoryService.findCategoryById(categoryId))) {
            categoryService.updateCategory(categoryId, category);
            return new ResponseEntity<>(new ApiResponse(true, "category updated"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false, "category does not exists"), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@RequestParam("token") String token, @PathVariable("categoryId") Long categoryId) {
        authenticationService.authenticate(token);
        if(!authenticationService.isAuthorized(token)) {
            return new ResponseEntity<>(new ApiResponse(false, "access denied"),HttpStatus.FORBIDDEN);
        }
        if(!Helper.notNull(categoryService.findCategoryById(categoryId))) {
            return new ResponseEntity<>(new ApiResponse(false, "category does not exists"), HttpStatus.NOT_FOUND);
        }
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity<>(new ApiResponse(true, "category deleted"), HttpStatus.OK);
    }

}
