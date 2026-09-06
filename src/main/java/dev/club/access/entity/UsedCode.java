package dev.club.access.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/** Факт однократного использования QR-кода для входа. */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "used_codes")
@AllArgsConstructor
public class UsedCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime usedAt;
    @Column(unique = true)
    private UUID qrUuid;
    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @Override
    public String toString() {
        return "UsedCode{" +
                "id=" + id +
                ", usedAt=" + usedAt +
                ", qrUuid=" + qrUuid +
                ", participant=" + participant +
                '}';
    }
}


