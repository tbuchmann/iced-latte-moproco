package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.SupportMessageEntity;
import dev.moproco.icedlatte.domain.SupportMessageDeliveryStatus;
import dev.moproco.icedlatte.domain.SupportMessageSenderType;
import java.time.LocalDateTime;

public interface SupportMessageEntityRepository extends JpaRepository<SupportMessageEntity, Long> {
    java.util.List<SupportMessageEntity> findByConversationId(Long conversationId);
    java.util.Optional<SupportMessageEntity> findBySenderType(SupportMessageSenderType senderType);
    java.util.List<SupportMessageEntity> findBySenderUserId(Long senderUserId);
    java.util.Optional<SupportMessageEntity> findByClientMessageId(String clientMessageId);
    java.util.Optional<SupportMessageEntity> findByBody(String body);
    java.util.Optional<SupportMessageEntity> findByNormalizedBody(String normalizedBody);
    java.util.Optional<SupportMessageEntity> findByDeliveryStatus(SupportMessageDeliveryStatus deliveryStatus);
    java.util.List<SupportMessageEntity> findByOperatorInspectionRequiredTrue();
    java.util.List<SupportMessageEntity> findByOperatorInspectionRequiredFalse();
    java.util.List<SupportMessageEntity> findByVisibleToCustomerTrue();
    java.util.List<SupportMessageEntity> findByVisibleToCustomerFalse();
    java.util.Optional<SupportMessageEntity> findByTelegramUpdateId(Long telegramUpdateId);
    java.util.Optional<SupportMessageEntity> findByTelegramMessageId(Long telegramMessageId);
    java.util.Optional<SupportMessageEntity> findByCreatedAt(LocalDateTime createdAt);

}
