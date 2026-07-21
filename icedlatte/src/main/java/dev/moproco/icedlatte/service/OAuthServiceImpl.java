package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.moproco.icedlatte.OAuthService;
import dev.moproco.icedlatte.domain.UserGrantedAuthority;
import dev.moproco.icedlatte.repository.UserGrantedAuthorityRepository;
import dev.moproco.icedlatte.domain.AuthSessionEntity;
import dev.moproco.icedlatte.repository.AuthSessionEntityRepository;
import dev.moproco.icedlatte.domain.OAuthIdentityEntity;
import dev.moproco.icedlatte.repository.OAuthIdentityEntityRepository;
import dev.moproco.icedlatte.domain.UserEntity;
import dev.moproco.icedlatte.repository.UserEntityRepository;
import dev.moproco.icedlatte.domain.OAuthProvider;
import dev.moproco.icedlatte.dto.AuthResponse;
import dev.moproco.icedlatte.dto.OAuthCallbackRequest;

@Service
public class OAuthServiceImpl implements OAuthService {

    private final UserGrantedAuthorityRepository userGrantedAuthorityRepository;
    private final AuthSessionEntityRepository authSessionEntityRepository;
    private final OAuthIdentityEntityRepository oAuthIdentityEntityRepository;
    private final UserEntityRepository userEntityRepository;

    public OAuthServiceImpl(UserGrantedAuthorityRepository userGrantedAuthorityRepository, AuthSessionEntityRepository authSessionEntityRepository, OAuthIdentityEntityRepository oAuthIdentityEntityRepository, UserEntityRepository userEntityRepository) {
        this.userGrantedAuthorityRepository = userGrantedAuthorityRepository;
        this.authSessionEntityRepository = authSessionEntityRepository;
        this.oAuthIdentityEntityRepository = oAuthIdentityEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }

    /**
     * @prompt Initiates the OAuth2 login flow for the specified provider. Builds the authorization URL with the provider's client ID, redirect URI, and scopes. Returns a redirect to the provider's authorization page.
     * @generated NOT
     */
    @Override
    @Transactional
    public void initiateOAuth(OAuthProvider provider) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

    /**
     * @prompt Completes the OAuth2 login flow after provider callback. The 'request' parameter contains code and state. Exchanges the authorization code for an access token with the provider. Looks up the OAuthIdentityEntity by providerSubject. If it exists, loads the UserEntity. If not, creates a new UserEntity with oauthUser=true and a new OAuthIdentityEntity. Creates an AuthSessionEntity and returns an AuthResponse with JWT tokens.
     * @generated NOT
     */
    @Override
    @Transactional
    public AuthResponse completeOAuthCallback(OAuthCallbackRequest request) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

    /**
     * @prompt Completes OAuth2 authentication via token handoff. Validates the provided token with the provider. Looks up or creates the OAuthIdentityEntity and UserEntity. Creates an AuthSessionEntity and returns an AuthResponse with JWT tokens.
     * @generated NOT
     */
    @Override
    @Transactional
    public AuthResponse completeOAuthTokenHandoff(String token) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

}
