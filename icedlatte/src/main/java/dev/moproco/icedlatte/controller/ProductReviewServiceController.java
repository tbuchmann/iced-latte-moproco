package dev.moproco.icedlatte.controller;

import org.springframework.web.bind.annotation.*;
import dev.moproco.icedlatte.ProductReviewService;
import org.springframework.http.ResponseEntity;
import java.util.List;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import dev.moproco.icedlatte.dto.AddProductReviewLikeRequest;
import dev.moproco.icedlatte.dto.AddProductReviewRequest;
import dev.moproco.icedlatte.dto.DeleteProductReviewRequest;
import dev.moproco.icedlatte.dto.GetProductReviewRequest;
import dev.moproco.icedlatte.dto.ProductReviewSnapshot;
import dev.moproco.icedlatte.dto.RatingSummarySnapshot;

@RestController
@RequestMapping("/api/v1/products/reviews")
@Tag(name = "/api/v1/products/reviews")
public class ProductReviewServiceController {

    private final ProductReviewService productReviewService;

    public ProductReviewServiceController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Product Reviews And Ratings", description = "Retrieves all reviews for a product by productId. Returns a list of ProductReviewSnapshot with id, userId, productId, text, productRating, likesCount, dislikesCount, createdAt. Sorted by createdAt descending.")
    public List<ProductReviewSnapshot> getProductReviewsAndRatings(@PathVariable Long productId) {
        return productReviewService.getProductReviewsAndRatings(productId);
    }

    @GetMapping("/getRatingAndReviewStat/{productId}")
    @Operation(summary = "Get Rating And Review Stat", description = "Retrieves aggregated rating statistics for a product by productId. Returns a RatingSummarySnapshot with averageRating and reviewsCount. Returns zeros if no reviews exist.")
    public ResponseEntity<RatingSummarySnapshot> getRatingAndReviewStat(@PathVariable Long productId) {
        return ResponseEntity.ok(productReviewService.getRatingAndReviewStat(productId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Add New Product Review", description = "Adds a new review for a product by productId. The 'request' parameter contains userId, text, and rating. Checks that the user hasn't already reviewed this product. Creates a ProductReview with userId, productId, text, productRating=rating, likesCount=0, dislikesCount=0. Updates ProductInfo.averageRating and reviewsCount. Returns a ProductReviewSnapshot.")
    public ResponseEntity<ProductReviewSnapshot> addNewProductReview(@PathVariable Long productId, @RequestBody @Valid AddProductReviewRequest request) {
        return ResponseEntity.ok(productReviewService.addNewProductReview(productId, request));
    }

    @PutMapping("/deleteProductReview/{productId}")
    @Operation(summary = "Delete Product Review", description = "Deletes a product review. The 'productId' parameter is the product ID, 'request' contains productReviewId. Verifies that the review belongs to the given productId. Updates ProductInfo.averageRating and reviewsCount after deletion. Also deletes all ProductReviewLike for this review.")
    public ResponseEntity<Void> deleteProductReview(@PathVariable Long productId, @RequestBody @Valid DeleteProductReviewRequest request) {
        productReviewService.deleteProductReview(productId, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/getProductReview/{productId}")
    @Operation(summary = "Get Product Review", description = "Retrieves the user's review for a product. The 'productId' parameter is the product ID, 'request' contains userId. Returns a ProductReviewSnapshot. Returns null if the user hasn't reviewed this product.")
    public ResponseEntity<ProductReviewSnapshot> getProductReview(@PathVariable Long productId, @RequestBody @Valid GetProductReviewRequest request) {
        return ResponseEntity.ok(productReviewService.getProductReview(productId, request));
    }

    @GetMapping("/getUserReviews/{userId}")
    @Operation(summary = "Get User Reviews", description = "Retrieves all reviews written by a user by userId. Returns a list of ProductReviewSnapshot. Sorted by createdAt descending.")
    public List<ProductReviewSnapshot> getUserReviews(@PathVariable Long userId) {
        return productReviewService.getUserReviews(userId);
    }

    @PostMapping("/{id}")
    @Operation(summary = "Add Product Review Like", description = "Adds or updates a like/dislike on a product review. The 'request' parameter contains userId and isLike. If a ProductReviewLike already exists for the same userId and review, updates isLike. Otherwise creates a new one. Increments likesCount or dislikesCount on the ProductReview accordingly. If toggling from like to dislike, decrements likesCount and increments dislikesCount.")
    public ResponseEntity<Void> addProductReviewLike(@PathVariable Long productId, @RequestBody @Valid AddProductReviewLikeRequest request) {
        productReviewService.addProductReviewLike(productId, request);
        return ResponseEntity.noContent().build();
    }

}
