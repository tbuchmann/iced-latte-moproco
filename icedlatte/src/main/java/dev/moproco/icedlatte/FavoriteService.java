package dev.moproco.icedlatte;

import java.util.List;

public interface FavoriteService {

    List<String> getListOfFavoriteProducts(Long userId);
    void addListOfFavoriteProducts(Long userId, List<Long> productIds);
    void removeProductFromFavorite(Long userId, Long productId);

}
