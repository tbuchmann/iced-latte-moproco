package dev.moproco.icedlatte;

import java.util.List;
import dev.moproco.icedlatte.dto.DeliveryAddressRequest;
import dev.moproco.icedlatte.dto.DeliveryAddressSnapshot;

public interface DeliveryAddressService {

    List<DeliveryAddressSnapshot> getDeliveryAddresses(Long userId);
    void addDeliveryAddress(Long userId, DeliveryAddressRequest request);
    void updateDeliveryAddress(Long addressId, DeliveryAddressRequest request);
    void deleteDeliveryAddress(Long addressId);
    void setDefaultDeliveryAddress(Long addressId);

}
