package dev.moproco.icedlatte.controller;

import org.springframework.web.bind.annotation.*;
import dev.moproco.icedlatte.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import java.util.List;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import dev.moproco.icedlatte.dto.AddCartItemRequest;
import dev.moproco.icedlatte.dto.CartSnapshot;
import dev.moproco.icedlatte.dto.UpdateCartItemRequest;

@RestController
@RequestMapping("/api/v1/cart")
@Tag(name = "/api/v1/cart")
public class ShoppingCartServiceController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartServiceController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Shopping Cart", description = "Retrieves the user's shopping cart by userId. Returns a CartSnapshot with id, userId, itemsQuantity, itemsTotalPrice, productsQuantity, and a list of CartItemSnapshot. If no cart exists, creates a new one.")
    public ResponseEntity<CartSnapshot> getShoppingCart(@PathVariable Long userId) {
        return ResponseEntity.ok(shoppingCartService.getShoppingCart(userId));
    }

    @PostMapping("/{id}")
    @Operation(summary = "Add New Item To Shopping Cart", description = "Adds an item to the user's cart by userId. If a ShoppingCartItem with the same productId already exists, increments productQuantity by request.productQuantity. Otherwise creates a new ShoppingCartItem. Recalculates itemsQuantity and itemsTotalPrice.")
    public ResponseEntity<Void> addNewItemToShoppingCart(@PathVariable Long userId, @RequestBody @Valid AddCartItemRequest request) {
        shoppingCartService.addNewItemToShoppingCart(userId, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Product Quantity In Shopping Cart Item", description = "Updates the quantity of a cart item. The 'userId' parameter is the user's ID, 'request' contains itemId and quantity. If quantity is 0, removes the item. Recalculates itemsQuantity and itemsTotalPrice for the cart.")
    public ResponseEntity<Void> updateProductQuantityInShoppingCartItem(@PathVariable Long userId, @RequestBody @Valid UpdateCartItemRequest request) {
        shoppingCartService.updateProductQuantityInShoppingCartItem(userId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Items From Shopping Cart", description = "Removes the specified items by their IDs from the user's cart. Recalculates itemsQuantity and itemsTotalPrice.")
    public ResponseEntity<Void> deleteItemsFromShoppingCart(@PathVariable Long userId, @RequestBody List<Long> itemIds) {
        shoppingCartService.deleteItemsFromShoppingCart(userId, itemIds);
        return ResponseEntity.noContent().build();
    }

}
