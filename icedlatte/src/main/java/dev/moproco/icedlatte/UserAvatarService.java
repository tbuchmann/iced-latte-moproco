package dev.moproco.icedlatte;

import dev.moproco.icedlatte.dto.FileMetadataDto;

public interface UserAvatarService {

    void uploadUserAvatar(Long userId, FileMetadataDto fileMetadata);
    String getUserAvatarLink(Long userId);
    void deleteUserAvatar(Long userId);

}
