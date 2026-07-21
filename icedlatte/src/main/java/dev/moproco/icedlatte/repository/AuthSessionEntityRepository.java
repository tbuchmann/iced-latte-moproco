package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.AuthSessionEntity;
import java.time.LocalDateTime;

public interface AuthSessionEntityRepository extends JpaRepository<AuthSessionEntity, Long> {
    java.util.List<AuthSessionEntity> findByUserId(Long userId);
    java.util.Optional<AuthSessionEntity> findByRefreshTokenHash(String refreshTokenHash);
    java.util.Optional<AuthSessionEntity> findByPreviousTokenHash(String previousTokenHash);
    java.util.Optional<AuthSessionEntity> findByCreatedAt(LocalDateTime createdAt);
    java.util.Optional<AuthSessionEntity> findByExpiresAt(LocalDateTime expiresAt);
    java.util.Optional<AuthSessionEntity> findByLastUsedAt(LocalDateTime lastUsedAt);
    java.util.Optional<AuthSessionEntity> findByRevokedAt(LocalDateTime revokedAt);
    java.util.Optional<AuthSessionEntity> findByUserAgent(String userAgent);
    java.util.Optional<AuthSessionEntity> findByIpAddress(String ipAddress);
    java.util.List<AuthSessionEntity> findByCompromisedTrue();
    java.util.List<AuthSessionEntity> findByCompromisedFalse();

}
