package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.StripeWebhookEvent;
import dev.moproco.icedlatte.domain.WebhookEventStatus;
import java.time.LocalDateTime;

public interface StripeWebhookEventRepository extends JpaRepository<StripeWebhookEvent, Long> {
    java.util.Optional<StripeWebhookEvent> findByStripeEventId(String stripeEventId);
    java.util.Optional<StripeWebhookEvent> findByEventType(String eventType);
    java.util.Optional<StripeWebhookEvent> findByStatus(WebhookEventStatus status);
    java.util.Optional<StripeWebhookEvent> findByReceivedAt(LocalDateTime receivedAt);
    java.util.Optional<StripeWebhookEvent> findByProcessedAt(LocalDateTime processedAt);
    java.util.Optional<StripeWebhookEvent> findByFailureReason(String failureReason);

}
