package dev.moproco.icedlatte;

import java.util.List;
import dev.moproco.icedlatte.dto.AddProductReviewLikeRequest;
import dev.moproco.icedlatte.dto.AddProductReviewRequest;
import dev.moproco.icedlatte.dto.DeleteProductReviewRequest;
import dev.moproco.icedlatte.dto.GetProductReviewRequest;
import dev.moproco.icedlatte.dto.ProductReviewSnapshot;
import dev.moproco.icedlatte.dto.RatingSummarySnapshot;

public interface ProductReviewService {

    List<ProductReviewSnapshot> getProductReviewsAndRatings(Long productId);
    RatingSummarySnapshot getRatingAndReviewStat(Long productId);
    ProductReviewSnapshot addNewProductReview(Long productId, AddProductReviewRequest request);
    void deleteProductReview(Long productId, DeleteProductReviewRequest request);
    ProductReviewSnapshot getProductReview(Long productId, GetProductReviewRequest request);
    List<ProductReviewSnapshot> getUserReviews(Long userId);
    void addProductReviewLike(Long productId, AddProductReviewLikeRequest request);

}
