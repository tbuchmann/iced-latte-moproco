package dev.moproco.icedlatte.dto;

import jakarta.validation.constraints.NotNull;

public record AddCartItemRequest(@NotNull Long productId, @NotNull Integer productQuantity) {}
