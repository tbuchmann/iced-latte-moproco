package dev.moproco.icedlatte;

import dev.moproco.icedlatte.domain.OAuthProvider;
import dev.moproco.icedlatte.dto.AuthResponse;
import dev.moproco.icedlatte.dto.OAuthCallbackRequest;

public interface OAuthService {

    void initiateOAuth(OAuthProvider provider);
    AuthResponse completeOAuthCallback(OAuthCallbackRequest request);
    AuthResponse completeOAuthTokenHandoff(String token);

}
