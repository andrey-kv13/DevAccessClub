package dev.club.access.controller;

import dev.club.access.entity.Participant;
import dev.club.access.service.ParticipantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ParticipantController.class)
public class ParticipantControllerTest {

    private Participant participant;
    private UUID uuid = UUID.randomUUID();


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ParticipantService participantService;

    @Test
    public void findAllReturnsParticipants() throws Exception {
        participant = new Participant();
        participant.setFullName("TestParticipant");
        participant.setBirthDate(LocalDate.parse("1993-04-19"));

        when(participantService.findAll()).thenReturn(List.of(participant));

        mockMvc.perform(get("/api/participants"))
                .andExpect(result -> assertEquals(200, result.getResponse().getStatus(),
                        "GET /api/participants должен вернуть 200"))
                .andExpect(jsonPath("$[0].fullName").value("TestParticipant"));
        verify(participantService, times(1)).findAll();

    }

    @Test
    public void createParticipantSuccess() throws Exception {
        participant = new Participant();
        participant.setFullName("Test_participant_12");
        participant.setBirthDate(LocalDate.parse("1989-08-02"));
        participant.setQrUuid(uuid);

        when(participantService.create(any(Participant.class))).thenReturn(participant);

        mockMvc.perform(post("/api/participants")
                .contentType(APPLICATION_JSON)
                .content("{\"fullName\":\"" + participant.getFullName() + "\"," +
                        "\"birthDate\":\"" + participant.getBirthDate() + "\"}"))

                .andExpect(result -> assertEquals(200, result.getResponse().getStatus(),
                        "POST /api/participants должен вернуть 200"))
                .andExpect(jsonPath("$.fullName").value("Test_participant_12"))
                .andExpect(jsonPath("$.qrUuid").value(uuid.toString()));

        verify(participantService, times(1)).create(any(Participant.class));




    }

}
