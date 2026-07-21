package dev.moproco.icedlatte.dto;

import jakarta.validation.constraints.NotNull;

public record DeliveryAddressRequest(@NotNull String label, @NotNull String line, @NotNull String city, @NotNull String country, @NotNull String postcode, Boolean isDefault) {}
