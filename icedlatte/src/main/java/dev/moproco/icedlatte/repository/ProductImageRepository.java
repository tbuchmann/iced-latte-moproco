package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.ProductImage;


public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    java.util.List<ProductImage> findByProductId(Long productId);
    java.util.Optional<ProductImage> findByUrl(String url);
    java.util.Optional<ProductImage> findByPosition(Integer position);

}
