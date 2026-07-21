package dev.moproco.icedlatte.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateCartItemRequest(@NotNull Long itemId, @NotNull Integer quantity) {}
