package dev.moproco.icedlatte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.moproco.icedlatte.domain.OAuthIdentityEntity;
import dev.moproco.icedlatte.domain.OAuthProvider;

public interface OAuthIdentityEntityRepository extends JpaRepository<OAuthIdentityEntity, Long> {
    java.util.List<OAuthIdentityEntity> findByUserId(Long userId);
    java.util.Optional<OAuthIdentityEntity> findByProvider(OAuthProvider provider);
    java.util.Optional<OAuthIdentityEntity> findByProviderSubject(String providerSubject);
    java.util.Optional<OAuthIdentityEntity> findByEmail(String email);

}
