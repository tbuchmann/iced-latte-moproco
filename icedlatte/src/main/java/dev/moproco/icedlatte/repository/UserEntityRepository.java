package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.UserEntity;
import java.time.LocalDate;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    java.util.Optional<UserEntity> findByFirstName(String firstName);
    java.util.Optional<UserEntity> findByLastName(String lastName);
    java.util.Optional<UserEntity> findByBirthDate(LocalDate birthDate);
    java.util.Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    java.util.Optional<UserEntity> findByEmail(String email);
    java.util.Optional<UserEntity> findByPassword(String password);
    java.util.Optional<UserEntity> findByStripeCustomerToken(String stripeCustomerToken);
    java.util.List<UserEntity> findByAccountNonExpiredTrue();
    java.util.List<UserEntity> findByAccountNonExpiredFalse();
    java.util.List<UserEntity> findByAccountNonLockedTrue();
    java.util.List<UserEntity> findByAccountNonLockedFalse();
    java.util.List<UserEntity> findByCredentialsNonExpiredTrue();
    java.util.List<UserEntity> findByCredentialsNonExpiredFalse();
    java.util.List<UserEntity> findByEnabledTrue();
    java.util.List<UserEntity> findByEnabledFalse();
    java.util.List<UserEntity> findByOauthUserTrue();
    java.util.List<UserEntity> findByOauthUserFalse();

}
