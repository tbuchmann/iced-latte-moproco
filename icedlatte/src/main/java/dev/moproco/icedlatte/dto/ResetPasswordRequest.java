package dev.moproco.icedlatte.dto;

import jakarta.validation.constraints.NotNull;

public record ResetPasswordRequest(@NotNull String resetToken, @NotNull String newPassword) {}
