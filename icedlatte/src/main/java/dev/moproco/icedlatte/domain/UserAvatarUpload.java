package dev.moproco.icedlatte.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "user_avatar_upload")
public class UserAvatarUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserAvatarUploadStatus status;

    @Column(name = "original_bucket")
    private String originalBucket;

    @Column(name = "original_key")
    private String originalKey;

    @Column(name = "processed_bucket")
    private String processedBucket;

    @Column(name = "processed_key")
    private String processedKey;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "original_size_bytes")
    private Long originalSizeBytes;

    @Column(name = "processed_size_bytes")
    private Long processedSizeBytes;

    @Column(name = "image_width")
    private Integer imageWidth;

    @Column(name = "image_height")
    private Integer imageHeight;

    @Column(name = "sha256")
    private String sha256;

    @Column(name = "client_idempotency_key")
    private String clientIdempotencyKey;

    @NotNull
    @Column(name = "active")
    private Boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserAvatarUploadStatus getStatus() {
        return this.status;
    }

    public void setStatus(UserAvatarUploadStatus status) {
        this.status = status;
    }

    public String getOriginalBucket() {
        return this.originalBucket;
    }

    public void setOriginalBucket(String originalBucket) {
        this.originalBucket = originalBucket;
    }

    public String getOriginalKey() {
        return this.originalKey;
    }

    public void setOriginalKey(String originalKey) {
        this.originalKey = originalKey;
    }

    public String getProcessedBucket() {
        return this.processedBucket;
    }

    public void setProcessedBucket(String processedBucket) {
        this.processedBucket = processedBucket;
    }

    public String getProcessedKey() {
        return this.processedKey;
    }

    public void setProcessedKey(String processedKey) {
        this.processedKey = processedKey;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getOriginalSizeBytes() {
        return this.originalSizeBytes;
    }

    public void setOriginalSizeBytes(Long originalSizeBytes) {
        this.originalSizeBytes = originalSizeBytes;
    }

    public Long getProcessedSizeBytes() {
        return this.processedSizeBytes;
    }

    public void setProcessedSizeBytes(Long processedSizeBytes) {
        this.processedSizeBytes = processedSizeBytes;
    }

    public Integer getImageWidth() {
        return this.imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getImageHeight() {
        return this.imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getSha256() {
        return this.sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getClientIdempotencyKey() {
        return this.clientIdempotencyKey;
    }

    public void setClientIdempotencyKey(String clientIdempotencyKey) {
        this.clientIdempotencyKey = clientIdempotencyKey;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return this.expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

}
