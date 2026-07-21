package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.moproco.icedlatte.UserProfileService;
import dev.moproco.icedlatte.domain.UserEntity;
import dev.moproco.icedlatte.repository.UserEntityRepository;
import dev.moproco.icedlatte.domain.Address;
import dev.moproco.icedlatte.repository.AddressRepository;
import dev.moproco.icedlatte.domain.AuthSessionEntity;
import dev.moproco.icedlatte.repository.AuthSessionEntityRepository;
import dev.moproco.icedlatte.domain.DeliveryAddressEntity;
import dev.moproco.icedlatte.repository.DeliveryAddressEntityRepository;
import dev.moproco.icedlatte.domain.FavoriteListEntity;
import dev.moproco.icedlatte.repository.FavoriteListEntityRepository;
import dev.moproco.icedlatte.domain.OAuthIdentityEntity;
import dev.moproco.icedlatte.repository.OAuthIdentityEntityRepository;
import dev.moproco.icedlatte.domain.ShoppingCart;
import dev.moproco.icedlatte.repository.ShoppingCartRepository;
import dev.moproco.icedlatte.domain.UserAvatarUpload;
import dev.moproco.icedlatte.repository.UserAvatarUploadRepository;
import dev.moproco.icedlatte.domain.UserGrantedAuthority;
import dev.moproco.icedlatte.repository.UserGrantedAuthorityRepository;
import dev.moproco.icedlatte.dto.ChangePasswordRequest;
import dev.moproco.icedlatte.dto.EditUserProfileRequest;
import dev.moproco.icedlatte.dto.UserLookupSnapshot;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserEntityRepository userEntityRepository;
    private final AddressRepository addressRepository;
    private final AuthSessionEntityRepository authSessionEntityRepository;
    private final DeliveryAddressEntityRepository deliveryAddressEntityRepository;
    private final FavoriteListEntityRepository favoriteListEntityRepository;
    private final OAuthIdentityEntityRepository oAuthIdentityEntityRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserAvatarUploadRepository userAvatarUploadRepository;
    private final UserGrantedAuthorityRepository userGrantedAuthorityRepository;

    public UserProfileServiceImpl(UserEntityRepository userEntityRepository, AddressRepository addressRepository, AuthSessionEntityRepository authSessionEntityRepository, DeliveryAddressEntityRepository deliveryAddressEntityRepository, FavoriteListEntityRepository favoriteListEntityRepository, OAuthIdentityEntityRepository oAuthIdentityEntityRepository, ShoppingCartRepository shoppingCartRepository, UserAvatarUploadRepository userAvatarUploadRepository, UserGrantedAuthorityRepository userGrantedAuthorityRepository) {
        this.userEntityRepository = userEntityRepository;
        this.addressRepository = addressRepository;
        this.authSessionEntityRepository = authSessionEntityRepository;
        this.deliveryAddressEntityRepository = deliveryAddressEntityRepository;
        this.favoriteListEntityRepository = favoriteListEntityRepository;
        this.oAuthIdentityEntityRepository = oAuthIdentityEntityRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userAvatarUploadRepository = userAvatarUploadRepository;
        this.userGrantedAuthorityRepository = userGrantedAuthorityRepository;
    }

    /**
     * @prompt Retrieves the user's profile by userId. Returns a UserLookupSnapshot with id, firstName, lastName, email. Throws NotFoundException if user not found.
     * @generated NOT
     */
    @Override
    @Transactional
    public UserLookupSnapshot getUserProfile(Long userId) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

    /**
     * @prompt Updates the user's profile fields (firstName, lastName, birthDate, phoneNumber) by userId. Returns the updated UserLookupSnapshot. Ignores null fields in the request.
     * @generated NOT
     */
    @Override
    @Transactional
    public UserLookupSnapshot editUserProfile(Long userId, EditUserProfileRequest request) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

    /**
     * @prompt Changes the user's password by userId. Verifies currentPassword against the stored hash using BCrypt, then encodes and saves the newPassword. Throws BadCredentialsException if currentPassword is wrong.
     * @generated NOT
     */
    @Override
    @Transactional
    public void changeUserPassword(Long userId, ChangePasswordRequest request) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

    /**
     * @prompt Deletes the user by userId and cascades to related entities: UserGrantedAuthority, Address, DeliveryAddressEntity, UserAvatarUpload, AuthSessionEntity, OAuthIdentityEntity, FavoriteListEntity, ShoppingCart. Also deletes associated AuthSessionEntity records.
     * @generated NOT
     */
    @Override
    @Transactional
    public void deleteUserProfile(Long userId) {
        // generated start
        throw new UnsupportedOperationException("Not yet implemented");
        // generated end
    }

}
