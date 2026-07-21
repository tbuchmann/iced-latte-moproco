package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.Order;
import dev.moproco.icedlatte.domain.OrderStatus;
import java.time.LocalDateTime;

public interface OrderRepository extends JpaRepository<Order, Long> {
    java.util.List<Order> findByUserId(Long userId);
    java.util.Optional<Order> findBySessionId(Long sessionId);
    java.util.Optional<Order> findByStatus(OrderStatus status);
    java.util.Optional<Order> findByVersion(Integer version);
    java.util.Optional<Order> findByIdempotencyKey(String idempotencyKey);
    java.util.Optional<Order> findByRecipientName(String recipientName);
    java.util.Optional<Order> findByRecipientSurname(String recipientSurname);
    java.util.Optional<Order> findByRecipientPhone(String recipientPhone);
    java.util.Optional<Order> findByItemsQuantity(Integer itemsQuantity);
    java.util.Optional<Order> findByItemsTotalPrice(Double itemsTotalPrice);
    java.util.Optional<Order> findByCancellationDeadline(LocalDateTime cancellationDeadline);
    java.util.Optional<Order> findByStripePaymentIntentId(String stripePaymentIntentId);
    java.util.Optional<Order> findByRefundReason(String refundReason);
    java.util.Optional<Order> findByRefundedAt(LocalDateTime refundedAt);

}
