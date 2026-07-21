package dev.moproco.icedlatte.controller;

import org.springframework.web.bind.annotation.*;
import dev.moproco.icedlatte.UserProfileService;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import dev.moproco.icedlatte.dto.ChangePasswordRequest;
import dev.moproco.icedlatte.dto.EditUserProfileRequest;
import dev.moproco.icedlatte.dto.UserLookupSnapshot;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "/api/v1/users")
public class UserProfileServiceController {

    private final UserProfileService userProfileService;

    public UserProfileServiceController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User Profile", description = "Retrieves the user's profile by userId. Returns a UserLookupSnapshot with id, firstName, lastName, email. Throws NotFoundException if user not found.")
    public ResponseEntity<UserLookupSnapshot> getUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userProfileService.getUserProfile(userId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit User Profile", description = "Updates the user's profile fields (firstName, lastName, birthDate, phoneNumber) by userId. Returns the updated UserLookupSnapshot. Ignores null fields in the request.")
    public ResponseEntity<UserLookupSnapshot> editUserProfile(@PathVariable Long userId, @RequestBody @Valid EditUserProfileRequest request) {
        return ResponseEntity.ok(userProfileService.editUserProfile(userId, request));
    }

    @PutMapping("/changeUserPassword/{userId}")
    @Operation(summary = "Change User Password", description = "Changes the user's password by userId. Verifies currentPassword against the stored hash using BCrypt, then encodes and saves the newPassword. Throws BadCredentialsException if currentPassword is wrong.")
    public ResponseEntity<Void> changeUserPassword(@PathVariable Long userId, @RequestBody @Valid ChangePasswordRequest request) {
        userProfileService.changeUserPassword(userId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User Profile", description = "Deletes the user by userId and cascades to related entities: UserGrantedAuthority, Address, DeliveryAddressEntity, UserAvatarUpload, AuthSessionEntity, OAuthIdentityEntity, FavoriteListEntity, ShoppingCart. Also deletes associated AuthSessionEntity records.")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long userId) {
        userProfileService.deleteUserProfile(userId);
        return ResponseEntity.noContent().build();
    }

}
