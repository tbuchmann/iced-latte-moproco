package dev.moproco.icedlatte.dto;

import java.util.List;

public record CartSnapshot(Long userId, Integer itemsQuantity, Double itemsTotalPrice, Integer productsQuantity, List<CartItemSnapshot> items) {}
