package dev.club.access.dto;

import dev.club.access.entity.Participant;
import dev.club.access.model.AccessStatus;

public record SimulateScanResponse(AccessStatus status, Participant participant) {}
