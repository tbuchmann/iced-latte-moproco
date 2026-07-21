package dev.moproco.icedlatte;

import java.util.List;
import dev.moproco.icedlatte.dto.ProductSnapshot;

public interface ProductCatalogService {

    List<ProductSnapshot> getProducts(Integer page, Integer size);
    List<String> getAllSellers();
    List<String> getAllBrands();
    List<ProductSnapshot> getProductsByIds(List<Long> productIds);
    ProductSnapshot getProductById(Long productId);

}
