package dev.club.access.service;

import dev.club.access.dto.CreateParticipantRequest;
import dev.club.access.dto.CreateParticipantResponse;
import dev.club.access.entity.Participant;
import dev.club.access.entity.QrCode;
import dev.club.access.repository.ParticipantRepository;
import dev.club.access.repository.QrCodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/** Регистрация и получение участников клуба. */
@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final QrCodeRepository qrCodeRepository;

    public Page<CreateParticipantResponse> findAll(Pageable pageable) {
        Page<Participant> page = participantRepository.findAll(pageable);
        return page.map(participant -> {
            QrCode qrCode = qrCodeRepository.findByParticipant(participant)
                    .orElseThrow(() -> new IllegalStateException(
                            "QR-код не найден для участника id=" + participant.getId()));
            return new CreateParticipantResponse(
                    participant.getId(),
                    participant.getFullName(),
                    participant.getBirthDate(),
                    qrCode.getUuid());
        });
    }



    @Transactional
    public CreateParticipantResponse create(CreateParticipantRequest createParticipantRequest) {

        Participant participant = new Participant();
        participant.setFullName(createParticipantRequest.fullName());
        participant.setBirthDate(createParticipantRequest.birthDate());
        Participant saved = participantRepository.save(participant);
        QrCode qrCode = new QrCode();
        UUID uuid = UUID.randomUUID();
        qrCode.setUuid(uuid);
        qrCode.setParticipant(saved);
        qrCodeRepository.save(qrCode);

        return new CreateParticipantResponse(
                saved.getId(),
                saved.getFullName(),
                saved.getBirthDate(),
                uuid);

    }
}