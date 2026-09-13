package dev.club.access.repository;

import dev.club.access.entity.Participant;
import dev.club.access.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface QrCodeRepository extends JpaRepository<QrCode, Long> {
    Optional<QrCode> findByUuid(UUID uuid);
    Optional<QrCode> findByParticipant(Participant participant);

    UUID uuid(UUID uuid);
}
