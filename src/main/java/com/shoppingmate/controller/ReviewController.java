package com.shoppingmate.controller;

import com.shoppingmate.exception.ProductException;
import com.shoppingmate.exception.UserException;
import com.shoppingmate.model.Review;
import com.shoppingmate.model.User;
import com.shoppingmate.request.ReviewRequest;
import com.shoppingmate.service.ReviewService;
import com.shoppingmate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Review> CreateReview(@RequestBody ReviewRequest req,
                                               @RequestHeader("Authorization") String jwt)
            throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(req, user);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<Review>> getProductsReview(@PathVariable Long id)
            throws UserException, ProductException {
        List<Review> reviews = reviewService.getAllReview(id);
        return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
    }
}
