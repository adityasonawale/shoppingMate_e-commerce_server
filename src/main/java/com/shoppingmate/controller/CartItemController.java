package com.shoppingmate.controller;

import com.shoppingmate.exception.CartItemException;
import com.shoppingmate.exception.UserException;
import com.shoppingmate.model.CartItem;
import com.shoppingmate.model.User;
import com.shoppingmate.response.ApiResponse;
import com.shoppingmate.service.CartItemService;
import com.shoppingmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemService cartItemService;

    public CartItemController(UserService userService, CartItemService cartItemService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem,
                                                   @PathVariable Long id,
                                                   @RequestHeader("Authorization") String jwt)
            throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), id, cartItem);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt)
            throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Item deleted Successfully !");
        apiResponse.setSuccess(true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
