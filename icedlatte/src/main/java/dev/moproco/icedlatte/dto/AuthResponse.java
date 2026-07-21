package dev.moproco.icedlatte.dto;

import jakarta.validation.constraints.NotNull;

public record AuthResponse(@NotNull String accessToken, @NotNull String refreshToken, @NotNull Long userId) {}
