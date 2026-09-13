package dev.club.access.controller;

import dev.club.access.dto.SimulateScanResponse;
import dev.club.access.entity.Participant;
import dev.club.access.service.DevService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Dev API: подготовка данных и симуляция сканирования. Не для prod. */
@RequestMapping("/api/dev")
@RestController
@RequiredArgsConstructor
public class DevController {
    private final DevService devService;

    /** Случайный участник → checkAccess. */
    @PostMapping("/simulate-scan")
    public SimulateScanResponse simulateScan(){
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


