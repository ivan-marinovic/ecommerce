package com.example.ecommerce.service;

import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.WishList;
import com.example.ecommerce.repository.WishListRepository;
import com.example.ecommerce.service.presentation.ProductPresentationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductService productService;
    private ProductPresentationService productPresentationService;
    public WishListService(WishListRepository wishListRepository, ProductService productService, ProductPresentationService productPresentationService) {
        this.wishListRepository = wishListRepository;
        this.productService = productService;
        this.productPresentationService = productPresentationService;
    }

    public void createWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<ProductDto> getWishListForUser(User user) {
        final List<WishList> wishLists = wishListRepository.findByUser(user);
        List<ProductDto> productsDto = new ArrayList<>();
        for(WishList wishList : wishLists) {
            productsDto.add(productPresentationService.convertModelToDto(wishList.getProduct()));
        }
        return productsDto;
    }
}
