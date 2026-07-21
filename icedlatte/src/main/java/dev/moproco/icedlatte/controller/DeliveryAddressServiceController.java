package dev.moproco.icedlatte.controller;

import org.springframework.web.bind.annotation.*;
import dev.moproco.icedlatte.DeliveryAddressService;
import org.springframework.http.ResponseEntity;
import java.util.List;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import dev.moproco.icedlatte.dto.DeliveryAddressRequest;
import dev.moproco.icedlatte.dto.DeliveryAddressSnapshot;

@RestController
@RequestMapping("/api/v1/users/addresses")
@Tag(name = "/api/v1/users/addresses")
public class DeliveryAddressServiceController {

    private final DeliveryAddressService deliveryAddressService;

    public DeliveryAddressServiceController(DeliveryAddressService deliveryAddressService) {
        this.deliveryAddressService = deliveryAddressService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Delivery Addresses", description = "Retrieves all delivery addresses for the user by userId. Returns a list of DeliveryAddressSnapshot with id, label, line, city, country, postcode, isDefault.")
    public List<DeliveryAddressSnapshot> getDeliveryAddresses(@PathVariable Long userId) {
        return deliveryAddressService.getDeliveryAddresses(userId);
    }

    @PostMapping("/{id}")
    @Operation(summary = "Add Delivery Address", description = "Adds a new delivery address for the user by userId. If request.isDefault is true, sets all existing addresses for the user to isDefault=false first. Saves the new DeliveryAddressEntity.")
    public ResponseEntity<Void> addDeliveryAddress(@PathVariable Long userId, @RequestBody @Valid DeliveryAddressRequest request) {
        deliveryAddressService.addDeliveryAddress(userId, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Delivery Address", description = "Updates an existing delivery address by addressId. Updates label, line, city, country, postcode, isDefault. If isDefault is true, unsets other defaults for the same user.")
    public ResponseEntity<Void> updateDeliveryAddress(@PathVariable Long addressId, @RequestBody @Valid DeliveryAddressRequest request) {
        deliveryAddressService.updateDeliveryAddress(addressId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Delivery Address", description = "Deletes a delivery address by addressId. If the deleted address was the default, sets another address as default if any remain.")
    public ResponseEntity<Void> deleteDeliveryAddress(@PathVariable Long addressId) {
        deliveryAddressService.deleteDeliveryAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/setDefaultDeliveryAddress/{addressId}")
    @Operation(summary = "Set Default Delivery Address", description = "Sets the delivery address as default by addressId. Unsets isDefault on all other addresses for the same user.")
    public ResponseEntity<Void> setDefaultDeliveryAddress(@PathVariable Long addressId) {
        deliveryAddressService.setDefaultDeliveryAddress(addressId);
        return ResponseEntity.noContent().build();
    }

}
