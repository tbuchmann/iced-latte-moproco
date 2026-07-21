package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import dev.moproco.icedlatte.DeliveryAddressService;
import dev.moproco.icedlatte.domain.Address;
import dev.moproco.icedlatte.repository.AddressRepository;
import dev.moproco.icedlatte.domain.DeliveryAddressEntity;
import dev.moproco.icedlatte.repository.DeliveryAddressEntityRepository;
import dev.moproco.icedlatte.dto.DeliveryAddressRequest;
import dev.moproco.icedlatte.dto.DeliveryAddressSnapshot;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    private final AddressRepository addressRepository;
    private final DeliveryAddressEntityRepository deliveryAddressEntityRepository;

    public DeliveryAddressServiceImpl(AddressRepository addressRepository, DeliveryAddressEntityRepository deliveryAddressEntityRepository) {
        this.addressRepository = addressRepository;
        this.deliveryAddressEntityRepository = deliveryAddressEntityRepository;
    }

    /**
     * @prompt Retrieves all delivery addresses for the user by userId. Returns a list of DeliveryAddressSnapshot with id, label, line, city, country, postcode, isDefault.
     * @generated NOT
     */
    @Override
    @Transactional
    public List<DeliveryAddressSnapshot> getDeliveryAddresses(Long userId) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
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
        throw new UnsupportedOperationException("Not yet implemented");
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
        throw new UnsupportedOperationException("Not yet implemented");
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
        throw new UnsupportedOperationException("Not yet implemented");
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
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

}
