package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import dev.moproco.icedlatte.ProductReviewService;
import dev.moproco.icedlatte.domain.ProductInfo;
import dev.moproco.icedlatte.repository.ProductInfoRepository;
import dev.moproco.icedlatte.domain.ProductReview;
import dev.moproco.icedlatte.repository.ProductReviewRepository;
import dev.moproco.icedlatte.domain.ProductReviewLike;
import dev.moproco.icedlatte.repository.ProductReviewLikeRepository;
import dev.moproco.icedlatte.dto.AddProductReviewLikeRequest;
import dev.moproco.icedlatte.dto.AddProductReviewRequest;
import dev.moproco.icedlatte.dto.DeleteProductReviewRequest;
import dev.moproco.icedlatte.dto.GetProductReviewRequest;
import dev.moproco.icedlatte.dto.ProductReviewSnapshot;
import dev.moproco.icedlatte.dto.RatingSummarySnapshot;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductInfoRepository productInfoRepository;
    private final ProductReviewRepository productReviewRepository;
    private final ProductReviewLikeRepository productReviewLikeRepository;

    public ProductReviewServiceImpl(ProductInfoRepository productInfoRepository, ProductReviewRepository productReviewRepository, ProductReviewLikeRepository productReviewLikeRepository) {
        this.productInfoRepository = productInfoRepository;
        this.productReviewRepository = productReviewRepository;
        this.productReviewLikeRepository = productReviewLikeRepository;
    }

    /**
     * @prompt Retrieves all reviews for a product by productId. Returns a list of ProductReviewSnapshot with id, userId, productId, text, productRating, likesCount, dislikesCount, createdAt. Sorted by createdAt descending.
     * @generated NOT
     */
    @Override
    @Transactional
    public List<ProductReviewSnapshot> getProductReviewsAndRatings(Long productId) {
        // generated start
List<ProductReview> reviews = productReviewRepository.findByProductId(productId);
    return reviews.stream()
            .sorted((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()))
            .map(r -> new ProductReviewSnapshot(r.getUserId(), r.getProductId(), r.getText(), r.getProductRating(), r.getLikesCount(), r.getDislikesCount(), r.getCreatedAt()))
            .collect(java.util.stream.Collectors.toList());
// generated end
    }

    /**
     * @prompt Retrieves aggregated rating statistics for a product by productId. Returns a RatingSummarySnapshot with averageRating and reviewsCount. Returns zeros if no reviews exist.
     * @generated NOT
     */
    @Override
    @Transactional
    public RatingSummarySnapshot getRatingAndReviewStat(Long productId) {
        // generated start
List<ProductReview> reviews = productReviewRepository.findByProductId(productId);
    if (reviews.isEmpty()) {
        return new RatingSummarySnapshot(0.0, 0);
    }
    double avg = reviews.stream().mapToInt(ProductReview::getProductRating).average().orElse(0.0);
    return new RatingSummarySnapshot(avg, reviews.size());
// generated end
    }

    /**
     * @prompt Adds a new review for a product by productId. The 'request' parameter contains userId, text, and rating. Checks that the user hasn't already reviewed this product. Creates a ProductReview with userId, productId, text, productRating=rating, likesCount=0, dislikesCount=0. Updates ProductInfo.averageRating and reviewsCount. Returns a ProductReviewSnapshot.
     * @generated NOT
     */
    @Override
    @Transactional
    public ProductReviewSnapshot addNewProductReview(Long productId, AddProductReviewRequest request) {
        // generated start
ProductInfo productInfo = productInfoRepository.findById(productId)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND, "Product not found with id: " + productId));

    java.util.List<ProductReview> existingReviews = productReviewRepository.findByProductId(productId);
    boolean alreadyReviewed = existingReviews.stream()
            .anyMatch(review -> review.getUserId().equals(request.userId()));
    if (alreadyReviewed) {
        throw new IllegalArgumentException("User has already reviewed this product");
    }

    ProductReview review = new ProductReview();
    review.setUserId(request.userId());
    review.setProductId(productId);
    review.setText(request.text());
    review.setProductRating(request.rating());
    review.setLikesCount(0);
    review.setDislikesCount(0);
    review.setCreatedAt(java.time.LocalDateTime.now());
    productReviewRepository.save(review);

    List<ProductReview> allReviews = productReviewRepository.findByProductId(productId);
    double avg = allReviews.stream().mapToInt(ProductReview::getProductRating).average().orElse(0.0);
    productInfo.setAverageRating(avg);
    productInfo.setReviewsCount(allReviews.size());
    productInfoRepository.save(productInfo);

    return new ProductReviewSnapshot(review.getUserId(), review.getProductId(), review.getText(),
            review.getProductRating(), review.getLikesCount(), review.getDislikesCount(), review.getCreatedAt());
// generated end
    }

    /**
     * @prompt Deletes a product review. The 'productId' parameter is the product ID, 'request' contains productReviewId. Verifies that the review belongs to the given productId. Updates ProductInfo.averageRating and reviewsCount after deletion. Also deletes all ProductReviewLike for this review.
     * @generated NOT
     */
    @Override
    @Transactional
    public void deleteProductReview(Long productId, DeleteProductReviewRequest request) {
        // generated start
ProductReview review = productReviewRepository.findById(request.productReviewId())
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND, "Review not found with id: " + request.productReviewId()));

    if (!review.getProductId().equals(productId)) {
        throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST, "Review does not belong to the specified product");
    }

    productReviewLikeRepository.findByProductReviewId(review.getId()).ifPresent(productReviewLikeRepository::delete);

    productReviewRepository.delete(review);

    ProductInfo productInfo = productInfoRepository.findById(productId)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND, "Product not found with id: " + productId));

    List<ProductReview> remainingReviews = productReviewRepository.findByProductId(productId);
    if (remainingReviews.isEmpty()) {
        productInfo.setAverageRating(0.0);
        productInfo.setReviewsCount(0);
    } else {
        double avg = remainingReviews.stream().mapToInt(ProductReview::getProductRating).average().orElse(0.0);
        productInfo.setAverageRating(avg);
        productInfo.setReviewsCount(remainingReviews.size());
    }
    productInfoRepository.save(productInfo);
// generated end
    }

    /**
     * @prompt Retrieves the user's review for a product. The 'productId' parameter is the product ID, 'request' contains userId. Returns a ProductReviewSnapshot. Returns null if the user hasn't reviewed this product.
     * @generated NOT
     */
    @Override
    @Transactional
    public ProductReviewSnapshot getProductReview(Long productId, GetProductReviewRequest request) {
        // generated start
List<ProductReview> reviews = productReviewRepository.findByProductId(productId);
    return reviews.stream()
            .filter(r -> r.getUserId().equals(request.userId()))
            .findFirst()
            .map(r -> new ProductReviewSnapshot(r.getUserId(), r.getProductId(), r.getText(), r.getProductRating(), r.getLikesCount(), r.getDislikesCount(), r.getCreatedAt()))
            .orElse(null);
// generated end
    }

    /**
     * @prompt Retrieves all reviews written by a user by userId. Returns a list of ProductReviewSnapshot. Sorted by createdAt descending.
     * @generated NOT
     */
    @Override
    @Transactional
    public List<ProductReviewSnapshot> getUserReviews(Long userId) {
        // generated start
List<ProductReview> reviews = productReviewRepository.findByUserId(userId);
    return reviews.stream()
            .sorted((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()))
            .map(r -> new ProductReviewSnapshot(r.getUserId(), r.getProductId(), r.getText(), r.getProductRating(), r.getLikesCount(), r.getDislikesCount(), r.getCreatedAt()))
            .collect(java.util.stream.Collectors.toList());
// generated end
    }

    /**
     * @prompt Adds or updates a like/dislike on a product review. The 'request' parameter contains userId and isLike. If a ProductReviewLike already exists for the same userId and review, updates isLike. Otherwise creates a new one. Increments likesCount or dislikesCount on the ProductReview accordingly. If toggling from like to dislike, decrements likesCount and increments dislikesCount.
     * @generated NOT
     */
    @Override
    @Transactional
    public void addProductReviewLike(Long productId, AddProductReviewLikeRequest request) {
        // generated start
ProductReview review = productReviewRepository.findById(productId)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND, "Review not found with id: " + productId));

    java.util.Optional<ProductReviewLike> existingLike = productReviewLikeRepository.findByProductReviewId(review.getId())
            .filter(like -> like.getUserId().equals(request.userId()));

    if (existingLike.isPresent()) {
        ProductReviewLike like = existingLike.get();
        boolean oldIsLike = like.getIsLike();
        boolean newIsLike = request.isLike();
        if (oldIsLike != newIsLike) {
            if (oldIsLike) {
                review.setLikesCount(review.getLikesCount() - 1);
                review.setDislikesCount(review.getDislikesCount() + 1);
            } else {
                review.setDislikesCount(review.getDislikesCount() - 1);
                review.setLikesCount(review.getLikesCount() + 1);
            }
        }
        like.setIsLike(newIsLike);
        productReviewLikeRepository.save(like);
    } else {
        ProductReviewLike newLike = new ProductReviewLike();
        newLike.setProductReviewId(review.getId());
        newLike.setProductId(productId);
        newLike.setUserId(request.userId());
        newLike.setIsLike(request.isLike());
        productReviewLikeRepository.save(newLike);
        if (request.isLike()) {
            review.setLikesCount(review.getLikesCount() + 1);
        } else {
            review.setDislikesCount(review.getDislikesCount() + 1);
        }
    }
    productReviewRepository.save(review);
// generated end
    }

}
