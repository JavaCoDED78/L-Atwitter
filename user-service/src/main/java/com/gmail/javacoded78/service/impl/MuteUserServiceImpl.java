package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.repository.projection.MutedUserProjection;
import com.gmail.javacoded78.service.AuthenticationService;
import com.gmail.javacoded78.service.MuteUserService;
import com.gmail.javacoded78.util.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MuteUserServiceImpl implements MuteUserService {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserServiceHelper userServiceHelper;

    @Override
    public Page<MutedUserProjection> getMutedList(Pageable pageable) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return userRepository.getUserMuteListById(authUserId, pageable);
    }

    @Override
    @Transactional
    public Boolean processMutedList(Long userId) {
        userServiceHelper.checkIsUserExist(userId);
        Long authUserId = authenticationService.getAuthenticatedUserId();
        boolean isUserMuted = userRepository.isUserMuted(authUserId, userId);

        if (isUserMuted) {
            userRepository.unmuteUser(authUserId, userId);
            return false;
        } else {
            userRepository.muteUser(authUserId, userId);
            return true;
        }
    }
}