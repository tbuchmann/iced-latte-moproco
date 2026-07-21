package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.ShoppingCart;
import java.time.LocalDateTime;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    java.util.List<ShoppingCart> findByUserId(Long userId);
    java.util.Optional<ShoppingCart> findByCreatedAt(LocalDateTime createdAt);
    java.util.Optional<ShoppingCart> findByClosedAt(LocalDateTime closedAt);
    java.util.Optional<ShoppingCart> findByItemsQuantity(Integer itemsQuantity);
    java.util.Optional<ShoppingCart> findByItemsTotalPrice(Double itemsTotalPrice);
    java.util.Optional<ShoppingCart> findByProductsQuantity(Integer productsQuantity);

}
