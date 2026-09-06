package dev.club.access.service;

import dev.club.access.entity.Participant;
import dev.club.access.model.AccessStatus;
import dev.club.access.repository.ParticipantRepository;
import dev.club.access.repository.UsedCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccessServiceTest {
    private ParticipantRepository participantRepository;
    private UsedCodeRepository usedCodeRepository;
    private AccessService accessService;
    private UUID qrUuid;


    @BeforeEach
    void setUp() {
        participantRepository = mock(ParticipantRepository.class);
        usedCodeRepository = mock(UsedCodeRepository.class);
        accessService = new dev.club.access.service.AccessService(usedCodeRepository, participantRepository);
        qrUuid = UUID.randomUUID();
    }

    @Test
    void checkAccess_unknownQr_returnsUnknown() {

       when(participantRepository.findByQrUuid(qrUuid)).thenReturn(Optional.empty());

       AccessStatus result = accessService.checkAccess(qrUuid);

       assertEquals(AccessStatus.UNKNOWN, result,
               "Неизвестный QR-код должен вернуть UNKNOWN");
       verify(usedCodeRepository, never()).save(any());
    }

    @Test
    void checkAccess_usedQr_returnsAlreadyUsed() {
        Participant participant = new Participant();
        participant.setQrUuid(qrUuid);
        when(participantRepository.findByQrUuid(qrUuid)).thenReturn(Optional.of(participant));
        when(usedCodeRepository.existsByQrUuid(qrUuid)).thenReturn(true);

        AccessStatus result = accessService.checkAccess(qrUuid);

        assertEquals(AccessStatus.ALREADY_USED, result,
                "Повторное сканирование должно вернуть ALREADY_USED");
        verify(usedCodeRepository, never()).save(any());

    }

    @Test
    void checkAccess_grantQr_returnsGrantAccess() {
        Participant participant = new Participant();
        participant.setQrUuid(qrUuid);
        when(participantRepository.findByQrUuid(qrUuid)).thenReturn(Optional.of(participant));
        when(usedCodeRepository.existsByQrUuid(qrUuid)).thenReturn(false);

        AccessStatus result = accessService.checkAccess(qrUuid);

        assertEquals(AccessStatus.GRANTED, result,
                "Первое сканирование валидного QR должно вернуть GRANTED");
        verify(usedCodeRepository, times(1)).save(any());

    }
}