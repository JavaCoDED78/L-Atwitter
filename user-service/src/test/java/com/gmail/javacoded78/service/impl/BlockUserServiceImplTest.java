package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.repository.BlockUserRepository;
import com.gmail.javacoded78.repository.FollowerUserRepository;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.repository.projection.BlockedUserProjection;
import com.gmail.javacoded78.service.AuthenticationService;
import com.gmail.javacoded78.service.UserServiceTestHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
class BlockUserServiceImplTest extends AbstractAuthTest {

    private final BlockUserServiceImpl blockUserService;

    @MockBean
    private final AuthenticationService authenticationService;

    @MockBean
    private final BlockUserRepository blockUserRepository;

    @MockBean
    private final FollowerUserRepository followerUserRepository;

    @MockBean
    private final UserRepository userRepository;

    @BeforeEach
    void setUpMock() {
        when(authenticationService.getAuthenticatedUserId()).thenReturn(TestConstants.USER_ID);
    }

    @Test
    void getBlockList_ShouldReturnBlockedUserProjections() {
        Page<BlockedUserProjection> blockedUserProjections = UserServiceTestHelper.createBlockedUserProjections();
        when(blockUserRepository.getUserBlockListById(TestConstants.USER_ID, pageable)).thenReturn(blockedUserProjections);
        assertEquals(blockedUserProjections, blockUserService.getBlockList(pageable));
        verify(authenticationService, times(1)).getAuthenticatedUserId();
        verify(blockUserRepository, times(1)).getUserBlockListById(TestConstants.USER_ID, pageable);
    }

    @Test
    void processBlockList_ShouldBlockUser() {
        when(userRepository.isUserExist(TestConstants.USER_ID)).thenReturn(true);
        when(blockUserRepository.isUserBlocked(TestConstants.USER_ID, 2L)).thenReturn(false);
        assertTrue(blockUserService.processBlockList(TestConstants.USER_ID));
        verify(authenticationService, times(1)).getAuthenticatedUserId();
        verify(userRepository, times(1)).isUserExist(TestConstants.USER_ID);
        verify(blockUserRepository, times(1)).isUserBlocked(TestConstants.USER_ID, 2L);
        verify(blockUserRepository, times(1)).blockUser(TestConstants.USER_ID, 2L);
        verify(followerUserRepository, times(2)).unfollow(any(), any());
    }

    @Test
    void processBlockList_ShouldUnblockUser() {
        when(userRepository.isUserExist(TestConstants.USER_ID)).thenReturn(true);
        when(blockUserRepository.isUserBlocked(TestConstants.USER_ID, 2L)).thenReturn(true);
        assertFalse(blockUserService.processBlockList(TestConstants.USER_ID));
        verify(authenticationService, times(1)).getAuthenticatedUserId();
        verify(userRepository, times(1)).isUserExist(TestConstants.USER_ID);
        verify(blockUserRepository, times(1)).isUserBlocked(TestConstants.USER_ID, 2L);
        verify(blockUserRepository, times(1)).unblockUser(TestConstants.USER_ID, 2L);
    }

    @Test
    void processBlockList_ShouldUserNotFound() {
        when(userRepository.isUserExist(TestConstants.USER_ID)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> blockUserService.processBlockList(TestConstants.USER_ID));
        assertEquals(String.format(USER_ID_NOT_FOUND, TestConstants.USER_ID), exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(userRepository, times(1)).isUserExist(TestConstants.USER_ID);
    }
}
