package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import dev.moproco.icedlatte.DeliveryAddressService;
import dev.moproco.icedlatte.domain.Address;
import dev.moproco.icedlatte.repository.AddressRepository;
import dev.moproco.icedlatte.domain.DeliveryAddressEntity;
import dev.moproco.icedlatte.domain.UserEntity;
import dev.moproco.icedlatte.repository.DeliveryAddressEntityRepository;
import dev.moproco.icedlatte.repository.UserEntityRepository;
import dev.moproco.icedlatte.dto.DeliveryAddressRequest;
import dev.moproco.icedlatte.dto.DeliveryAddressSnapshot;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    private final AddressRepository addressRepository;
    private final DeliveryAddressEntityRepository deliveryAddressEntityRepository;
    private final UserEntityRepository userEntityRepository;

    public DeliveryAddressServiceImpl(AddressRepository addressRepository, DeliveryAddressEntityRepository deliveryAddressEntityRepository, UserEntityRepository userEntityRepository) {
        this.addressRepository = addressRepository;
        this.deliveryAddressEntityRepository = deliveryAddressEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }

    /**
     * @prompt Retrieves all delivery addresses for the user by userId. Returns a list of DeliveryAddressSnapshot with id, label, line, city, country, postcode, isDefault.
     * @generated NOT
     */
    @Override
    @Transactional
    public List<DeliveryAddressSnapshot> getDeliveryAddresses(Long userId) {
        // generated start
        UserEntity user = userEntityRepository.findById(userId).get();
        return deliveryAddressEntityRepository.findByUser(user).stream()
            .map(entity -> new DeliveryAddressSnapshot(entity.getLabel(), entity.getLine(), entity.getCity(), entity.getCountry(), entity.getPostcode(), entity.getIsDefault()))
            .collect(java.util.stream.Collectors.toList());
// generated end
    }

    /**
     * @prompt Adds a new delivery address for the user by userId. If request.isDefault is true, sets all existing addresses for the user to isDefault=false first. Saves the new DeliveryAddressEntity.
     * @generated NOT
     */
    @Override
    @Transactional
    public void addDeliveryAddress(Long userId, DeliveryAddressRequest request) {
        // generated start
if (request.isDefault() != null && request.isDefault()) {
        List<DeliveryAddressEntity> existingDefaults = deliveryAddressEntityRepository.findByIsDefaultTrue();
        for (DeliveryAddressEntity addr : existingDefaults) {
            if (addr.getUser() != null && addr.getUser().getId().equals(userId)) {
                addr.setIsDefault(false);
                deliveryAddressEntityRepository.save(addr);
            }
        }
    }
    DeliveryAddressEntity entity = new DeliveryAddressEntity();
    entity.setLabel(request.label());
    entity.setLine(request.line());
    entity.setCity(request.city());
    entity.setCountry(request.country());
    entity.setPostcode(request.postcode());
    entity.setIsDefault(request.isDefault() != null && request.isDefault());
    deliveryAddressEntityRepository.save(entity);
// generated end
    }

    /**
     * @prompt Updates an existing delivery address by addressId. Updates label, line, city, country, postcode, isDefault. If isDefault is true, unsets other defaults for the same user.
     * @generated NOT
     */
    @Override
    @Transactional
    public void updateDeliveryAddress(Long addressId, DeliveryAddressRequest request) {
        // generated start
DeliveryAddressEntity entity = deliveryAddressEntityRepository.findById(addressId)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Delivery address not found"));
    entity.setLabel(request.label());
    entity.setLine(request.line());
    entity.setCity(request.city());
    entity.setCountry(request.country());
    entity.setPostcode(request.postcode());
    if (request.isDefault() != null && request.isDefault()) {
        List<DeliveryAddressEntity> existingDefaults = deliveryAddressEntityRepository.findByIsDefaultTrue();
        for (DeliveryAddressEntity addr : existingDefaults) {
            if (!addr.getId().equals(addressId) && addr.getUser() != null && addr.getUser().getId().equals(entity.getUser().getId())) {
                addr.setIsDefault(false);
                deliveryAddressEntityRepository.save(addr);
            }
        }
        entity.setIsDefault(true);
    } else {
        entity.setIsDefault(request.isDefault() != null && request.isDefault());
    }
    deliveryAddressEntityRepository.save(entity);
// generated end
    }

    /**
     * @prompt Deletes a delivery address by addressId. If the deleted address was the default, sets another address as default if any remain.
     * @generated NOT
     */
    @Override
    @Transactional
    public void deleteDeliveryAddress(Long addressId) {
        // generated start
DeliveryAddressEntity entity = deliveryAddressEntityRepository.findById(addressId)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Delivery address not found"));
    boolean wasDefault = entity.getIsDefault() != null && entity.getIsDefault();
    deliveryAddressEntityRepository.delete(entity);
    if (wasDefault) {
        List<DeliveryAddressEntity> remaining = deliveryAddressEntityRepository.findByIsDefaultTrue();
        if (remaining.isEmpty()) {
            List<DeliveryAddressEntity> allForUser = deliveryAddressEntityRepository.findByIsDefaultFalse();
            if (!allForUser.isEmpty()) {
                DeliveryAddressEntity newDefault = allForUser.get(0);
                newDefault.setIsDefault(true);
                deliveryAddressEntityRepository.save(newDefault);
            }
        }
    }
// generated end
    }

    /**
     * @prompt Sets the delivery address as default by addressId. Unsets isDefault on all other addresses for the same user.
     * @generated NOT
     */
    @Override
    @Transactional
    public void setDefaultDeliveryAddress(Long addressId) {
        // generated start
DeliveryAddressEntity entity = deliveryAddressEntityRepository.findById(addressId)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Delivery address not found"));
    List<DeliveryAddressEntity> allForUser = deliveryAddressEntityRepository.findByIsDefaultTrue();
    for (DeliveryAddressEntity addr : allForUser) {
        if (!addr.getId().equals(addressId) && addr.getUser() != null && addr.getUser().getId().equals(entity.getUser().getId())) {
            addr.setIsDefault(false);
            deliveryAddressEntityRepository.save(addr);
        }
    }
    entity.setIsDefault(true);
    deliveryAddressEntityRepository.save(entity);
// generated end
    }

}
