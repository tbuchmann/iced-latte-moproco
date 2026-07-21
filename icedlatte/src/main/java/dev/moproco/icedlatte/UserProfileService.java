package dev.moproco.icedlatte;

import dev.moproco.icedlatte.dto.ChangePasswordRequest;
import dev.moproco.icedlatte.dto.EditUserProfileRequest;
import dev.moproco.icedlatte.dto.UserLookupSnapshot;

public interface UserProfileService {

    UserLookupSnapshot getUserProfile(Long userId);
    UserLookupSnapshot editUserProfile(Long userId, EditUserProfileRequest request);
    void changeUserPassword(Long userId, ChangePasswordRequest request);
    void deleteUserProfile(Long userId);

}
