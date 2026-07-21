package dev.moproco.icedlatte.dto;

import dev.moproco.icedlatte.domain.PaymentStatus;

public record CheckoutStatusSnapshot(PaymentStatus status, String providerSessionId) {}
