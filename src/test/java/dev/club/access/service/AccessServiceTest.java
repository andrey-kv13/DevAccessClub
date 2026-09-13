package dev.club.access.service;

import dev.club.access.entity.QrCode;
import dev.club.access.model.AccessStatus;
import dev.club.access.repository.QrCodeRepository;
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
    private QrCodeRepository qrCodeRepository;
    private UsedCodeRepository usedCodeRepository;
    private AccessService accessService;
    private UUID qrUuid;

    @BeforeEach
    void setUp() {
        qrCodeRepository = mock(QrCodeRepository.class);
        usedCodeRepository = mock(UsedCodeRepository.class);
        qrUuid = UUID.randomUUID();
        this.accessService = new AccessService(usedCodeRepository, qrCodeRepository);
    }

    @Test
    void checkAccess_unknownQr_returnsUnknown() {
        when(qrCodeRepository.findByUuid(qrUuid)).thenReturn(Optional.empty());

        AccessStatus result = accessService.checkAccess(qrUuid);

        assertEquals(AccessStatus.UNKNOWN, result,
                "Неизвестный QR-код должен вернуть UNKNOWN");
        verify(usedCodeRepository, never()).save(any());
    }

    @Test
    void checkAccess_usedQr_returnsAlreadyUsed() {
        QrCode qrCode = new QrCode();
        qrCode.setUuid(qrUuid);

        when(qrCodeRepository.findByUuid(qrUuid)).thenReturn(Optional.of(qrCode));
        when(usedCodeRepository.existsByQrCode(qrCode)).thenReturn(true);

        AccessStatus result = accessService.checkAccess(qrUuid);

        assertEquals(AccessStatus.ALREADY_USED, result,
                "Повторное сканирование должно вернуть ALREADY_USED");
        verify(usedCodeRepository, never()).save(any());
    }

    @Test
    void checkAccess_grantQr_returnsGrantAccess() {
        QrCode qrCode = new QrCode();
        qrCode.setUuid(qrUuid);

        when(qrCodeRepository.findByUuid(qrUuid)).thenReturn(Optional.of(qrCode));
        when(usedCodeRepository.existsByQrCode(qrCode)).thenReturn(false);

        AccessStatus result = accessService.checkAccess(qrUuid);

        assertEquals(AccessStatus.GRANTED, result,
                "Первое сканирование валидного QR должно вернуть GRANTED");
        verify(usedCodeRepository, times(1)).save(any());
    }
}
