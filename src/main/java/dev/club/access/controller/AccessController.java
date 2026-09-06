package dev.club.access.controller;

import dev.club.access.dto.DTO;
import dev.club.access.model.AccessStatus;
import dev.club.access.service.AccessService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** API турникета: проверка QR-кода. */
@RequestMapping("/api/access")
@RestController
public class AccessController {
    private final AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;

    }

    @PostMapping
    public AccessStatus checkAccess(@RequestBody DTO.AccessCheckRequest request) {

        return accessService.checkAccess(request.qrUuid());
    }

}
