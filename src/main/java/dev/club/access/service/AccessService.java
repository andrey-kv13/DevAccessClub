package dev.club.access.service;

import dev.club.access.entity.QrCode;
import dev.club.access.entity.UsedCode;
import dev.club.access.model.AccessStatus;
import dev.club.access.repository.QrCodeRepository;
import dev.club.access.repository.UsedCodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


/**
 * Проверка QR-кода при входе в клуб.
 */
@Service
@RequiredArgsConstructor
public class AccessService {
    private final UsedCodeRepository usedCodeRepository;
    private final QrCodeRepository qrCodeRepository;

    /**
     * Первое успешное сканирование фиксируется в used_codes и возвращает GRANTED.
     * Повторное сканирование того же QR — ALREADY_USED, неизвестный QR — UNKNOWN.
     */
    @Transactional
    public AccessStatus checkAccess(UUID qrUuid) {

        Optional<QrCode> optionalQrCode = qrCodeRepository.findByUuid(qrUuid);

        if (optionalQrCode.isEmpty()) {
            return AccessStatus.UNKNOWN;
        }

        QrCode qrCode = optionalQrCode.get();

        if (usedCodeRepository.existsByQrCode(qrCode)) {
            return AccessStatus.ALREADY_USED;
        }

        UsedCode usedCode = new UsedCode();
        usedCode.setQrCode(qrCode);
        usedCode.setUsedAt(LocalDateTime.now());
        usedCodeRepository.save(usedCode);

        return AccessStatus.GRANTED;
    }
}
