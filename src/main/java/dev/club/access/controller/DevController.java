package dev.club.access.controller;

import dev.club.access.dto.DTO;
import dev.club.access.entity.Participant;
import dev.club.access.service.DevService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Dev API: подготовка данных и симуляция сканирования. Не для prod. */
@RequestMapping("/api/dev")
@RestController
public class DevController {
    private final DevService devService;

    public DevController(DevService devService) {
        this.devService = devService;
    }

    /** Случайный участник → checkAccess. */
    @PostMapping("/simulate-scan")
    public DTO.SimulateScanResponse simulateScan(){
        return devService.simulateScan();

    }

    @PostMapping("/reset")
    public void reset() {
        devService.reset();
    }

    @PostMapping("/generate")
    public List<Participant> generateParticipant(int count){
        return devService.generateParticipants(count);

    }

    /** reset + generateParticipants. */
    @PostMapping("/setup")
    public List<Participant> setup(@RequestParam(defaultValue = "5") int count) {
        devService.reset();
        return devService.generateParticipants(count);
    }
}


