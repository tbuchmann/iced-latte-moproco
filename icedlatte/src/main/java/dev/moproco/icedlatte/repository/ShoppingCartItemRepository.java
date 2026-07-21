package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.ShoppingCartItem;


public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {
    java.util.Optional<ShoppingCartItem> findByVersion(Integer version);
    java.util.List<ShoppingCartItem> findByProductId(Long productId);
    java.util.Optional<ShoppingCartItem> findByProductQuantity(Integer productQuantity);

}
