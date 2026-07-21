package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.DeliveryAddressEntity;


public interface DeliveryAddressEntityRepository extends JpaRepository<DeliveryAddressEntity, Long> {
    java.util.Optional<DeliveryAddressEntity> findByLabel(String label);
    java.util.Optional<DeliveryAddressEntity> findByLine(String line);
    java.util.Optional<DeliveryAddressEntity> findByCity(String city);
    java.util.Optional<DeliveryAddressEntity> findByCountry(String country);
    java.util.Optional<DeliveryAddressEntity> findByPostcode(String postcode);
    java.util.List<DeliveryAddressEntity> findByIsDefaultTrue();
    java.util.List<DeliveryAddressEntity> findByIsDefaultFalse();

}
