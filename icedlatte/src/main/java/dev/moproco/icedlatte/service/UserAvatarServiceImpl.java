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
        throw new UnsupportedOperationException("Not yet implemented");
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
        throw new UnsupportedOperationException("Not yet implemented");
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
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

}
