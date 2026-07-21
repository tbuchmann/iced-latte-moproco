package dev.moproco.icedlatte.dto;

import dev.moproco.icedlatte.domain.OrderStatus;

public record OrderSnapshot(Long userId, OrderStatus status, String recipientName, String recipientSurname, String recipientPhone, Integer itemsQuantity, Double itemsTotalPrice) {}
