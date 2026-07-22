package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.FavoriteListEntity;
import java.time.LocalDateTime;

public interface FavoriteListEntityRepository extends JpaRepository<FavoriteListEntity, Long> {
    java.util.List<FavoriteListEntity> findByUserId(Long userId);
    java.util.Optional<FavoriteListEntity> findByUpdatedAt(LocalDateTime updatedAt);    

}
