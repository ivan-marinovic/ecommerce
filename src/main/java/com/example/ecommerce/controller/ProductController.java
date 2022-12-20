package com.example.ecommerce.controller;

import com.example.ecommerce.common.ApiResponse;
import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.AuthenticationService;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final AuthenticationService authenticationService;
    private final ProductRepository productRepository;
    public ProductController(ProductService productService, CategoryService categoryService,AuthenticationService authenticationService,ProductRepository productRepository) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.authenticationService = authenticationService;
        this.productRepository = productRepository;
    }

    @GetMapping(path = "all")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping
    Page<Product> getProduct(@RequestParam Optional<String> sortBy, @RequestParam Optional<Integer> page) {
        return productRepository.findAll(
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id"))
                );
    }

    @PostMapping(path = "create")
    public ResponseEntity<ApiResponse> createProduct(@RequestParam("token") String token, @Valid @RequestBody ProductDto productDto) {
        authenticationService.authenticate(token);
        if(!authenticationService.isAuthorized(token)){
            return new ResponseEntity<>(new ApiResponse(false, "access denied"), HttpStatus.FORBIDDEN);
        }
        Optional<Category> optionalCategory = categoryService.findCategoryById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "category does not exists"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.createProduct(productDto, category);
        return new ResponseEntity<>(new ApiResponse(true, "product has been created"),HttpStatus.CREATED);
    }

    @PutMapping(path = "update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestParam("token") String token, @PathVariable("productId") Long productId, @Valid @RequestBody ProductDto productDto) throws Exception {
        authenticationService.authenticate(token);
        if(!authenticationService.isAuthorized(token)){
            return new ResponseEntity<>(new ApiResponse(false, "access denied"), HttpStatus.FORBIDDEN);
        }
        Optional<Category>optionalCategory = categoryService.findCategoryById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category does not exists"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.updateProduct(productId, productDto, category);
        return new ResponseEntity<>(new ApiResponse(true, "product has been updated"), HttpStatus.OK);
    }

    @DeleteMapping(path = "{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestParam("token") String token, @PathVariable("productId") Long productId) throws Exception {
        authenticationService.authenticate(token);
        if(!authenticationService.isAuthorized(token)){
            return new ResponseEntity<>(new ApiResponse(false, "access denied"), HttpStatus.FORBIDDEN);
        }
        productService.deleteProduct(productId);
        return new ResponseEntity<>(new ApiResponse(true, "product has been deleted"), HttpStatus.OK);
    }
}
