package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.UserGrantedAuthority;
import dev.moproco.icedlatte.domain.Authority;

public interface UserGrantedAuthorityRepository extends JpaRepository<UserGrantedAuthority, Long> {
    java.util.Optional<UserGrantedAuthority> findByAuthority(Authority authority);

}
