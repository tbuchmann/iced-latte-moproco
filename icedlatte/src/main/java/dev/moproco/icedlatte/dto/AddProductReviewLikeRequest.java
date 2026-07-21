package dev.moproco.icedlatte.dto;

import jakarta.validation.constraints.NotNull;

public record AddProductReviewLikeRequest(@NotNull Long userId, @NotNull Boolean isLike) {}
