package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.OrderAddress;


public interface OrderAddressRepository extends JpaRepository<OrderAddress, Long> {
    java.util.Optional<OrderAddress> findByCountry(String country);
    java.util.Optional<OrderAddress> findByCity(String city);
    java.util.Optional<OrderAddress> findByLine(String line);
    java.util.Optional<OrderAddress> findByPostcode(String postcode);

}
