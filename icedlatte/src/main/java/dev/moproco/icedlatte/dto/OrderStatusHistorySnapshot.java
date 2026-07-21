package dev.moproco.icedlatte.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import dev.moproco.icedlatte.domain.OrderStatus;

public record OrderStatusHistorySnapshot(OrderStatus oldStatus, OrderStatus newStatus, String changedBy, String reason, LocalDateTime changedAt) {}
