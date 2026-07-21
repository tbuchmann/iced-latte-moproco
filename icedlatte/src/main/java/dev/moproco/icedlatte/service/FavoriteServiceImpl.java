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
        throw new UnsupportedOperationException("Not yet implemented");
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
        throw new UnsupportedOperationException("Not yet implemented");
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
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

}
