package com.example.ecommerce.controller;

import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.presentation.ProductPresentationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductPresentationService productPresentationService;
    public ProductController(ProductService productService, CategoryService categoryService, ProductPresentationService productPresentationService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productPresentationService = productPresentationService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productsDto = productPresentationService.convertModelToDtoList(products);
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

    @GetMapping("/{offset}/{pageSize}/{field}")
    public ResponseEntity<List<ProductDto>> getProductsWithPaginationAndSorting(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String field) {
        Page<Product> products = productService.findProductWithPaginationAndSorting(offset, pageSize, field);
        List<ProductDto> productsDto = productPresentationService.convertModelToDtoPage(products);
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = productPresentationService.convertDtoToModel(productDto);
        Long categoryId = productDto.getCategoryId();
        productService.createProduct(product, categoryId);
        return new ResponseEntity<>(new ApiResponse(1, "product has been created"),HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Long productId, @Valid @RequestBody ProductDto productDto) throws Exception {
        Product product = productPresentationService.convertDtoToModel(productDto);
        productService.updateProduct(product, productId);
        return new ResponseEntity<>(new ApiResponse(1, "product has been updated"), HttpStatus.OK);
    }

    @DeleteMapping("{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Long productId) throws Exception {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(new ApiResponse(1, "product has been deleted"), HttpStatus.OK);
    }
}
