package dev.club.access.manager;

import dev.club.access.model.AccessStatus;
import dev.club.access.service.AccessService;
import dev.club.access.service.DevService;
import org.springframework.stereotype.Service;

@Service
public class ClubAccessManager {
    private final DevService devService;
    private final AccessService accessService;


    public ClubAccessManager(DevService devService, AccessService accessService) {
        this.devService = devService;
        this.accessService = accessService;
    }
    
    public void runDemo(){
        devService.reset();
        var participants = devService.generateParticipants(3);
        var qrUuid = participants.get(0).getQrUuid();

        var first = accessService.checkAccess(qrUuid);
        var second = accessService.checkAccess(qrUuid);

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
