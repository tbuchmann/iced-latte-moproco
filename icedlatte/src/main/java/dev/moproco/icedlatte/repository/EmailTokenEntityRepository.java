package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.EmailTokenEntity;
import dev.moproco.icedlatte.domain.TokenPurpose;
import java.time.LocalDateTime;

public interface EmailTokenEntityRepository extends JpaRepository<EmailTokenEntity, Long> {
    java.util.List<EmailTokenEntity> findByUserId(Long userId);
    java.util.Optional<EmailTokenEntity> findByToken(String token);
    java.util.Optional<EmailTokenEntity> findByPurpose(TokenPurpose purpose);
    java.util.Optional<EmailTokenEntity> findByExpiresAt(LocalDateTime expiresAt);
    java.util.Optional<EmailTokenEntity> findByUsedAt(LocalDateTime usedAt);

}
