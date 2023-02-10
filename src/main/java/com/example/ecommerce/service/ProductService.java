package com.example.ecommerce.service;

import com.example.ecommerce.exception.CategoryNotFoundException;
import com.example.ecommerce.exception.ProductNotFoundException;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public void createProduct(Product product, Long categoryId) {
        Optional<Category> optionalCategory = categoryService.findCategoryById(categoryId);
        if(optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException("category does not exists");
        }
        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setQuantity(product.getQuantity());
        newProduct.setImageUrl(product.getImageUrl());
        newProduct.setCategory(optionalCategory.get());
        productRepository.save(newProduct);
    }

    public List<Product> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts;
    }

    public Page<Product> findProductWithPaginationAndSorting(int offset, int pageSize, String field) {
        Page<Product> products = productRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return products;
    }

    public void updateProduct(Product product, Long productId) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isEmpty()) {
            throw new ProductNotFoundException("product does not exists");
        }
        Product updatedProduct = productOptional.get();
        updatedProduct.setName(product.getName());
        updatedProduct.setImageUrl(product.getImageUrl());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setQuantity(product.getQuantity());
        productRepository.save(updatedProduct);
    }

    public Product findById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("product does not exist");
        }
        return optionalProduct.get();
    }

    public void setProductQuantity(Long itemId, Integer quantity) {
        Product product = findById(itemId);
        Integer productQuantity = product.getQuantity();
        Integer newQuantity = productQuantity - quantity;
        product.setQuantity(newQuantity);
        productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("product does not exist");
        }
        Product product = optionalProduct.get();
        productRepository.deleteById(product.getProductId());
    }
}
