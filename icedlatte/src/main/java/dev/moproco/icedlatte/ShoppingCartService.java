package dev.moproco.icedlatte;

import java.util.List;
import dev.moproco.icedlatte.dto.AddCartItemRequest;
import dev.moproco.icedlatte.dto.CartSnapshot;
import dev.moproco.icedlatte.dto.UpdateCartItemRequest;

public interface ShoppingCartService {

    CartSnapshot getShoppingCart(Long userId);
    void addNewItemToShoppingCart(Long userId, AddCartItemRequest request);
    void updateProductQuantityInShoppingCartItem(Long userId, UpdateCartItemRequest request);
    void deleteItemsFromShoppingCart(Long userId, List<Long> itemIds);

}
