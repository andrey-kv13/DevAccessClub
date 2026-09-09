package dev.club.access.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @OneToOne(optional = false)
    @JoinColumn(name = "qr_code_id")
    private QrCode qrCode;

    @Override
    public String toString() {
        return "UsedCode{" +
                "id=" + id +
                ", usedAt=" + usedAt +
                '}';
    }
}


