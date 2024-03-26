package com.shoppingmate.service;

import com.shoppingmate.exception.ProductException;
import com.shoppingmate.model.Cart;
import com.shoppingmate.model.User;
import com.shoppingmate.request.AddItemRequest;

public interface CartService {
    public Cart createCart(User user);
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
    public Cart findUserCart(Long userId);
}
