package dev.club.access.service;

import dev.club.access.dto.CreateParticipantRequest;
import dev.club.access.dto.CreateParticipantResponse;
import dev.club.access.entity.Participant;
import dev.club.access.entity.QrCode;
import dev.club.access.repository.ParticipantRepository;
import dev.club.access.repository.QrCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParticipantServiceTest {
    private ParticipantService participantService;
    private Participant participant;
    private ParticipantRepository participantRepository;
    private QrCodeRepository qrCodeRepository;

    @BeforeEach
    void setUp() {
        participantRepository = mock(ParticipantRepository.class);
        qrCodeRepository = mock(QrCodeRepository.class);
        this.participantService = new ParticipantService(participantRepository, qrCodeRepository);

    }

    @Test
    void create_generatesQrUuidAndSaves() {
        CreateParticipantRequest request = new CreateParticipantRequest("TestParticipant", LocalDate.parse("1993-04-19"));
        participant = new Participant();
        participant.setFullName(request.fullName());
        participant.setBirthDate(request.birthDate());

        when(participantRepository.save(any())).thenReturn(participant);

        CreateParticipantResponse result = participantService.create(request);

        assertNotNull(result.qrUuid(),
                "После create() ответ должен содержать qrUuid");
        verify(participantRepository, times(1)).save(any());
        verify(qrCodeRepository, times(1)).save(any());
    }
}
