package dev.moproco.icedlatte.controller;

import org.springframework.web.bind.annotation.*;
import dev.moproco.icedlatte.AuthenticationService;
import org.springframework.http.ResponseEntity;
import java.util.List;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import dev.moproco.icedlatte.dto.AuthResponse;
import dev.moproco.icedlatte.dto.AuthSessionSnapshot;
import dev.moproco.icedlatte.dto.LoginRequest;
import dev.moproco.icedlatte.dto.RegisterRequest;
import dev.moproco.icedlatte.dto.ResetPasswordRequest;
import dev.moproco.icedlatte.dto.RevokeSessionRequest;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "/api/v1/auth")
public class AuthenticationServiceController {

    private final AuthenticationService authenticationService;

    public AuthenticationServiceController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    @Operation(summary = "Register", description = "Registers a new user account. The 'request' parameter contains email, password, firstName, lastName. Creates a UserEntity with email=request.email, password (BCrypt-hashed), firstName=request.firstName, lastName=request.lastName, enabled=false, oauthUser=false. Creates a UserGrantedAuthority with authority=USER. Creates an AuthSessionEntity and generates JWT access and refresh tokens. Returns an AuthResponse with accessToken, refreshToken, userId.")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Confirm Email", description = "Confirms a user's email address using a verification token. The 'token' parameter is the verification token string. Looks up an EmailTokenEntity by token with purpose=EMAIL_VERIFICATION that is not yet used and not expired. Sets the UserEntity.enabled=true. Marks the EmailTokenEntity.usedAt=now. Throws NotFoundException if token not found or already used.")
    public ResponseEntity<Void> confirmEmail(@PathVariable String token) {
        authenticationService.confirmEmail(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/authenticate/{request}")
    @Operation(summary = "Authenticate", description = "Authenticates a user with email and password. The 'request' parameter contains email and password. Verifies the password against the stored BCrypt hash. Checks that enabled=true and accountNonLocked=true. Creates an AuthSessionEntity with refreshTokenHash, userAgent, ipAddress. Returns an AuthResponse with accessToken, refreshToken, userId. Throws BadCredentialsException on invalid credentials.")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Refresh Token", description = "Refreshes an expired access token using a refresh token. The 'refreshToken' parameter is the refresh token string. Looks up the AuthSessionEntity by refreshTokenHash. Checks that it's not revoked or expired. Generates a new access token. Updates lastUsedAt. Returns an AuthResponse with new accessToken, same refreshToken, userId.")
    public ResponseEntity<AuthResponse> refreshToken(@PathVariable String refreshToken) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }

    @PutMapping("/logout/{refreshToken}")
    @Operation(summary = "Logout", description = "Revokes the current refresh token. The 'refreshToken' parameter is the refresh token string. Looks up the AuthSessionEntity by refreshTokenHash and sets revokedAt=now. Throws NotFoundException if not found. Does not return anything (void).")
    public ResponseEntity<Void> logout(@PathVariable String refreshToken) {
        authenticationService.logout(refreshToken);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/logoutAll/{userId}")
    @Operation(summary = "Logout All", description = "Revokes all active sessions for the user by userId. The 'userId' parameter is the user's ID. Sets revokedAt=now on all AuthSessionEntity where userId matches and revokedAt is null. Does not return anything (void).")
    public ResponseEntity<Void> logoutAll(@PathVariable Long userId) {
        authenticationService.logoutAll(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getSessions/{userId}")
    @Operation(summary = "Get Sessions", description = "Retrieves all active (non-revoked) sessions for the user by userId. The 'userId' parameter is the user's ID. Returns a list of AuthSessionSnapshot with id, userAgent, ipAddress, createdAt, lastUsedAt, expiresAt.")
    public List<AuthSessionSnapshot> getSessions(@PathVariable Long userId) {
        return authenticationService.getSessions(userId);
    }

    @PutMapping("/revokeSession/{userId}")
    @Operation(summary = "Revoke Session", description = "Revokes a specific session. The 'userId' parameter is the user's ID, 'request' contains sessionId. Verifies that the session belongs to the given userId. Sets revokedAt=now. Does not return anything (void).")
    public ResponseEntity<Void> revokeSession(@PathVariable Long userId, @RequestBody @Valid RevokeSessionRequest request) {
        authenticationService.revokeSession(userId, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/forgotPassword/{email}")
    @Operation(summary = "Forgot Password", description = "Sends a password reset email to the user. The 'email' parameter is the user's email. Looks up UserEntity by email. Creates an EmailTokenEntity with purpose=PASSWORD_RESET and a random token. (Email sending is out of scope - just create the token.) Throws NotFoundException if email not found. Does not return anything (void).")
    public ResponseEntity<Void> forgotPassword(@PathVariable String email) {
        authenticationService.forgotPassword(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/changePassword/{request}")
    @Operation(summary = "Change Password", description = "Changes the user's password using a reset token. The 'request' parameter contains resetToken and newPassword. Looks up an EmailTokenEntity by token with purpose=PASSWORD_RESET that is not yet used and not expired. Updates the UserEntity.password with BCrypt-hashed newPassword. Marks the EmailTokenEntity.usedAt=now. Does not return anything (void).")
    public ResponseEntity<Void> changePassword(@PathVariable ResetPasswordRequest request) {
        authenticationService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

}
