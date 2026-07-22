package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import dev.moproco.icedlatte.AdminOrderService;
import dev.moproco.icedlatte.domain.Order;
import dev.moproco.icedlatte.repository.OrderRepository;
import dev.moproco.icedlatte.domain.OrderAddress;
import dev.moproco.icedlatte.repository.OrderAddressRepository;
import dev.moproco.icedlatte.domain.OrderItem;
import dev.moproco.icedlatte.repository.OrderItemRepository;
import dev.moproco.icedlatte.domain.OrderStatusHistory;
import dev.moproco.icedlatte.repository.OrderStatusHistoryRepository;
import dev.moproco.icedlatte.domain.OrderStatus;
import dev.moproco.icedlatte.dto.OrderSnapshot;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;
    private final OrderAddressRepository orderAddressRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    public AdminOrderServiceImpl(OrderRepository orderRepository, OrderAddressRepository orderAddressRepository, OrderItemRepository orderItemRepository, OrderStatusHistoryRepository orderStatusHistoryRepository) {
        this.orderRepository = orderRepository;
        this.orderAddressRepository = orderAddressRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
    }

    /**
     * @prompt Retrieves all orders in the system. Returns a list of OrderSnapshot. Sorted by createdAt descending.
     * @generated NOT
     */
    @Override
    @Transactional
    public List<OrderSnapshot> getAllOrders() {
        // generated start
return orderRepository.findAll()
            .stream()
            .sorted((o1, o2) -> {
                java.time.LocalDateTime c1 = o1.getCancellationDeadline() != null ? o1.getCancellationDeadline() : java.time.LocalDateTime.MIN;
                java.time.LocalDateTime c2 = o2.getCancellationDeadline() != null ? o2.getCancellationDeadline() : java.time.LocalDateTime.MIN;
                return c2.compareTo(c1);
            })
            .map(order -> new OrderSnapshot(
                order.getUserId(),
                order.getStatus(),
                order.getRecipientName(),
                order.getRecipientSurname(),
                order.getRecipientPhone(),
                order.getItemsQuantity(),
                order.getItemsTotalPrice()
            ))
            .collect(java.util.stream.Collectors.toList());
// generated end
    }

    /**
     * @prompt Updates the status of an order by orderId to newStatus. Adds an OrderStatusHistory entry with oldStatus, newStatus, changedBy='admin'. Throws NotFoundException if order not found.
     * @generated NOT
     */
    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        // generated start
Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND, "Order not found with id: " + orderId));
    OrderStatus oldStatus = order.getStatus();
    order.setStatus(newStatus);
    orderRepository.save(order);
    OrderStatusHistory history = new OrderStatusHistory();
    history.setOrderId(orderId);
    history.setOldStatus(oldStatus);
    history.setNewStatus(newStatus);
    history.setChangedBy("admin");
    history.setChangedAt(java.time.LocalDateTime.now());
    orderStatusHistoryRepository.save(history);
// generated end
    }

    /**
     * @prompt Deletes an order by orderId and cascades to OrderItem, OrderAddress, and OrderStatusHistory. Throws NotFoundException if order not found.
     * @generated NOT
     */
    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        // generated start
Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Order not found with id: " + orderId));
    orderItemRepository.findByOrderId(orderId).forEach(orderItemRepository::delete);
    orderStatusHistoryRepository.findByOrderId(orderId).forEach(orderStatusHistoryRepository::delete);
    if (order.getAddress() != null) {
        orderAddressRepository.delete(order.getAddress());
    }
    orderRepository.delete(order);
// generated end
    }

}
