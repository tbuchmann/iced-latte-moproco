package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import dev.moproco.icedlatte.ProductCatalogService;
import dev.moproco.icedlatte.domain.ProductInfo;
import dev.moproco.icedlatte.repository.ProductInfoRepository;
import dev.moproco.icedlatte.dto.ProductSnapshot;

@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final ProductInfoRepository productInfoRepository;

    public ProductCatalogServiceImpl(ProductInfoRepository productInfoRepository) {
        this.productInfoRepository = productInfoRepository;
    }

    /**
     * @prompt Retrieves a paginated list of products (PageRequest of size 'size' starting at 'page'). Returns a list of ProductSnapshot. Only returns products where active=true. Sorted by dateAdded descending.
     * @generated NOT
     */
    @Override
    @Transactional
    public List<ProductSnapshot> getProducts(Integer page, Integer size) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

    /**
     * @prompt Retrieves a list of all distinct sellerName values from ProductInfo where active=true.
     * @generated NOT
     */
    @Override
    @Transactional
    public List<String> getAllSellers() {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

    /**
     * @prompt Retrieves a list of all distinct brandName values from ProductInfo where active=true.
     * @generated NOT
     */
    @Override
    @Transactional
    public List<String> getAllBrands() {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

    /**
     * @prompt Retrieves products by a list of product IDs. Returns a list of ProductSnapshot for each ID found. Ignores IDs that don't exist.
     * @generated NOT
     */
    @Override
    @Transactional
    public List<ProductSnapshot> getProductsByIds(List<Long> productIds) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

    /**
     * @prompt Retrieves a single product by its ID. Returns a ProductSnapshot. Throws NotFoundException if product not found.
     * @generated NOT
     */
    @Override
    @Transactional
    public ProductSnapshot getProductById(Long productId) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

}
