package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.SupportConversationEntity;
import java.time.LocalDateTime;

public interface SupportConversationEntityRepository extends JpaRepository<SupportConversationEntity, Long> {
    java.util.List<SupportConversationEntity> findByUserId(Long userId);
    java.util.Optional<SupportConversationEntity> findByCreatedAt(LocalDateTime createdAt);
    java.util.Optional<SupportConversationEntity> findByUpdatedAt(LocalDateTime updatedAt);
    java.util.Optional<SupportConversationEntity> findByLastMessageAt(LocalDateTime lastMessageAt);
    java.util.Optional<SupportConversationEntity> findByTelegramMessageThreadId(Long telegramMessageThreadId);
    java.util.Optional<SupportConversationEntity> findByTelegramFallbackMessageId(Long telegramFallbackMessageId);

}
