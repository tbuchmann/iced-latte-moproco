package dev.moproco.icedlatte.dto;

import jakarta.validation.constraints.NotNull;

public record DeleteProductReviewRequest(@NotNull Long productReviewId) {}
