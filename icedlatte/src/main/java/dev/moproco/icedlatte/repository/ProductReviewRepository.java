package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.ProductReview;
import java.time.LocalDateTime;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    java.util.List<ProductReview> findByUserId(Long userId);
    java.util.List<ProductReview> findByProductId(Long productId);
    java.util.Optional<ProductReview> findByCreatedAt(LocalDateTime createdAt);
    java.util.Optional<ProductReview> findByText(String text);
    java.util.Optional<ProductReview> findByProductRating(Integer productRating);
    java.util.Optional<ProductReview> findByLikesCount(Integer likesCount);
    java.util.Optional<ProductReview> findByDislikesCount(Integer dislikesCount);

}
