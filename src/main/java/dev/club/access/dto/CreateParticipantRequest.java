package dev.club.access.dto;

import java.time.LocalDate;

public record CreateParticipantRequest (String fullName, LocalDate birthDate){}
