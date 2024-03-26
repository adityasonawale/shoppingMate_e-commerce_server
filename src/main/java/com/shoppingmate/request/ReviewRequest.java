package com.shoppingmate.request;

public class ReviewRequest {
    private Long productId;
    private String Review;

    public ReviewRequest(Long productId, String review) {
        this.productId = productId;
        Review = review;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }
}
