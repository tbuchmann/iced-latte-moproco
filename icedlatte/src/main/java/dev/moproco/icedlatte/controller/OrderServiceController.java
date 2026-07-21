package dev.moproco.icedlatte.controller;

import org.springframework.web.bind.annotation.*;
import dev.moproco.icedlatte.OrderService;
import org.springframework.http.ResponseEntity;
import java.util.List;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import dev.moproco.icedlatte.dto.CheckoutOrderRequest;
import dev.moproco.icedlatte.dto.OrderSnapshot;
import dev.moproco.icedlatte.dto.OrderStatusHistorySnapshot;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "/api/v1/orders")
public class OrderServiceController {

    private final OrderService orderService;

    public OrderServiceController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Orders", description = "Retrieves all orders for the user by userId. Returns a list of OrderSnapshot with id, userId, status, recipientName, recipientSurname, recipientPhone, itemsQuantity, itemsTotalPrice. Sorted by createdAt descending.")
    public List<OrderSnapshot> getOrders(@PathVariable Long userId) {
        return orderService.getOrders(userId);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Create Order", description = "Creates a new order from the user's shopping cart by userId. Retrieves the ShoppingCart and its ShoppingCartItems. Creates Order, OrderItem for each ShoppingCartItem, and OrderAddress from request.address. Sets status to CREATED, computes itemsQuantity and itemsTotalPrice from cart items. Closes the ShoppingCart (sets closedAt). Returns an OrderSnapshot.")
    public ResponseEntity<OrderSnapshot> createOrder(@PathVariable Long userId, @RequestBody @Valid CheckoutOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(userId, request));
    }

    @GetMapping("/getOrderById/{orderId}")
    @Operation(summary = "Get Order By Id", description = "Retrieves a single order by its ID. The 'orderId' parameter is the order ID. Looks up the Order by orderId. Returns an OrderSnapshot with id, userId, status, recipientName, recipientSurname, recipientPhone, itemsQuantity, itemsTotalPrice. Throws NotFoundException if not found.")
    public ResponseEntity<OrderSnapshot> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PutMapping("/cancelOrder/{orderId}")
    @Operation(summary = "Cancel Order", description = "Cancels an order by orderId. Only allowed if status is PENDING_PAYMENT or PAID. Sets the Order's status to CANCELLED. Creates and saves an OrderStatusHistory entry with oldStatus=previous status, newStatus=CANCELLED, changedBy='system'. Does not return anything (void).")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/requestRefund/{orderId}")
    @Operation(summary = "Request Refund", description = "Requests a refund for an order by orderId. Only allowed if status is DELIVERED. Sets the Order's status to REFUND_REQUESTED. Creates and saves an OrderStatusHistory entry with oldStatus=DELIVERED, newStatus=REFUND_REQUESTED. Does not return anything (void).")
    public ResponseEntity<Void> requestRefund(@PathVariable Long orderId) {
        orderService.requestRefund(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reorder/{orderId}")
    @Operation(summary = "Reorder", description = "Creates a new shopping cart from a previous order's items by orderId. Creates new ShoppingCartItems for each OrderItem in the order. Returns the new cart ID as a String.")
    public ResponseEntity<Long> reorder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.reorder(orderId));
    }

    @GetMapping("/getOrderHistory/{orderId}")
    @Operation(summary = "Get Order History", description = "Retrieves the status change history for an order by orderId. Returns a list of OrderStatusHistorySnapshot with id, oldStatus, newStatus, changedBy, reason, changedAt. Sorted by changedAt ascending.")
    public List<OrderStatusHistorySnapshot> getOrderHistory(@PathVariable Long orderId) {
        return orderService.getOrderHistory(orderId);
    }

}
