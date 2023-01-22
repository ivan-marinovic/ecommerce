package com.example.ecommerce.service.presentation;

import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductPresentationService {

        public ProductDto convertModelToDto(Product product) {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setQuantity(product.getQuantity());
            productDto.setImageUrl(product.getImageUrl());
            productDto.setCategoryId(product.getCategory().getCategoryId());
            return productDto;
        }

        public List<ProductDto> convertModelToDtoList(List<Product> products) {
            List<ProductDto> categoriesDto = new ArrayList<>();
            for(Product book : products) {
                categoriesDto.add(convertModelToDto(book));
            }
            return categoriesDto;
        }


        public Product convertDtoToModel(ProductDto productDto) {
            Product product = new Product();
            product.setProductId(productDto.getCategoryId());
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setQuantity(productDto.getQuantity());
            product.setImageUrl(productDto.getImageUrl());
            product.setCategory(product.getCategory());
            return product;
        }

}
