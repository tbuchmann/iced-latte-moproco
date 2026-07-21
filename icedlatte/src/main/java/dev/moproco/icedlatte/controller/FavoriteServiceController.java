package dev.moproco.icedlatte.controller;

import org.springframework.web.bind.annotation.*;
import dev.moproco.icedlatte.FavoriteService;
import org.springframework.http.ResponseEntity;
import java.util.List;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/favorites")
@Tag(name = "/api/v1/favorites")
public class FavoriteServiceController {

    private final FavoriteService favoriteService;

    public FavoriteServiceController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get List Of Favorite Products", description = "Retrieves the user's list of favorite product IDs by userId. Returns a list of productId strings from FavoriteItemEntity. Returns empty list if no favorites exist.")
    public List<String> getListOfFavoriteProducts(@PathVariable Long userId) {
        return favoriteService.getListOfFavoriteProducts(userId);
    }

    @PostMapping("/{id}")
    @Operation(summary = "Add List Of Favorite Products", description = "Adds products to the user's favorites list by userId. Creates a FavoriteListEntity if it doesn't exist for the user. Creates a FavoriteItemEntity for each productId that isn't already in the list. Updates updatedAt.")
    public ResponseEntity<Void> addListOfFavoriteProducts(@PathVariable Long userId, @RequestBody List<Long> productIds) {
        favoriteService.addListOfFavoriteProducts(userId, productIds);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/removeProductFromFavorite")
    @Operation(summary = "Remove Product From Favorite")
    public void removeProductFromFavorite(Long userId, Long productId) {
        favoriteService.removeProductFromFavorite(userId, productId);
    }

}
