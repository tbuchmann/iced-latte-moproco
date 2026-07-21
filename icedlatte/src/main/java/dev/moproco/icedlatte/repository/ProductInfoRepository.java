package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.ProductInfo;
import java.time.LocalDateTime;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
    java.util.Optional<ProductInfo> findByVersion(Long version);
    java.util.Optional<ProductInfo> findByName(String name);
    java.util.Optional<ProductInfo> findByDescription(String description);
    java.util.Optional<ProductInfo> findByPrice(Double price);
    java.util.Optional<ProductInfo> findByQuantity(Integer quantity);
    java.util.List<ProductInfo> findByActiveTrue();
    java.util.List<ProductInfo> findByActiveFalse();
    java.util.Optional<ProductInfo> findByAverageRating(Double averageRating);
    java.util.Optional<ProductInfo> findByReviewsCount(Integer reviewsCount);
    java.util.Optional<ProductInfo> findByBrandName(String brandName);
    java.util.Optional<ProductInfo> findBySellerName(String sellerName);
    java.util.Optional<ProductInfo> findByOriginCountry(String originCountry);
    java.util.Optional<ProductInfo> findByWeight(Integer weight);
    java.util.Optional<ProductInfo> findByLength(Integer length);
    java.util.Optional<ProductInfo> findByWidth(Integer width);
    java.util.Optional<ProductInfo> findByHeight(Integer height);
    java.util.Optional<ProductInfo> findBySoldProductsCount(Integer soldProductsCount);
    java.util.Optional<ProductInfo> findByDiscount(Integer discount);
    java.util.Optional<ProductInfo> findByDateAdded(LocalDateTime dateAdded);
    java.util.Optional<ProductInfo> findByPopularityScore(Integer popularityScore);
    java.util.Optional<ProductInfo> findByAiSummary(String aiSummary);

}
