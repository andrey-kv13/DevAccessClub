package dev.club.access.repository;

import dev.club.access.entity.QrCode;
import dev.club.access.entity.UsedCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsedCodeRepository extends JpaRepository<UsedCode, Long> {

    boolean existsByQrCode(QrCode qrCode);
}
