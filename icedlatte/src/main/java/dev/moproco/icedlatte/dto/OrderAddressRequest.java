package dev.moproco.icedlatte.dto;

import jakarta.validation.constraints.NotNull;

public record OrderAddressRequest(@NotNull String country, @NotNull String city, @NotNull String line, @NotNull String postcode) {}
