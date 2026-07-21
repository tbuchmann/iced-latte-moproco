package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    java.util.List<OrderItem> findByOrderId(Long orderId);
    java.util.List<OrderItem> findByProductId(Long productId);
    java.util.Optional<OrderItem> findByProductPrice(Double productPrice);
    java.util.Optional<OrderItem> findByProductName(String productName);
    java.util.Optional<OrderItem> findByProductsQuantity(Integer productsQuantity);

}
