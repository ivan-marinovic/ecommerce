package com.example.ecommerce.controller;

import com.example.ecommerce.common.ApiResponse;
import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.service.AuthenticationService;
import com.example.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final AuthenticationService authenticationService;
    public ProductController(ProductService productService, CategoryRepository categoryRepository,AuthenticationService authenticationService) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.authenticationService = authenticationService;
    }

    @GetMapping(path = "all")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@RequestParam("token") String token, @RequestBody ProductDto productDto) {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        if(!user.getRole().equals(Role.admin)){
            return new ResponseEntity<>(new ApiResponse(false, "access denied"), HttpStatus.FORBIDDEN);
        }
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "category does not exists"), HttpStatus.BAD_REQUEST);
        }
        productService.createProduct(productDto, optionalCategory.get());
        return new ResponseEntity<>(new ApiResponse(true, "product has been created"),HttpStatus.CREATED);
    }


    //ne radi update
    @PutMapping(path = "{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Long productId,@RequestBody ProductDto productDto) throws Exception {
        Optional<Category>optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category does not exists"), HttpStatus.BAD_REQUEST);
        }
        productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(new ApiResponse(true, "product has been updated"), HttpStatus.OK);
    }
}
