package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.UserAvatarUpload;
import dev.moproco.icedlatte.domain.UserAvatarUploadStatus;
import java.time.LocalDateTime;

public interface UserAvatarUploadRepository extends JpaRepository<UserAvatarUpload, Long> {
    java.util.List<UserAvatarUpload> findByUserId(Long userId);
    java.util.Optional<UserAvatarUpload> findByStatus(UserAvatarUploadStatus status);
    java.util.Optional<UserAvatarUpload> findByOriginalBucket(String originalBucket);
    java.util.Optional<UserAvatarUpload> findByOriginalKey(String originalKey);
    java.util.Optional<UserAvatarUpload> findByProcessedBucket(String processedBucket);
    java.util.Optional<UserAvatarUpload> findByProcessedKey(String processedKey);
    java.util.Optional<UserAvatarUpload> findByContentType(String contentType);
    java.util.Optional<UserAvatarUpload> findByOriginalSizeBytes(Long originalSizeBytes);
    java.util.Optional<UserAvatarUpload> findByProcessedSizeBytes(Long processedSizeBytes);
    java.util.Optional<UserAvatarUpload> findByImageWidth(Integer imageWidth);
    java.util.Optional<UserAvatarUpload> findByImageHeight(Integer imageHeight);
    java.util.Optional<UserAvatarUpload> findBySha256(String sha256);
    java.util.Optional<UserAvatarUpload> findByClientIdempotencyKey(String clientIdempotencyKey);
    java.util.List<UserAvatarUpload> findByActiveTrue();
    java.util.List<UserAvatarUpload> findByActiveFalse();
    java.util.Optional<UserAvatarUpload> findByCreatedAt(LocalDateTime createdAt);
    java.util.Optional<UserAvatarUpload> findByExpiresAt(LocalDateTime expiresAt);

}
