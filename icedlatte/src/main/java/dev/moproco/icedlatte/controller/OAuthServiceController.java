package dev.moproco.icedlatte.controller;

import org.springframework.web.bind.annotation.*;
import dev.moproco.icedlatte.OAuthService;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import dev.moproco.icedlatte.domain.OAuthProvider;
import dev.moproco.icedlatte.dto.AuthResponse;
import dev.moproco.icedlatte.dto.OAuthCallbackRequest;

@RestController
@RequestMapping("/api/v1/auth/oauth")
@Tag(name = "/api/v1/auth/oauth")
public class OAuthServiceController {

    private final OAuthService oAuthService;

    public OAuthServiceController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Initiate O Auth", description = "Initiates the OAuth2 login flow for the specified provider. Builds the authorization URL with the provider's client ID, redirect URI, and scopes. Returns a redirect to the provider's authorization page.")
    public ResponseEntity<Void> initiateOAuth(@PathVariable OAuthProvider provider) {
        oAuthService.initiateOAuth(provider);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(summary = "Complete O Auth Callback", description = "Completes the OAuth2 login flow after provider callback. The 'request' parameter contains code and state. Exchanges the authorization code for an access token with the provider. Looks up the OAuthIdentityEntity by providerSubject. If it exists, loads the UserEntity. If not, creates a new UserEntity with oauthUser=true and a new OAuthIdentityEntity. Creates an AuthSessionEntity and returns an AuthResponse with JWT tokens.")
    public ResponseEntity<AuthResponse> completeOAuthCallback(@RequestBody @Valid OAuthCallbackRequest request) {
        return ResponseEntity.ok(oAuthService.completeOAuthCallback(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Complete O Auth Token Handoff", description = "Completes OAuth2 authentication via token handoff. Validates the provided token with the provider. Looks up or creates the OAuthIdentityEntity and UserEntity. Creates an AuthSessionEntity and returns an AuthResponse with JWT tokens.")
    public ResponseEntity<AuthResponse> completeOAuthTokenHandoff(@PathVariable String token) {
        return ResponseEntity.ok(oAuthService.completeOAuthTokenHandoff(token));
    }

}
