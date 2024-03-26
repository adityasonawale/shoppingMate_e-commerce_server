package com.shoppingmate.service;

import com.shoppingmate.exception.ProductException;
import com.shoppingmate.model.Rating;
import com.shoppingmate.model.User;
import com.shoppingmate.request.RatingRequest;

import java.util.List;


public interface RatingService {
    public Rating createRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductsRating(Long productId);
}
