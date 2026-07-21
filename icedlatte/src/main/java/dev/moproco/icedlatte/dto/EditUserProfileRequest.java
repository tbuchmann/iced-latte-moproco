package dev.moproco.icedlatte.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EditUserProfileRequest(String firstName, String lastName, LocalDate birthDate, String phoneNumber) {}
