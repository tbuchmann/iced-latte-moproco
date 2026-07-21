package dev.moproco.icedlatte.dto;

import jakarta.validation.constraints.NotNull;

public record AddProductReviewRequest(@NotNull Long userId, @NotNull String text, @NotNull Integer rating) {}
