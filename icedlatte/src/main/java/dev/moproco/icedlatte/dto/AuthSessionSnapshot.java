package dev.moproco.icedlatte.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AuthSessionSnapshot(String userAgent, String ipAddress, LocalDateTime createdAt, LocalDateTime lastUsedAt, LocalDateTime expiresAt) {}
