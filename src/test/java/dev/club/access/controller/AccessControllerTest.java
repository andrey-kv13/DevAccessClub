package dev.club.access.controller;

import dev.club.access.model.AccessStatus;
import dev.club.access.service.AccessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(AccessController.class)
public class AccessControllerTest {
    private UUID uuid = UUID.randomUUID();
    private String errorUuid = "d8dc205a-3e78-458f-bbfc-";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccessService accessService;

    @Test
    void checkAccess_validRequest_returnsGranted() throws Exception {

        when(accessService.checkAccess(uuid)).thenReturn(AccessStatus.GRANTED);

        mockMvc.perform(post("/api/access")
                        .contentType(APPLICATION_JSON)
                        .content("{\"qrUuid\":\"" + uuid + "\"}"))
                .andExpect(result -> assertEquals(200, result.getResponse().getStatus(),
                        "POST /api/access с валидным UUID должен вернуть 200"))
                .andExpect(result -> assertEquals("\"GRANTED\"", result.getResponse().getContentAsString(),
                        "Тело ответа должно содержать GRANTED"));
        verify(accessService, times(1)).checkAccess(uuid);

    }

    @Test
    void incorrectUuidReturns400Code() throws Exception {


        mockMvc.perform(post("/api/access")
                        .contentType(APPLICATION_JSON)
                        .content("{\"qrUuid\":\"" + errorUuid + "\"}"))
                .andExpect(result -> assertEquals(400, result.getResponse().getStatus(),
                        "POST /api/access с некорректным UUID должен вернуть 400"));

        verifyNoInteractions(accessService);

    }


}





