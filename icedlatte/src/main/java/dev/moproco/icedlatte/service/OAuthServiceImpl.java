package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.moproco.icedlatte.OAuthService;
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
// Build the authorization URL for the given provider
    // Using standard OAuth2 authorization endpoint patterns
    String authorizationUrl;
    String clientId;
    String redirectUri = "http://localhost:8080/oauth/callback/" + provider.name().toLowerCase();
    String scopes;

    switch (provider) {
        case GOOGLE:
            clientId = System.getenv("GOOGLE_CLIENT_ID");
            scopes = "openid email profile";
            authorizationUrl = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code" +
                "&scope=" + scopes.replace(" ", "%20") +
                "&access_type=offline" +
                "&state=" + java.util.UUID.randomUUID().toString();
            break;
        case GITHUB:
            clientId = System.getenv("GITHUB_CLIENT_ID");
            scopes = "read:user user:email";
            authorizationUrl = "https://github.com/login/oauth/authorize" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=" + scopes.replace(" ", "%20") +
                "&state=" + java.util.UUID.randomUUID().toString();
            break;
        default:
            throw new IllegalArgumentException("Unsupported OAuth provider: " + provider);
    }

    // Redirect to the authorization URL by throwing a redirect response
    throw new org.springframework.web.server.ResponseStatusException(
        org.springframework.http.HttpStatus.FOUND,
        "Redirecting to " + authorizationUrl);
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
    java.util.Optional<OAuthIdentityEntity> identityOpt = oAuthIdentityEntityRepository.findByProviderSubject(request.code());
    UserEntity user;
    if (identityOpt.isPresent()) {
        user = userEntityRepository.findById(identityOpt.get().getUserId())
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "User not found"));
    } else {
        user = new UserEntity();
        user.setEmail(request.code() + "@oauth.placeholder");
        user.setPassword("");
        user.setFirstName("");
        user.setLastName("");
        user.setEnabled(true);
        user.setOauthUser(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user = userEntityRepository.save(user);
        OAuthIdentityEntity identity = new OAuthIdentityEntity();
        identity.setUserId(user.getId());
        identity.setProviderSubject(request.code());
        identity.setEmail(user.getEmail());
        oAuthIdentityEntityRepository.save(identity);
        dev.moproco.icedlatte.domain.UserGrantedAuthority grantedAuthority = new dev.moproco.icedlatte.domain.UserGrantedAuthority();
        grantedAuthority.setAuthority(dev.moproco.icedlatte.domain.Authority.USER);
        grantedAuthority.setUser(user);
        userGrantedAuthorityRepository.save(grantedAuthority);
    }
    String accessToken = java.util.UUID.randomUUID().toString();
    String refreshToken = java.util.UUID.randomUUID().toString();
    AuthSessionEntity session = new AuthSessionEntity();
    session.setUserId(user.getId());
    session.setRefreshTokenHash(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(refreshToken));
    session.setCreatedAt(java.time.LocalDateTime.now());
    session.setExpiresAt(java.time.LocalDateTime.now().plusDays(30));
    session.setLastUsedAt(java.time.LocalDateTime.now());
    session.setCompromised(false);
    authSessionEntityRepository.save(session);
    return new AuthResponse(accessToken, refreshToken, user.getId());
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
    java.util.Optional<OAuthIdentityEntity> identityOpt = oAuthIdentityEntityRepository.findByProviderSubject(token);
    UserEntity user;
    if (identityOpt.isPresent()) {
        user = userEntityRepository.findById(identityOpt.get().getUserId())
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "User not found"));
    } else {
        user = new UserEntity();
        user.setEmail(token + "@oauth.placeholder");
        user.setPassword("");
        user.setFirstName("");
        user.setLastName("");
        user.setEnabled(true);
        user.setOauthUser(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user = userEntityRepository.save(user);
        OAuthIdentityEntity identity = new OAuthIdentityEntity();
        identity.setUserId(user.getId());
        identity.setProviderSubject(token);
        identity.setEmail(user.getEmail());
        oAuthIdentityEntityRepository.save(identity);
        dev.moproco.icedlatte.domain.UserGrantedAuthority grantedAuthority = new dev.moproco.icedlatte.domain.UserGrantedAuthority();
        grantedAuthority.setAuthority(dev.moproco.icedlatte.domain.Authority.USER);
        grantedAuthority.setUser(user);
        userGrantedAuthorityRepository.save(grantedAuthority);
    }
    String accessToken = java.util.UUID.randomUUID().toString();
    String refreshToken = java.util.UUID.randomUUID().toString();
    AuthSessionEntity session = new AuthSessionEntity();
    session.setUserId(user.getId());
    session.setRefreshTokenHash(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(refreshToken));
    session.setCreatedAt(java.time.LocalDateTime.now());
    session.setExpiresAt(java.time.LocalDateTime.now().plusDays(30));
    session.setLastUsedAt(java.time.LocalDateTime.now());
    session.setCompromised(false);
    authSessionEntityRepository.save(session);
    return new AuthResponse(accessToken, refreshToken, user.getId());
// generated end
    }

}
