package dev.moproco.icedlatte.controller;

import org.springframework.web.bind.annotation.*;
import dev.moproco.icedlatte.ProductCatalogService;
import org.springframework.http.ResponseEntity;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import dev.moproco.icedlatte.dto.ProductSnapshot;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "/api/v1/products")
public class ProductCatalogServiceController {

    private final ProductCatalogService productCatalogService;

    public ProductCatalogServiceController(ProductCatalogService productCatalogService) {
        this.productCatalogService = productCatalogService;
    }

    @GetMapping("/getProducts")
    @Operation(summary = "Get Products")
    public List<ProductSnapshot> getProducts(Integer page, Integer size) {
        return productCatalogService.getProducts(page, size);
    }

    @GetMapping
    @Operation(summary = "Get All Sellers", description = "Retrieves a list of all distinct sellerName values from ProductInfo where active=true.")
    public List<String> getAllSellers() {
        return productCatalogService.getAllSellers();
    }

    @GetMapping("/getAllBrands")
    @Operation(summary = "Get All Brands", description = "Retrieves a list of all distinct brandName values from ProductInfo where active=true.")
    public List<String> getAllBrands() {
        return productCatalogService.getAllBrands();
    }

    @GetMapping("/getProductsByIds")
    @Operation(summary = "Get Products By Ids")
    public List<ProductSnapshot> getProductsByIds(List<Long> productIds) {
        return productCatalogService.getProductsByIds(productIds);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Product By Id", description = "Retrieves a single product by its ID. Returns a ProductSnapshot. Throws NotFoundException if product not found.")
    public ResponseEntity<ProductSnapshot> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productCatalogService.getProductById(productId));
    }

}
