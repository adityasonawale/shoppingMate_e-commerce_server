package com.shoppingmate.service;

import com.shoppingmate.exception.CartItemException;
import com.shoppingmate.exception.UserException;
import com.shoppingmate.model.Cart;
import com.shoppingmate.model.CartItem;
import com.shoppingmate.model.Product;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
