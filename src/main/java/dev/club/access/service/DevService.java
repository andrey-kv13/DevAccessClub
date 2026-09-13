package dev.club.access.service;

import dev.club.access.dto.CreateParticipantRequest;
import dev.club.access.dto.SimulateScanResponse;
import dev.club.access.entity.Participant;
import dev.club.access.entity.QrCode;
import dev.club.access.model.AccessStatus;
import dev.club.access.repository.ParticipantRepository;
import dev.club.access.repository.QrCodeRepository;
import dev.club.access.repository.UsedCodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Вспомогательные сценарии для локальной разработки и ручного прогона.
 */
@Service
@RequiredArgsConstructor
public class DevService {
    private final ParticipantService participantService;
    private final AccessService accessService;
    private final UsedCodeRepository usedCodeRepository;
    private final ParticipantRepository participantRepository;
    private final QrCodeRepository qrCodeRepository;

    public SimulateScanResponse simulateScan() {
        List<Participant> list = participantRepository.findAll();
        if (list.isEmpty()) {
            throw new IllegalStateException("список пустой");
        }
        Participant chosen = list.get(ThreadLocalRandom.current().nextInt(list.size()));
        QrCode qrCode = qrCodeRepository.findByParticipant(chosen)
                .orElseThrow(() -> new IllegalStateException(
                        "QR-код не найден для участника id=" + chosen.getId()));
        UUID uuid = qrCode.getUuid();
        AccessStatus status = accessService.checkAccess(uuid);
        return new SimulateScanResponse(status, chosen);

    }

    /** Очищает dev-данные: used_codes → qr_codes → participants (цепочка FK). */
    @Transactional
    public void reset() {
        usedCodeRepository.deleteAll();
        qrCodeRepository.deleteAll();
        participantRepository.deleteAll();
    }

    public List<Participant> generateParticipants(int count) {
        for (int i = 0; i < count; i++) {
            CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest(("User - " + (i + 1)),
                    LocalDate.of(1990, 1, 1).plusDays(i));

            participantService.create(createParticipantRequest);
        }

        return participantRepository.findAll();
    }
}
