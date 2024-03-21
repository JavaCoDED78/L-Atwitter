package com.gmail.javacoded78.service.util;

import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.exception.InputFieldException;
import com.gmail.javacoded78.model.User;
import com.gmail.javacoded78.repository.BlockUserRepository;
import com.gmail.javacoded78.repository.FollowerUserRepository;
import com.gmail.javacoded78.repository.MuteUserRepository;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.repository.projection.SameFollower;
import com.gmail.javacoded78.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;

@Component
@RequiredArgsConstructor
public class UserServiceHelper {

    @Lazy
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final FollowerUserRepository followerUserRepository;
    private final BlockUserRepository blockUserRepository;
    private final MuteUserRepository muteUserRepository;

    public void processInputErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
    }

    public void validateUserProfile(Long userId) {
        checkIsUserExist(userId);
        Long authUserId = authenticationService.getAuthenticatedUserId();

        if (!userId.equals(authUserId)) {
            checkIsUserBlocked(userId);
            checkIsUserHavePrivateProfile(userId);
        }
    }

    public void checkIsUserExistOrMyProfileBlocked(Long userId) {
        checkIsUserExist(userId);
        checkIsUserBlocked(userId);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException(String.format(USER_ID_NOT_FOUND, userId), HttpStatus.NOT_FOUND));
    }

    public void checkIsUserExist(Long userId) {
        boolean userExist = userRepository.isUserExist(userId);

        if (!userExist) {
            throw new ApiRequestException(String.format(USER_ID_NOT_FOUND, userId), HttpStatus.NOT_FOUND);
        }
    }

    public void checkIsUserBlocked(Long userId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        boolean userBlocked = blockUserRepository.isUserBlocked(userId, authUserId);

        if (userBlocked) {
            throw new ApiRequestException(USER_PROFILE_BLOCKED, HttpStatus.BAD_REQUEST);
        }
    }

    public void checkIsUserHavePrivateProfile(Long userId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();

        if (!userRepository.isUserHavePrivateProfile(userId, authUserId)) {
            throw new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public boolean isUserFollowByOtherUser(Long userId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return followerUserRepository.isUserFollowByOtherUser(authUserId, userId);
    }

    public boolean isUserHavePrivateProfile(Long userId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return !userRepository.isUserHavePrivateProfile(userId, authUserId);
    }

    public boolean isUserBlockedByMyProfile(Long userId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return blockUserRepository.isUserBlocked(authUserId, userId);
    }

    public boolean isUserMutedByMyProfile(Long userId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return muteUserRepository.isUserMuted(authUserId, userId);
    }

    public boolean isMyProfileBlockedByUser(Long userId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return blockUserRepository.isUserBlocked(userId, authUserId);
    }

    public boolean isMyProfileWaitingForApprove(Long userId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return userRepository.isMyProfileWaitingForApprove(userId, authUserId);
    }

    public boolean isMyProfileSubscribed(Long userId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return userRepository.isMyProfileSubscribed(userId, authUserId);
    }

    public List<SameFollower> getSameFollowers(Long userId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return followerUserRepository.getSameFollowers(userId, authUserId, SameFollower.class);
    }
}
