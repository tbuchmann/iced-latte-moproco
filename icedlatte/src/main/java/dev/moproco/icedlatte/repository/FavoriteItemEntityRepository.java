package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.FavoriteItemEntity;


public interface FavoriteItemEntityRepository extends JpaRepository<FavoriteItemEntity, Long> {
    java.util.Optional<FavoriteItemEntity> findByVersion(Integer version);
    java.util.List<FavoriteItemEntity> findByProductId(Long productId);

}
