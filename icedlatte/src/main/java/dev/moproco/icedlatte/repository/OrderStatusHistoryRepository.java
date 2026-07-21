package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.OrderStatusHistory;
import dev.moproco.icedlatte.domain.OrderStatus;
import java.time.LocalDateTime;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
    java.util.List<OrderStatusHistory> findByOrderId(Long orderId);
    java.util.Optional<OrderStatusHistory> findByOldStatus(OrderStatus oldStatus);
    java.util.Optional<OrderStatusHistory> findByNewStatus(OrderStatus newStatus);
    java.util.Optional<OrderStatusHistory> findByChangedBy(String changedBy);
    java.util.Optional<OrderStatusHistory> findByReason(String reason);
    java.util.Optional<OrderStatusHistory> findByChangedAt(LocalDateTime changedAt);

}
