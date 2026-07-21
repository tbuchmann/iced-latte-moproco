package dev.moproco.icedlatte.controller;

import org.springframework.web.bind.annotation.*;
import dev.moproco.icedlatte.AdminOrderService;
import org.springframework.http.ResponseEntity;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import dev.moproco.icedlatte.domain.OrderStatus;
import dev.moproco.icedlatte.dto.OrderSnapshot;

@RestController
@RequestMapping("/api/v1/admin/orders")
@Tag(name = "/api/v1/admin/orders")
public class AdminOrderServiceController {

    private final AdminOrderService adminOrderService;

    public AdminOrderServiceController(AdminOrderService adminOrderService) {
        this.adminOrderService = adminOrderService;
    }

    @GetMapping
    @Operation(summary = "Get All Orders", description = "Retrieves all orders in the system. Returns a list of OrderSnapshot. Sorted by createdAt descending.")
    public List<OrderSnapshot> getAllOrders() {
        return adminOrderService.getAllOrders();
    }

    @PostMapping("/updateOrderStatus")
    @Operation(summary = "Update Order Status")
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        adminOrderService.updateOrderStatus(orderId, newStatus);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Order", description = "Deletes an order by orderId and cascades to OrderItem, OrderAddress, and OrderStatusHistory. Throws NotFoundException if order not found.")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        adminOrderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

}
