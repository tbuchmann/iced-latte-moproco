package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import dev.moproco.icedlatte.ShoppingCartService;
import dev.moproco.icedlatte.domain.ShoppingCart;
import dev.moproco.icedlatte.repository.ShoppingCartRepository;
import dev.moproco.icedlatte.domain.ShoppingCartItem;
import dev.moproco.icedlatte.repository.ShoppingCartItemRepository;
import dev.moproco.icedlatte.dto.AddCartItemRequest;
import dev.moproco.icedlatte.dto.CartSnapshot;
import dev.moproco.icedlatte.dto.UpdateCartItemRequest;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ShoppingCartItemRepository shoppingCartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    /**
     * @prompt Retrieves the user's shopping cart by userId. Returns a CartSnapshot with id, userId, itemsQuantity, itemsTotalPrice, productsQuantity, and a list of CartItemSnapshot. If no cart exists, creates a new one.
     * @generated NOT
     */
    @Override
    @Transactional
    public CartSnapshot getShoppingCart(Long userId) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

    /**
     * @prompt Adds an item to the user's cart by userId. If a ShoppingCartItem with the same productId already exists, increments productQuantity by request.productQuantity. Otherwise creates a new ShoppingCartItem. Recalculates itemsQuantity and itemsTotalPrice.
     * @generated NOT
     */
    @Override
    @Transactional
    public void addNewItemToShoppingCart(Long userId, AddCartItemRequest request) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

    /**
     * @prompt Updates the quantity of a cart item. The 'userId' parameter is the user's ID, 'request' contains itemId and quantity. If quantity is 0, removes the item. Recalculates itemsQuantity and itemsTotalPrice for the cart.
     * @generated NOT
     */
    @Override
    @Transactional
    public void updateProductQuantityInShoppingCartItem(Long userId, UpdateCartItemRequest request) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

    /**
     * @prompt Removes the specified items by their IDs from the user's cart. Recalculates itemsQuantity and itemsTotalPrice.
     * @generated NOT
     */
    @Override
    @Transactional
    public void deleteItemsFromShoppingCart(Long userId, List<Long> itemIds) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

}
