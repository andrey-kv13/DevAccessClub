package dev.club.access.service;

import dev.club.access.dto.DTO;
import dev.club.access.entity.Participant;
import dev.club.access.model.AccessStatus;
import dev.club.access.repository.ParticipantRepository;
import dev.club.access.repository.UsedCodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Вспомогательные сценарии для локальной разработки и ручного прогона.
 */
@Service
public class DevService {
    private final ParticipantService participantService;
    private final AccessService accessService;
    private final UsedCodeRepository usedCodeRepository;
    private final ParticipantRepository participantRepository;

    public DevService(ParticipantService participantService, AccessService accessService, UsedCodeRepository usedCodeRepository, ParticipantRepository participantRepository) {
        this.participantService = participantService;
        this.accessService = accessService;
        this.usedCodeRepository = usedCodeRepository;
        this.participantRepository = participantRepository;
    }
    public DTO.SimulateScanResponse simulateScan() {
        List<Participant> list = participantService.findAll();
        if (list.isEmpty()) {
            throw new IllegalStateException("список пустой");
        }
        Participant chosen = list.get(ThreadLocalRandom.current().nextInt(list.size()));
        AccessStatus status = accessService.checkAccess(chosen.getQrUuid());
        return new DTO.SimulateScanResponse(status, chosen);

    }

    /** Очищает dev-данные. Сначала used_codes — из-за FK на participants. */
    @Transactional
    public void reset(){
        usedCodeRepository.deleteAll();
        participantRepository.deleteAll();
    }

    public List<Participant> generateParticipants(int count){
        List<Participant> created = new ArrayList<>();
        for(int i=0; i<count; i++){
            Participant p = new Participant();
            p.setFullName("User - " + (i + 1));
            p.setBirthDate(LocalDate.of(1990, 1, 1).plusDays(i));
            created.add(participantService.create(p));
        }

        return created;
    }
}
