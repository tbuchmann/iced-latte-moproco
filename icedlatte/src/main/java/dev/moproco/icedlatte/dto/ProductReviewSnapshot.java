package dev.moproco.icedlatte.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProductReviewSnapshot(Long userId, Long productId, String text, Integer productRating, Integer likesCount, Integer dislikesCount, LocalDateTime createdAt) {}
