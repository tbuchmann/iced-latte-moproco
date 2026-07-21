package dev.moproco.icedlatte.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import dev.moproco.icedlatte.domain.SupportMessageDeliveryStatus;
import dev.moproco.icedlatte.domain.SupportMessageSenderType;

public record SupportMessageSnapshot(Long conversationId, SupportMessageSenderType senderType, String body, SupportMessageDeliveryStatus deliveryStatus, LocalDateTime createdAt) {}
