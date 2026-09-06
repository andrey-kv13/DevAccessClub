package dev.club.access.repository;

import dev.club.access.entity.UsedCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsedCodeRepository extends JpaRepository<UsedCode, Long> {

    boolean existsByQrUuid(UUID qrUuid);
}
