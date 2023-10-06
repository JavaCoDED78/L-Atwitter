package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.repository.MuteUserRepository;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.repository.projection.MutedUserProjection;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
class MuteUserServiceImplTest extends AbstractAuthTest {

    private final MuteUserServiceImpl muteUserService;

    @MockBean
    private final MuteUserRepository muteUserRepository;

    @MockBean
    private final AuthenticationService authenticationService;

    @MockBean
    private final UserRepository userRepository;

    @BeforeEach
    void setUpMock() {
        when(authenticationService.getAuthenticatedUserId()).thenReturn(TestConstants.USER_ID);
    }

    @Test
    void getMutedList_ShouldReturnMutedUserProjections() {
        Page<MutedUserProjection> mutedUserProjection = UserServiceTestHelper.createMutedUserProjections();
        when(muteUserRepository.getUserMuteListById(TestConstants.USER_ID, pageable)).thenReturn(mutedUserProjection);
        assertEquals(mutedUserProjection, muteUserService.getMutedList(pageable));
        verify(authenticationService, times(1)).getAuthenticatedUserId();
        verify(muteUserRepository, times(1)).getUserMuteListById(TestConstants.USER_ID, pageable);
    }

    @Test
    void processMutedList_ShouldMuteUser() {
        when(userRepository.isUserExist(TestConstants.USER_ID)).thenReturn(true);
        when(muteUserRepository.isUserMuted(TestConstants.USER_ID, 2L)).thenReturn(false);
        assertTrue(muteUserService.processMutedList(TestConstants.USER_ID));
        verify(authenticationService, times(1)).getAuthenticatedUserId();
        verify(userRepository, times(1)).isUserExist(TestConstants.USER_ID);
        verify(muteUserRepository, times(1)).isUserMuted(TestConstants.USER_ID, 2L);
        verify(muteUserRepository, times(1)).muteUser(TestConstants.USER_ID, 2L);
    }

    @Test
    void processMutedList_ShouldUnmuteUser() {
        when(userRepository.isUserExist(TestConstants.USER_ID)).thenReturn(true);
        when(muteUserRepository.isUserMuted(TestConstants.USER_ID, 2L)).thenReturn(true);
        assertFalse(muteUserService.processMutedList(TestConstants.USER_ID));
        verify(authenticationService, times(1)).getAuthenticatedUserId();
        verify(userRepository, times(1)).isUserExist(TestConstants.USER_ID);
        verify(muteUserRepository, times(1)).isUserMuted(TestConstants.USER_ID, 2L);
        verify(muteUserRepository, times(1)).unmuteUser(TestConstants.USER_ID, 2L);
    }

    @Test
    void processMutedList_ShouldUserNotFound() {
        when(userRepository.isUserExist(TestConstants.USER_ID)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> muteUserService.processMutedList(TestConstants.USER_ID));
        assertEquals(String.format(USER_ID_NOT_FOUND, TestConstants.USER_ID), exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(userRepository, times(1)).isUserExist(TestConstants.USER_ID);
    }
}
