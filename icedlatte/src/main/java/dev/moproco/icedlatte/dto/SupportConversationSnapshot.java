package dev.moproco.icedlatte.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SupportConversationSnapshot(Long userId, LocalDateTime createdAt, LocalDateTime lastMessageAt) {}
