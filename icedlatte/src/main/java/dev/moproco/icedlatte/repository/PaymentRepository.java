package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.Payment;
import dev.moproco.icedlatte.domain.PaymentProvider;
import dev.moproco.icedlatte.domain.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    java.util.List<Payment> findByOrderId(Long orderId);
    java.util.List<Payment> findByUserId(Long userId);
    java.util.Optional<Payment> findByProvider(PaymentProvider provider);
    java.util.Optional<Payment> findByProviderSessionId(String providerSessionId);
    java.util.Optional<Payment> findByProviderPaymentIntentId(String providerPaymentIntentId);
    java.util.Optional<Payment> findByStatus(PaymentStatus status);
    java.util.Optional<Payment> findByAmountMinor(Long amountMinor);
    java.util.Optional<Payment> findByCurrency(String currency);
    java.util.Optional<Payment> findByRawEventId(String rawEventId);
    java.util.Optional<Payment> findByLatestEventType(String latestEventType);
    java.util.Optional<Payment> findByCheckoutIdempotencyKey(String checkoutIdempotencyKey);

}
