package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.Address;


public interface AddressRepository extends JpaRepository<Address, Long> {
    java.util.Optional<Address> findByCountry(String country);
    java.util.Optional<Address> findByCity(String city);
    java.util.Optional<Address> findByLine(String line);
    java.util.Optional<Address> findByPostcode(String postcode);

}
