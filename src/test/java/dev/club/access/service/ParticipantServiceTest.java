package dev.club.access.service;

import dev.club.access.entity.Participant;
import dev.club.access.repository.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParticipantServiceTest {
    private  ParticipantService participantService;
    private  Participant participant;
    private ParticipantRepository participantRepository;

    @BeforeEach
    void setUp() {
        participantRepository = mock(ParticipantRepository.class);
        participantService = new ParticipantService(participantRepository);

    }

    @Test
    void create_generatesQrUuidAndSaves(){
        participant = new Participant();
        participant.setFullName("TestParticipant");
        participant.setBirthDate(LocalDate.parse("1993-04-19"));

        when(participantRepository.save(any())).thenReturn(participant);

        Participant result = participantService.create(participant);

        assertNotNull(result.getQrUuid(),
                "После create() участник должен получить qrUuid");
        verify(participantRepository, times(1)).save(any());

    }


}
