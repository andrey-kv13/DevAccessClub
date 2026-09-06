package dev.club.access.service;

import dev.club.access.entity.Participant;
import dev.club.access.entity.UsedCode;
import dev.club.access.model.AccessStatus;
import dev.club.access.repository.ParticipantRepository;
import dev.club.access.repository.UsedCodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


/**
 * Проверка QR-кода при входе в клуб.
 */
@Service
public class AccessService {
    private final UsedCodeRepository usedCodeRepository;
    private final ParticipantRepository participantRepository;


    public AccessService(UsedCodeRepository usedCodeRepository, ParticipantRepository participantRepository) {
        this.usedCodeRepository = usedCodeRepository;
        this.participantRepository = participantRepository;
    }

    /**
     * Первое успешное сканирование фиксируется в used_codes и возвращает GRANTED.
     * Повторное сканирование того же QR — ALREADY_USED, неизвестный QR — UNKNOWN.
     */
    @Transactional
    public AccessStatus checkAccess(UUID qrUuid) {
        Optional<Participant> optionalParticipant = participantRepository.findByQrUuid(qrUuid);

        if (optionalParticipant.isEmpty()) {

            return AccessStatus.UNKNOWN;
        }

        if (usedCodeRepository.existsByQrUuid(qrUuid)) {

            return AccessStatus.ALREADY_USED;

        } else {

            Participant participant = optionalParticipant.get();
            UsedCode usedCode = new UsedCode();
            usedCode.setQrUuid(qrUuid);
            usedCode.setParticipant(participant);
            usedCode.setUsedAt(LocalDateTime.now());
            usedCodeRepository.save(usedCode);

            return AccessStatus.GRANTED;
        }

    }


}
