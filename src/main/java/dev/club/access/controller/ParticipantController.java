package dev.club.access.controller;

import dev.club.access.entity.Participant;
import dev.club.access.service.ParticipantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Регистрация и просмотр участников. */
@RequestMapping("/api/participants")
@RestController
public class ParticipantController {
    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public List<Participant> findAll(){

        return participantService.findAll();
    }

    @PostMapping
    public Participant create(@RequestBody Participant participants){
        return participantService.create(participants);
    }
}
