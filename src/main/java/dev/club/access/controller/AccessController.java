package dev.club.access.controller;

import dev.club.access.dto.AccessCheckRequest;
import dev.club.access.model.AccessStatus;
import dev.club.access.service.AccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API турникета: проверка QR-кода.
 */
@RequestMapping("/api/access")
@RestController
@RequiredArgsConstructor
public class AccessController {
    private final AccessService accessService;

    @PostMapping
    public AccessStatus checkAccess(@RequestBody AccessCheckRequest request) {

        return accessService.checkAccess(request.qrUuid());
    }

}
