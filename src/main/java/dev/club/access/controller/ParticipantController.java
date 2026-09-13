package dev.club.access.controller;

import dev.club.access.dto.CreateParticipantRequest;
import dev.club.access.dto.CreateParticipantResponse;
import dev.club.access.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * Регистрация и просмотр участников.
 */
@RequestMapping("/api/participants")
@RestController
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;

    @GetMapping
    public Page<CreateParticipantResponse> findAll(Pageable pageable) {

        return participantService.findAll(pageable);
    }

    @PostMapping
    public CreateParticipantResponse create(@RequestBody CreateParticipantRequest createParticipantRequest) {
        return participantService.create(createParticipantRequest);
    }
}
