package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import dev.moproco.icedlatte.AuthenticationService;
import dev.moproco.icedlatte.domain.AuthSessionEntity;
import dev.moproco.icedlatte.repository.AuthSessionEntityRepository;
import dev.moproco.icedlatte.domain.LoginAttemptEntity;
import dev.moproco.icedlatte.repository.LoginAttemptEntityRepository;
import dev.moproco.icedlatte.domain.UserEntity;
import dev.moproco.icedlatte.repository.UserEntityRepository;
import dev.moproco.icedlatte.domain.UserGrantedAuthority;
import dev.moproco.icedlatte.repository.UserGrantedAuthorityRepository;
import dev.moproco.icedlatte.domain.EmailTokenEntity;
import dev.moproco.icedlatte.repository.EmailTokenEntityRepository;
import dev.moproco.icedlatte.dto.AuthResponse;
import dev.moproco.icedlatte.dto.AuthSessionSnapshot;
import dev.moproco.icedlatte.dto.LoginRequest;
import dev.moproco.icedlatte.dto.RegisterRequest;
import dev.moproco.icedlatte.dto.ResetPasswordRequest;
import dev.moproco.icedlatte.dto.RevokeSessionRequest;
import dev.moproco.icedlatte.domain.Authority;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthSessionEntityRepository authSessionEntityRepository;
    private final LoginAttemptEntityRepository loginAttemptEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final UserGrantedAuthorityRepository userGrantedAuthorityRepository;
    private final EmailTokenEntityRepository emailTokenEntityRepository;

    public AuthenticationServiceImpl(AuthSessionEntityRepository authSessionEntityRepository, LoginAttemptEntityRepository loginAttemptEntityRepository, UserEntityRepository userEntityRepository, UserGrantedAuthorityRepository userGrantedAuthorityRepository, EmailTokenEntityRepository emailTokenEntityRepository) {
        this.authSessionEntityRepository = authSessionEntityRepository;
        this.loginAttemptEntityRepository = loginAttemptEntityRepository;
        this.userEntityRepository = userEntityRepository;
        this.userGrantedAuthorityRepository = userGrantedAuthorityRepository;
        this.emailTokenEntityRepository = emailTokenEntityRepository;
    }

    /**
     * @prompt Registers a new user account. The 'request' parameter contains email, password, firstName, lastName. Creates a UserEntity with email=request.email, password (BCrypt-hashed), firstName=request.firstName, lastName=request.lastName, enabled=false, oauthUser=false. Creates a UserGrantedAuthority with authority=USER. Creates an AuthSessionEntity and generates JWT access and refresh tokens. Returns an AuthResponse with accessToken, refreshToken, userId.
     * @generated NOT
     */
    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // generated start
UserEntity user = new UserEntity();
    user.setEmail(request.email());
    user.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(request.password()));
    user.setFirstName(request.firstName());
    user.setLastName(request.lastName());
    user.setEnabled(false);
    user.setOauthUser(false);
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setCredentialsNonExpired(true);
    user = userEntityRepository.save(user);

    UserGrantedAuthority authority = new UserGrantedAuthority();
    authority.setAuthority(Authority.USER);
    authority.setUser(user);
    userGrantedAuthorityRepository.save(authority);

    String accessToken = java.util.UUID.randomUUID().toString();
    String refreshToken = java.util.UUID.randomUUID().toString();
    String refreshTokenHash = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(refreshToken);

    AuthSessionEntity session = new AuthSessionEntity();
    session.setUserId(user.getId());
    session.setRefreshTokenHash(refreshTokenHash);
    session.setCreatedAt(java.time.LocalDateTime.now());
    session.setExpiresAt(java.time.LocalDateTime.now().plusDays(30));
    session.setLastUsedAt(java.time.LocalDateTime.now());
    session.setCompromised(false);
    authSessionEntityRepository.save(session);

    return new AuthResponse(accessToken, refreshToken, user.getId());
// generated end
    }

    /**
     * @prompt Confirms a user's email address using a verification token. The 'token' parameter is the verification token string. Looks up an EmailTokenEntity by token with purpose=EMAIL_VERIFICATION that is not yet used and not expired. Sets the UserEntity.enabled=true. Marks the EmailTokenEntity.usedAt=now. Throws NotFoundException if token not found or already used.
     * @generated NOT
     */
    @Override
    @Transactional
    public void confirmEmail(String token) {
        // generated start
EmailTokenEntity emailToken = emailTokenEntityRepository.findByToken(token)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND, "Token not found"));
    if (emailToken.getPurpose() != dev.moproco.icedlatte.domain.TokenPurpose.EMAIL_VERIFICATION
        || emailToken.getUsedAt() != null
        || emailToken.getExpiresAt().isBefore(java.time.LocalDateTime.now())) {
        throw new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND, "Token not found or already used");
    }
    UserEntity user = userEntityRepository.findById(emailToken.getUserId())
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND, "User not found"));
    user.setEnabled(true);
    userEntityRepository.save(user);
    emailToken.setUsedAt(java.time.LocalDateTime.now());
    emailTokenEntityRepository.save(emailToken);
// generated end
    }

    /**
     * @prompt Authenticates a user with email and password. The 'request' parameter contains email and password. Verifies the password against the stored BCrypt hash. Checks that enabled=true and accountNonLocked=true. Creates an AuthSessionEntity with refreshTokenHash, userAgent, ipAddress. Returns an AuthResponse with accessToken, refreshToken, userId. Throws BadCredentialsException on invalid credentials.
     * @generated NOT
     */
    @Override
    @Transactional
    public AuthResponse authenticate(LoginRequest request) {
        // generated start
UserEntity user = userEntityRepository.findByEmail(request.email())
        .orElseThrow(() -> new org.springframework.security.authentication.BadCredentialsException("Invalid credentials"));
    if (!user.getEnabled() || !user.getAccountNonLocked()) {
        throw new org.springframework.security.authentication.BadCredentialsException("Invalid credentials");
    }
    if (!new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().matches(request.password(), user.getPassword())) {
        throw new org.springframework.security.authentication.BadCredentialsException("Invalid credentials");
    }
    String accessToken = java.util.UUID.randomUUID().toString();
    String refreshToken = java.util.UUID.randomUUID().toString();
    String refreshTokenHash = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(refreshToken);
    AuthSessionEntity session = new AuthSessionEntity();
    session.setUserId(user.getId());
    session.setRefreshTokenHash(refreshTokenHash);
    session.setCreatedAt(java.time.LocalDateTime.now());
    session.setExpiresAt(java.time.LocalDateTime.now().plusDays(30));
    session.setLastUsedAt(java.time.LocalDateTime.now());
    session.setCompromised(false);
    authSessionEntityRepository.save(session);
    return new AuthResponse(accessToken, refreshToken, user.getId());
// generated end
    }

    /**
     * @prompt Refreshes an expired access token using a refresh token. The 'refreshToken' parameter is the refresh token string. Looks up the AuthSessionEntity by refreshTokenHash. Checks that it's not revoked or expired. Generates a new access token. Updates lastUsedAt. Returns an AuthResponse with new accessToken, same refreshToken, userId.
     * @generated NOT
     */
    @Override
    @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        // generated start
String refreshTokenHash = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(refreshToken);
    AuthSessionEntity session = authSessionEntityRepository.findByRefreshTokenHash(refreshTokenHash)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND, "Session not found"));
    if (session.getRevokedAt() != null || session.getExpiresAt().isBefore(java.time.LocalDateTime.now())) {
        throw new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.BAD_REQUEST, "Refresh token is revoked or expired");
    }
    String newAccessToken = java.util.UUID.randomUUID().toString();
    session.setLastUsedAt(java.time.LocalDateTime.now());
    authSessionEntityRepository.save(session);
    return new AuthResponse(newAccessToken, refreshToken, session.getUserId());
// generated end
    }

    /**
     * @prompt Revokes the current refresh token. The 'refreshToken' parameter is the refresh token string. Looks up the AuthSessionEntity by refreshTokenHash and sets revokedAt=now. Throws NotFoundException if not found. Does not return anything (void).
     * @generated NOT
     */
    @Override
    @Transactional
    public void logout(String refreshToken) {
        // generated start
String refreshTokenHash = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(refreshToken);
    AuthSessionEntity session = authSessionEntityRepository.findByRefreshTokenHash(refreshTokenHash)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND, "Session not found"));
    session.setRevokedAt(java.time.LocalDateTime.now());
    authSessionEntityRepository.save(session);
// generated end
    }

    /**
     * @prompt Revokes all active sessions for the user by userId. The 'userId' parameter is the user's ID. Sets revokedAt=now on all AuthSessionEntity where userId matches and revokedAt is null. Does not return anything (void).
     * @generated NOT
     */
    @Override
    @Transactional
    public void logoutAll(Long userId) {
        // generated start
List<AuthSessionEntity> sessions = authSessionEntityRepository.findByUserId(userId);
    java.time.LocalDateTime now = java.time.LocalDateTime.now();
    for (AuthSessionEntity session : sessions) {
        if (session.getRevokedAt() == null) {
            session.setRevokedAt(now);
        }
    }
    authSessionEntityRepository.saveAll(sessions);
// generated end
    }

    /**
     * @prompt Retrieves all active (non-revoked) sessions for the user by userId. The 'userId' parameter is the user's ID. Returns a list of AuthSessionSnapshot with id, userAgent, ipAddress, createdAt, lastUsedAt, expiresAt.
     * @generated NOT
     */
    @Override
    @Transactional
    public List<AuthSessionSnapshot> getSessions(Long userId) {
        // generated start
List<AuthSessionEntity> sessions = authSessionEntityRepository.findByUserId(userId);
    return sessions.stream()
        .filter(s -> s.getRevokedAt() == null)
        .map(s -> new AuthSessionSnapshot(
            s.getUserAgent(),
            s.getIpAddress(),
            s.getCreatedAt(),
            s.getLastUsedAt(),
            s.getExpiresAt()
        ))
        .collect(java.util.stream.Collectors.toList());
// generated end
    }

    /**
     * @prompt Revokes a specific session. The 'userId' parameter is the user's ID, 'request' contains sessionId. Verifies that the session belongs to the given userId. Sets revokedAt=now. Does not return anything (void).
     * @generated NOT
     */
    @Override
    @Transactional
    public void revokeSession(Long userId, RevokeSessionRequest request) {
        // generated start
AuthSessionEntity session = authSessionEntityRepository.findById(request.sessionId())
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND, "Session not found"));
    if (!session.getUserId().equals(userId)) {
        throw new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.BAD_REQUEST, "Session does not belong to the given user");
    }
    session.setRevokedAt(java.time.LocalDateTime.now());
    authSessionEntityRepository.save(session);
// generated end
    }

    /**
     * @prompt Sends a password reset email to the user. The 'email' parameter is the user's email. Looks up UserEntity by email. Creates an EmailTokenEntity with purpose=PASSWORD_RESET and a random token. (Email sending is out of scope - just create the token.) Throws NotFoundException if email not found. Does not return anything (void).
     * @generated NOT
     */
    @Override
    @Transactional
    public void forgotPassword(String email) {
        // generated start
UserEntity user = userEntityRepository.findByEmail(email)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND, "Email not found"));
    EmailTokenEntity tokenEntity = new EmailTokenEntity();
    tokenEntity.setUserId(user.getId());
    tokenEntity.setToken(java.util.UUID.randomUUID().toString());
    tokenEntity.setPurpose(dev.moproco.icedlatte.domain.TokenPurpose.PASSWORD_RESET);
    tokenEntity.setExpiresAt(java.time.LocalDateTime.now().plusHours(24));
    emailTokenEntityRepository.save(tokenEntity);
// generated end
    }

    /**
     * @prompt Changes the user's password using a reset token. The 'request' parameter contains resetToken and newPassword. Looks up an EmailTokenEntity by token with purpose=PASSWORD_RESET that is not yet used and not expired. Updates the UserEntity.password with BCrypt-hashed newPassword. Marks the EmailTokenEntity.usedAt=now. Does not return anything (void).
     * @generated NOT
     */
    @Override
    @Transactional
    public void changePassword(ResetPasswordRequest request) {
        // generated start
EmailTokenEntity emailToken = emailTokenEntityRepository.findByToken(request.resetToken())
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND, "Token not found"));
    if (emailToken.getPurpose() != dev.moproco.icedlatte.domain.TokenPurpose.PASSWORD_RESET
        || emailToken.getUsedAt() != null
        || emailToken.getExpiresAt().isBefore(java.time.LocalDateTime.now())) {
        throw new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND, "Token not found or already used");
    }
    UserEntity user = userEntityRepository.findById(emailToken.getUserId())
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND, "User not found"));
    user.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(request.newPassword()));
    userEntityRepository.save(user);
    emailToken.setUsedAt(java.time.LocalDateTime.now());
    emailTokenEntityRepository.save(emailToken);
// generated end
    }

}
