package dev.moproco.icedlatte.dto;

import jakarta.validation.constraints.NotNull;

public record RegisterRequest(@NotNull String email, @NotNull String password, @NotNull String firstName, @NotNull String lastName) {}
