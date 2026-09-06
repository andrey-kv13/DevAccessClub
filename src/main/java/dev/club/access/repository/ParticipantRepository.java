package dev.club.access.repository;

import dev.club.access.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Optional<Participant> findByQrUuid(UUID qrUuid);

}
