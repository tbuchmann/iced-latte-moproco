package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.LoginAttemptEntity;
import java.time.LocalDateTime;

public interface LoginAttemptEntityRepository extends JpaRepository<LoginAttemptEntity, Long> {
    java.util.Optional<LoginAttemptEntity> findByUserEmail(String userEmail);
    java.util.Optional<LoginAttemptEntity> findByAttempts(Integer attempts);
    java.util.Optional<LoginAttemptEntity> findByExpirationDatetime(LocalDateTime expirationDatetime);
    java.util.List<LoginAttemptEntity> findByIsUserLockedTrue();
    java.util.List<LoginAttemptEntity> findByIsUserLockedFalse();
    java.util.Optional<LoginAttemptEntity> findByLastModified(LocalDateTime lastModified);

}
