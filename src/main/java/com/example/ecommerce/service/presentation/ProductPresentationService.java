package com.example.ecommerce.service.presentation;

import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.empty;

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
            List<ProductDto> productsDto = new ArrayList<>();
            for(Product product : products) {
                productsDto.add(convertModelToDto(product));
            }
            return productsDto;
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

    public List<ProductDto> convertModelToDtoPage(Page<Product> products) {
        List<ProductDto> productsDto = new ArrayList<>();
        for(Product product : products) {
            productsDto.add(convertModelToDto(product));
        }
        return productsDto;
    }
}
