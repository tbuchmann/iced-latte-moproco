package dev.moproco.icedlatte.controller;

import org.springframework.web.bind.annotation.*;
import dev.moproco.icedlatte.UserAvatarService;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import dev.moproco.icedlatte.dto.FileMetadataDto;

@RestController
@RequestMapping("/api/v1/users/avatar")
@Tag(name = "/api/v1/users/avatar")
public class UserAvatarServiceController {

    private final UserAvatarService userAvatarService;

    public UserAvatarServiceController(UserAvatarService userAvatarService) {
        this.userAvatarService = userAvatarService;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Upload User Avatar", description = "Creates a new UserAvatarUpload entry for the user by userId with status PENDING_UPLOAD. Stores the provided bucketName and fileName from fileMetadata. Marks any previous active avatar as SUPERSEDED. Returns the id of the new upload for the client to upload to.")
    public ResponseEntity<Void> uploadUserAvatar(@PathVariable Long userId, @RequestBody @Valid FileMetadataDto fileMetadata) {
        userAvatarService.uploadUserAvatar(userId, fileMetadata);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User Avatar Link", description = "Returns the URL to the user's current active avatar image by userId. Returns null if no avatar exists. The URL points to the processedBucket/processedKey of the active UserAvatarUpload with status READY.")
    public ResponseEntity<String> getUserAvatarLink(@PathVariable Long userId) {
        return ResponseEntity.ok(userAvatarService.getUserAvatarLink(userId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User Avatar", description = "Marks the user's active avatar as SUPERSEDED by userId. Does not delete the file from storage, just sets active=false on the UserAvatarUpload.")
    public ResponseEntity<Void> deleteUserAvatar(@PathVariable Long userId) {
        userAvatarService.deleteUserAvatar(userId);
        return ResponseEntity.noContent().build();
    }

}
