package dev.moproco.icedlatte;

import java.util.List;
import dev.moproco.icedlatte.domain.OrderStatus;
import dev.moproco.icedlatte.dto.OrderSnapshot;

public interface AdminOrderService {

    List<OrderSnapshot> getAllOrders();
    void updateOrderStatus(Long orderId, OrderStatus newStatus);
    void deleteOrder(Long orderId);

}
