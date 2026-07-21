package dev.moproco.icedlatte.dto;

import jakarta.validation.constraints.NotNull;

public record CheckoutOrderRequest(@NotNull String recipientName, @NotNull String recipientSurname, String recipientPhone, @NotNull OrderAddressRequest address) {}
