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
        throw new UnsupportedOperationException("Not yet implemented");
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
        throw new UnsupportedOperationException("Not yet implemented");
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
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

}
