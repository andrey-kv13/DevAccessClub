package dev.club.access.controller;

import dev.club.access.dto.CreateParticipantRequest;
import dev.club.access.dto.CreateParticipantResponse;
import dev.club.access.entity.Participant;
import dev.club.access.entity.QrCode;
import dev.club.access.service.ParticipantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

        CreateParticipantResponse response = new CreateParticipantResponse(1L, "TestParticipant", LocalDate.parse("1989-08-02"), uuid);

        when(participantService.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(response)));

        mockMvc.perform(get("/api/participants"))
                .andExpect(result -> assertEquals(200, result.getResponse().getStatus(),
                        "GET /api/participants должен вернуть 200"))
                .andExpect(jsonPath("$.content[0].fullName").value("TestParticipant"));
        verify(participantService, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void createParticipantSuccess() throws Exception {
        CreateParticipantRequest createParticipantRequest = new CreateParticipantRequest("TestParticipant", LocalDate.parse("1989-08-02"));


        CreateParticipantResponse createParticipantResponse = new CreateParticipantResponse(
                1L,
                "Test_participant_12",
                LocalDate.parse("1989-08-02"),
                uuid
        );

        when(participantService.create(any(CreateParticipantRequest.class))).thenReturn(createParticipantResponse);

        mockMvc.perform(post("/api/participants")
                        .contentType(APPLICATION_JSON)
                        .content("{\"fullName\":\"" + createParticipantRequest.fullName() + "\"," +
                                "\"birthDate\":\"" + createParticipantRequest.birthDate() + "\"}"))
                .andExpect(result -> assertEquals(200, result.getResponse().getStatus(),
                        "POST /api/participants должен вернуть 200"))
                .andExpect(jsonPath("$.fullName").value("Test_participant_12"))
                .andExpect(jsonPath("$.qrUuid").value(uuid.toString()));

        verify(participantService, times(1)).create(any(CreateParticipantRequest.class));
    }
}
