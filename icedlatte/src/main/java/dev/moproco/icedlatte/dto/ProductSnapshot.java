package dev.moproco.icedlatte.dto;

public record ProductSnapshot(String name, String description, Double price, Integer quantity, Boolean active, Double averageRating, Integer reviewsCount, String brandName, String sellerName, Integer weight) {}
