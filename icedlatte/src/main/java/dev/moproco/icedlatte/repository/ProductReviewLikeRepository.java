package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.ProductReviewLike;


public interface ProductReviewLikeRepository extends JpaRepository<ProductReviewLike, Long> {
    java.util.Optional<ProductReviewLike> findByProductReviewId(Long productReviewId);
    java.util.List<ProductReviewLike> findByProductId(Long productId);
    java.util.List<ProductReviewLike> findByUserId(Long userId);
    java.util.List<ProductReviewLike> findByIsLikeTrue();
    java.util.List<ProductReviewLike> findByIsLikeFalse();

}
