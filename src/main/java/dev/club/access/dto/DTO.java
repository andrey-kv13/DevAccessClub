package dev.club.access.dto;

import dev.club.access.entity.Participant;
import dev.club.access.model.AccessStatus;

import java.util.UUID;

public class DTO {

    public record AccessCheckRequest(UUID qrUuid) {}
    public record SimulateScanResponse(AccessStatus status, Participant participant) {}
}
