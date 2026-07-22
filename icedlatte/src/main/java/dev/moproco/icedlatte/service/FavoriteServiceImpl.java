package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import dev.moproco.icedlatte.FavoriteService;
import dev.moproco.icedlatte.domain.FavoriteItemEntity;
import dev.moproco.icedlatte.repository.FavoriteItemEntityRepository;
import dev.moproco.icedlatte.domain.FavoriteListEntity;
import dev.moproco.icedlatte.repository.FavoriteListEntityRepository;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteItemEntityRepository favoriteItemEntityRepository;
    private final FavoriteListEntityRepository favoriteListEntityRepository;

    public FavoriteServiceImpl(FavoriteItemEntityRepository favoriteItemEntityRepository, FavoriteListEntityRepository favoriteListEntityRepository) {
        this.favoriteItemEntityRepository = favoriteItemEntityRepository;
        this.favoriteListEntityRepository = favoriteListEntityRepository;
    }

    /**
     * @prompt Retrieves the user's list of favorite product IDs by userId. Returns a list of productId strings from FavoriteItemEntity. Returns empty list if no favorites exist.
     * @generated NOT
     */
    @Override
    @Transactional
    public List<String> getListOfFavoriteProducts(Long userId) {
        // generated start
// Find the favorite list for the user
    java.util.List<FavoriteListEntity> favoriteList = favoriteListEntityRepository.findByUserId(userId);
    if (favoriteList.isEmpty()) {
        return java.util.Collections.emptyList();
    }    
    // Extract product IDs from items and convert to String
    return favoriteList.stream()
            .flatMap(list -> list.getItems().stream())
            .map(item -> item.getProductId().toString())
            .collect(java.util.stream.Collectors.toList());
// generated end
    }

    /**
     * @prompt Adds products to the user's favorites list by userId. Creates a FavoriteListEntity if it doesn't exist for the user. Creates a FavoriteItemEntity for each productId that isn't already in the list. Updates updatedAt.
     * @generated NOT
     */
    @Override
    @Transactional
    public void addListOfFavoriteProducts(Long userId, List<Long> productIds) {
        // generated start
// Find or create the favorite list for the user
    java.util.Optional<FavoriteListEntity> favoriteListOpt = favoriteListEntityRepository.findByUserId(userId).stream().findFirst();
    FavoriteListEntity favoriteList;
    if (favoriteListOpt.isEmpty()) {
        favoriteList = new FavoriteListEntity();
        favoriteList.setUserId(userId);
        favoriteList.setUpdatedAt(java.time.LocalDateTime.now());
        favoriteList.setItems(new java.util.ArrayList<>());
        favoriteList = favoriteListEntityRepository.save(favoriteList);
    } else {
        favoriteList = favoriteListOpt.get();
    }

    // Get existing product IDs in the list
    java.util.Set<Long> existingProductIds = favoriteList.getItems().stream()
            .map(FavoriteItemEntity::getProductId)
            .collect(java.util.stream.Collectors.toSet());

    // Create FavoriteItemEntity for each productId not already in the list
    for (Long productId : productIds) {
        if (!existingProductIds.contains(productId)) {
            FavoriteItemEntity item = new FavoriteItemEntity();
            item.setProductId(productId);
            item.setVersion(1);
            item.setFavoriteList(favoriteList);
            favoriteItemEntityRepository.save(item);
            favoriteList.getItems().add(item);
        }
    }

    // Update updatedAt
    favoriteList.setUpdatedAt(java.time.LocalDateTime.now());
    favoriteListEntityRepository.save(favoriteList);
// generated end
    }

    /**
     * @prompt Removes a product from the user's favorites list by userId and productId. Deletes the FavoriteItemEntity. Updates updatedAt on the FavoriteListEntity.
     * @generated NOT
     */
    @Override
    @Transactional
    public void removeProductFromFavorite(Long userId, Long productId) {
        // generated start
// Find the favorite list for the user
    java.util.Optional<FavoriteListEntity> favoriteListOpt = favoriteListEntityRepository.findByUserId(userId).stream().findFirst();
    if (favoriteListOpt.isEmpty()) {
        throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Favorite list not found for user");
    }
    FavoriteListEntity favoriteList = favoriteListOpt.get();

    // Find the FavoriteItemEntity to remove
    java.util.Optional<FavoriteItemEntity> itemToRemove = favoriteList.getItems().stream()
            .filter(item -> item.getProductId().equals(productId))
            .findFirst();

    if (itemToRemove.isEmpty()) {
        throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Product not found in favorites");
    }

    // Remove from the list and delete from repository
    favoriteList.getItems().remove(itemToRemove.get());
    favoriteItemEntityRepository.delete(itemToRemove.get());

    // Update updatedAt
    favoriteList.setUpdatedAt(java.time.LocalDateTime.now());
    favoriteListEntityRepository.save(favoriteList);
// generated end
    }

}
