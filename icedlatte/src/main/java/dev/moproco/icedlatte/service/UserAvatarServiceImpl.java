package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.moproco.icedlatte.UserAvatarService;
import dev.moproco.icedlatte.domain.FileMetadata;
import dev.moproco.icedlatte.repository.FileMetadataRepository;
import dev.moproco.icedlatte.domain.UserAvatarUpload;
import dev.moproco.icedlatte.repository.UserAvatarUploadRepository;
import dev.moproco.icedlatte.domain.UserEntity;
import dev.moproco.icedlatte.repository.UserEntityRepository;
import dev.moproco.icedlatte.dto.FileMetadataDto;
import dev.moproco.icedlatte.domain.UserAvatarUploadStatus;


@Service
public class UserAvatarServiceImpl implements UserAvatarService {

    private final FileMetadataRepository fileMetadataRepository;
    private final UserAvatarUploadRepository userAvatarUploadRepository;
    private final UserEntityRepository userEntityRepository;

    public UserAvatarServiceImpl(FileMetadataRepository fileMetadataRepository, UserAvatarUploadRepository userAvatarUploadRepository, UserEntityRepository userEntityRepository) {
        this.fileMetadataRepository = fileMetadataRepository;
        this.userAvatarUploadRepository = userAvatarUploadRepository;
        this.userEntityRepository = userEntityRepository;
    }

    /**
     * @prompt Creates a new UserAvatarUpload entry for the user by userId with status PENDING_UPLOAD. Stores the provided bucketName and fileName from fileMetadata. Marks any previous active avatar as SUPERSEDED. Returns the id of the new upload for the client to upload to.
     * @generated NOT
     */
    @Override
    @Transactional
    public void uploadUserAvatar(Long userId, FileMetadataDto fileMetadata) {
        // generated start
UserEntity user = userEntityRepository.findById(userId)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "User not found"));

    // Mark any previous active avatar as SUPERSEDED
    java.util.List<UserAvatarUpload> activeAvatars = userAvatarUploadRepository.findByActiveTrue();
    for (UserAvatarUpload avatar : activeAvatars) {
        if (avatar.getUserId().equals(userId)) {
            avatar.setActive(false);
            avatar.setStatus(UserAvatarUploadStatus.SUPERSEDED);
            userAvatarUploadRepository.save(avatar);
        }
    }

    // Create new UserAvatarUpload with PENDING_UPLOAD status
    UserAvatarUpload newUpload = new UserAvatarUpload();
    newUpload.setUserId(userId);
    newUpload.setStatus(UserAvatarUploadStatus.PENDING_UPLOAD);
    newUpload.setOriginalBucket(fileMetadata.bucketName());
    newUpload.setOriginalKey(fileMetadata.fileName());
    newUpload.setActive(true);
    newUpload.setCreatedAt(java.time.LocalDateTime.now());

    userAvatarUploadRepository.save(newUpload);
// generated end
    }

    /**
     * @prompt Returns the URL to the user's current active avatar image by userId. Returns null if no avatar exists. The URL points to the processedBucket/processedKey of the active UserAvatarUpload with status READY.
     * @generated NOT
     */
    @Override
    @Transactional
    public String getUserAvatarLink(Long userId) {
        // generated start
UserAvatarUpload activeAvatar = userAvatarUploadRepository.findByUserId(userId).stream()
            .filter(u -> u.getActive() != null && u.getActive() && u.getStatus() == UserAvatarUploadStatus.READY)
            .findFirst()
            .orElse(null);
    if (activeAvatar == null) {
        return null;
    }
    String bucket = activeAvatar.getProcessedBucket();
    String key = activeAvatar.getProcessedKey();
    if (bucket == null || key == null) {
        return null;
    }
    return bucket + "/" + key;
// generated end
    }

    /**
     * @prompt Marks the user's active avatar as SUPERSEDED by userId. Does not delete the file from storage, just sets active=false on the UserAvatarUpload.
     * @generated NOT
     */
    @Override
    @Transactional
    public void deleteUserAvatar(Long userId) {
        // generated start
// Find the user's active avatar(s) and mark them as SUPERSEDED
    java.util.List<UserAvatarUpload> userAvatars = userAvatarUploadRepository.findByUserId(userId);
    for (UserAvatarUpload avatar : userAvatars) {
        if (Boolean.TRUE.equals(avatar.getActive())) {
            avatar.setActive(false);
            avatar.setStatus(UserAvatarUploadStatus.SUPERSEDED);
            userAvatarUploadRepository.save(avatar);
        }
    }
// generated end
    }

}
