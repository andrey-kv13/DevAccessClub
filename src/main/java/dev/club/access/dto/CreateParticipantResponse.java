package dev.club.access.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CreateParticipantResponse(
        Long id,
        String fullName,
        LocalDate birthDate,
        UUID qrUuid
) {}