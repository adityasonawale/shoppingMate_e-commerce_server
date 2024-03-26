package com.shoppingmate.controller;

import com.shoppingmate.exception.ProductException;
import com.shoppingmate.exception.UserException;
import com.shoppingmate.model.Cart;
import com.shoppingmate.model.User;
import com.shoppingmate.request.AddItemRequest;
import com.shoppingmate.response.ApiResponse;
import com.shoppingmate.service.CartService;
import com.shoppingmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> AddItemToCart(@RequestBody AddItemRequest req,
                                                     @RequestHeader("Authorization") String jwt)
            throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(), req);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Item Added To Cart Successfully !");
        apiResponse.setSuccess(true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
