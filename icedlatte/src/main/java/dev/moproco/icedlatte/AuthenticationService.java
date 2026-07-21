package dev.moproco.icedlatte;

import java.util.List;
import dev.moproco.icedlatte.dto.AuthResponse;
import dev.moproco.icedlatte.dto.AuthSessionSnapshot;
import dev.moproco.icedlatte.dto.LoginRequest;
import dev.moproco.icedlatte.dto.RegisterRequest;
import dev.moproco.icedlatte.dto.ResetPasswordRequest;
import dev.moproco.icedlatte.dto.RevokeSessionRequest;

public interface AuthenticationService {

    AuthResponse register(RegisterRequest request);
    void confirmEmail(String token);
    AuthResponse authenticate(LoginRequest request);
    AuthResponse refreshToken(String refreshToken);
    void logout(String refreshToken);
    void logoutAll(Long userId);
    List<AuthSessionSnapshot> getSessions(Long userId);
    void revokeSession(Long userId, RevokeSessionRequest request);
    void forgotPassword(String email);
    void changePassword(ResetPasswordRequest request);

}
