package dev.club.access.service;

import dev.club.access.entity.Participant;
import dev.club.access.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

//Регистрация и получение участников клуба
@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<Participant> findAll() {
        return participantRepository.findAll();
    }

    //QR-код генерируется на сервере и не принимается от клиента
    public Participant create(Participant participants) {
        participants.setQrUuid(UUID.randomUUID());
        return participantRepository.save(participants);
    }


}