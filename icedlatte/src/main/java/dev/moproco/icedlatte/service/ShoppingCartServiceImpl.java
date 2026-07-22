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
import dev.moproco.icedlatte.dto.CartItemSnapshot;


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
ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
            .stream()
            .filter(c -> c.getClosedAt() == null)
            .findFirst()
            .orElseGet(() -> {
                ShoppingCart newCart = new ShoppingCart();
                newCart.setUserId(userId);
                newCart.setCreatedAt(java.time.LocalDateTime.now());
                newCart.setItemsQuantity(0);
                newCart.setItemsTotalPrice(0.0);
                newCart.setProductsQuantity(0);
                return shoppingCartRepository.save(newCart);
            });

    List<CartItemSnapshot> itemSnapshots = cart.getItems() != null
            ? cart.getItems().stream()
                .map(item -> new CartItemSnapshot(item.getId(), item.getProductQuantity()))
                .collect(java.util.stream.Collectors.toList())
            : java.util.Collections.emptyList();

    return new CartSnapshot(cart.getUserId(), cart.getItemsQuantity(), cart.getItemsTotalPrice(), cart.getProductsQuantity(), itemSnapshots);
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
ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
            .stream()
            .filter(c -> c.getClosedAt() == null)
            .findFirst()
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Shopping cart not found for user " + userId));

    java.util.Optional<ShoppingCartItem> existingItem = cart.getItems().stream()
            .filter(item -> item.getProductId().equals(request.productId()))
            .findFirst();

    if (existingItem.isPresent()) {
        ShoppingCartItem item = existingItem.get();
        item.setProductQuantity(item.getProductQuantity() + request.productQuantity());
        shoppingCartItemRepository.save(item);
    } else {
        ShoppingCartItem newItem = new ShoppingCartItem();
        newItem.setProductId(request.productId());
        newItem.setProductQuantity(request.productQuantity());
        newItem.setCart(cart);
        newItem.setVersion(1);
        shoppingCartItemRepository.save(newItem);
    }

    int itemsQuantity = cart.getItems().size();
    double itemsTotalPrice = 0.0;
    for (ShoppingCartItem item : cart.getItems()) {
        itemsTotalPrice += item.getProductQuantity() * 10.0;
    }
    cart.setItemsQuantity(itemsQuantity);
    cart.setItemsTotalPrice(itemsTotalPrice);
    shoppingCartRepository.save(cart);
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
ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
        .stream()
        .filter(c -> c.getClosedAt() == null)
        .findFirst()
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Shopping cart not found for user " + userId));

ShoppingCartItem item = cart.getItems().stream()
        .filter(i -> i.getId().equals(request.itemId()))
        .findFirst()
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Cart item not found with id " + request.itemId()));

if (request.quantity() == 0) {
    cart.getItems().remove(item);
    shoppingCartItemRepository.delete(item);
} else {
    item.setProductQuantity(request.quantity());
    shoppingCartItemRepository.save(item);
}

int itemsQuantity = cart.getItems().size();
double itemsTotalPrice = 0.0;
for (ShoppingCartItem cartItem : cart.getItems()) {
    itemsTotalPrice += cartItem.getProductQuantity() * 10.0;
}
cart.setItemsQuantity(itemsQuantity);
cart.setItemsTotalPrice(itemsTotalPrice);
shoppingCartRepository.save(cart);
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
ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
            .stream()
            .filter(c -> c.getClosedAt() == null)
            .findFirst()
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Shopping cart not found for user " + userId));

    List<ShoppingCartItem> itemsToRemove = cart.getItems().stream()
            .filter(item -> itemIds.contains(item.getId()))
            .collect(java.util.stream.Collectors.toList());

    for (ShoppingCartItem item : itemsToRemove) {
        cart.getItems().remove(item);
        shoppingCartItemRepository.delete(item);
    }

    int itemsQuantity = cart.getItems().size();
    double itemsTotalPrice = 0.0;
    for (ShoppingCartItem item : cart.getItems()) {
        itemsTotalPrice += item.getProductQuantity() * 10.0;
    }
    cart.setItemsQuantity(itemsQuantity);
    cart.setItemsTotalPrice(itemsTotalPrice);
    shoppingCartRepository.save(cart);
// generated end
    }

}
