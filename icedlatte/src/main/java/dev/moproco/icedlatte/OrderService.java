package dev.moproco.icedlatte;

import java.util.List;
import dev.moproco.icedlatte.dto.CheckoutOrderRequest;
import dev.moproco.icedlatte.dto.OrderSnapshot;
import dev.moproco.icedlatte.dto.OrderStatusHistorySnapshot;

public interface OrderService {

    List<OrderSnapshot> getOrders(Long userId);
    OrderSnapshot createOrder(Long userId, CheckoutOrderRequest request);
    OrderSnapshot getOrderById(Long orderId);
    void cancelOrder(Long orderId);
    void requestRefund(Long orderId);
    Long reorder(Long orderId);
    List<OrderStatusHistorySnapshot> getOrderHistory(Long orderId);

}
