package dev.moproco.icedlatte.dto;

import jakarta.validation.constraints.NotNull;

public record RevokeSessionRequest(@NotNull Long sessionId) {}
