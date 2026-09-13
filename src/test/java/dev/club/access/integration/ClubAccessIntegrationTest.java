package dev.club.access.integration;

import dev.club.access.dto.CreateParticipantRequest;
import dev.club.access.model.AccessStatus;
import dev.club.access.service.AccessService;
import dev.club.access.service.ParticipantService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class ClubAccessIntegrationTest {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private AccessService accessService;


    @Test
    public void checkParticipantAccessStatus() {
        CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest("Test_user_1",
                LocalDate.parse("1993-04-19"));


        var response = participantService.create(createParticipantRequest);

        UUID qrUuid = response.qrUuid();


        AccessStatus resultGranted = accessService.checkAccess(qrUuid);
        AccessStatus resultUsed = accessService.checkAccess(qrUuid);

        assertEquals(AccessStatus.GRANTED, resultGranted,
                "Первое сканирование должно вернуть GRANTED");
        assertEquals(AccessStatus.ALREADY_USED, resultUsed,
                "Повторное сканирование того же QR должно вернуть ALREADY_USED");

    }
}