package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.event.UserEvent;
import com.gmail.javacoded78.model.User;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.service.UserService;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean isUserExists(Long userId) {
        return userRepository.isUserExists(userId);
    }

    @Override
    @Transactional
    public void handleUser(UserEvent userEvent) {
        Optional<User> user = userRepository.findById(userEvent.getId());

        if (user.isPresent()) {
            mapUser(user.get(), userEvent);
        } else {
            User newUser = new User();
            mapUser(newUser, userEvent);
            userRepository.save(newUser);
        }
    }

    @Override
    public boolean isMyProfileBlockedByUser(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.isUserBlocked(userId, authUserId);
    }

    @Override
    public boolean isUserHavePrivateProfile(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.isUserHavePrivateProfile(userId, authUserId);
    }

    private void mapUser(User user, UserEvent userEvent) {
        user.setUsername(userEvent.getUsername());
        user.setFullName(userEvent.getFullName());
        user.setPrivateProfile(userEvent.isPrivateProfile());
    }
}
