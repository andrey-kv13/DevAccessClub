package dev.club.access.manager;

import dev.club.access.model.AccessStatus;
import dev.club.access.repository.QrCodeRepository;
import dev.club.access.service.AccessService;
import dev.club.access.service.DevService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubAccessManager {
    private final DevService devService;
    private final AccessService accessService;
    private final QrCodeRepository qrCodeRepository;

    public void runDemo() {
        devService.reset();
        var participants = devService.generateParticipants(3);
        var participant = participants.get(0);
        var qrCode = qrCodeRepository.findByParticipant(participant)
                .orElseThrow(() -> new IllegalStateException(
                        "QR-код не найден для участника id = " + participant.getId()));

        UUID uuid = qrCode.getUuid();

        var first = accessService.checkAccess(uuid);
        var second = accessService.checkAccess(uuid);

        if (first != AccessStatus.GRANTED) {
            throw new IllegalStateException(
                    "Первое сканирование должно вернуть GRANTED, получено: " + first);
        }
        if (second != AccessStatus.ALREADY_USED) {
            throw new IllegalStateException(
                    "Повторное сканирование должно вернуть ALREADY_USED, получено: " + second);
        }

        System.out.println("1st check: " + first);
        System.out.println("2nd check: " + second);
    }

}
