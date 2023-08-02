package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.repository.projection.BlockedUserProjection;
import com.gmail.javacoded78.service.AuthenticationService;
import com.gmail.javacoded78.service.BlockUserService;
import com.gmail.javacoded78.util.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlockUserServiceImpl implements BlockUserService {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserServiceHelper userServiceHelper;

    @Override
    public Page<BlockedUserProjection> getBlockList(Pageable pageable) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return userRepository.getUserBlockListById(authUserId, pageable);
    }

    @Override
    @Transactional
    public Boolean processBlockList(Long userId) {
        userServiceHelper.checkIsUserExist(userId);
        Long authUserId = authenticationService.getAuthenticatedUserId();
        boolean isUserBlocked = userRepository.isUserBlocked(authUserId, userId);

        if (isUserBlocked) {
            userRepository.unblockUser(authUserId, userId);
            return false;
        } else {
            userRepository.blockUser(authUserId, userId);
            userRepository.unfollow(authUserId, userId);
            userRepository.unfollow(userId, authUserId);
            // TODO get lists by user id instead of user.getLists()
//            user.getLists().removeIf(list -> list.getMembers().stream()
//                    .anyMatch(member -> member.getId().equals(currentUser.getId())));
            return true;
        }
    }
}
