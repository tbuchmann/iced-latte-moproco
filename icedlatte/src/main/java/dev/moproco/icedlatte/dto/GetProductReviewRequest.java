package dev.moproco.icedlatte.dto;

import jakarta.validation.constraints.NotNull;

public record GetProductReviewRequest(@NotNull Long userId) {}
