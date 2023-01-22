package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/wishlist")
public class WishListController {

   /* private final WishListService wishListService;
    public WishListController(WishListService wishListService, AuthenticationService authenticationService) {
        this.wishListService = wishListService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishlist(@RequestBody Product product,@RequestParam("token") String token) {
        WishList wishList = new WishList(user, product);
        wishListService.createWishList(wishList);

        return new ResponseEntity<>(new ApiResponse(true, "added to wishlist"), HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token ) {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        List<ProductDto> productDtos = wishListService.getWishListForUser(user);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
*/

}
