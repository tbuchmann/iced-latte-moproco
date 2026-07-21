package dev.moproco.icedlatte.dto;

import jakarta.validation.constraints.NotNull;

public record OAuthCallbackRequest(@NotNull String code, @NotNull String state) {}
