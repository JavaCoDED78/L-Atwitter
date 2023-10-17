package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.request.NotificationRequest;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.NotificationClient;
import com.gmail.javacoded78.repository.BlockUserRepository;
import com.gmail.javacoded78.repository.FollowerUserRepository;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.repository.projection.BaseUserProjection;
import com.gmail.javacoded78.repository.projection.FollowerUserProjection;
import com.gmail.javacoded78.repository.projection.UserProfileProjection;
import com.gmail.javacoded78.repository.projection.UserProjection;
import com.gmail.javacoded78.service.AuthenticationService;
import com.gmail.javacoded78.service.UserServiceTestHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
class FollowerUserServiceImplTest extends AbstractAuthTest {

    private final FollowerUserServiceImpl followerUserService;

    @MockBean
    private final UserRepository userRepository;

    @MockBean
    private final BlockUserRepository blockUserRepository;

    @MockBean
    private final FollowerUserRepository followerUserRepository;

    @MockBean
    private final AuthenticationService authenticationService;

    @MockBean
    private final NotificationClient notificationClient;

    private static final Page<UserProjection> userProjections = UserServiceTestHelper.createUserProjections();
    private static final UserProfileProjection userProfileProjection = UserServiceTestHelper.createUserProfileProjection();


    @BeforeEach
    void setUpMock() {
        when(authenticationService.getAuthenticatedUserId()).thenReturn(TestConstants.USER_ID);
    }

    @Test
    void getFollowers_ShouldReturnUserProjections() {
        when(followerUserRepository.getFollowersById(TestConstants.USER_ID, pageable)).thenReturn(userProjections);
        testReturnUserProjections(() -> followerUserService.getFollowers(TestConstants.USER_ID, pageable));
        verify(followerUserRepository, times(1)).getFollowersById(TestConstants.USER_ID, pageable);
    }

    @Test
    void getFollowers_ShouldThrowUserNotFound() {
        testThrowUserNotFound(() -> followerUserService.getFollowers(1L, pageable));
    }

    @Test
    void getFollowers_ShouldThrowUserProfileBLocked() {
        testThrowUserProfileBlocked(() -> followerUserService.getFollowers(1L, pageable));
    }

    @Test
    void getFollowers_ShouldThrowUserHavePrivateProfile() {
        testThrowUserHavePrivateProfile(() -> followerUserService.getFollowers(1L, pageable));
    }

    @Test
    void getFollowing_ShouldReturnUserProjections() {
        when(followerUserRepository.getFollowingById(TestConstants.USER_ID, pageable)).thenReturn(userProjections);
        testReturnUserProjections(() -> followerUserService.getFollowing(TestConstants.USER_ID, pageable));
        verify(followerUserRepository, times(1)).getFollowingById(TestConstants.USER_ID, pageable);
    }

    @Test
    void getFollowing_ShouldThrowUserNotFound() {
        testThrowUserNotFound(() -> followerUserService.getFollowing(1L, pageable));
    }

    @Test
    void getFollowing_ShouldThrowUserProfileBLocked() {
        testThrowUserProfileBlocked(() -> followerUserService.getFollowing(1L, pageable));
    }

    @Test
    void getFollowing_ShouldThrowUserHavePrivateProfile() {
        testThrowUserHavePrivateProfile(() -> followerUserService.getFollowing(1L, pageable));
    }

    @Test
    void getFollowerRequests_ShouldReturnFollowerUserProjections() {
        Page<FollowerUserProjection> followerUserProjections = UserServiceTestHelper.createFollowerUserProjections();
        when(followerUserRepository.getFollowerRequests(TestConstants.USER_ID, pageable)).thenReturn(followerUserProjections);
        assertEquals(followerUserProjections, followerUserService.getFollowerRequests(pageable));
        verify(authenticationService, times(1)).getAuthenticatedUserId();
        verify(followerUserRepository, times(1)).getFollowerRequests(TestConstants.USER_ID, pageable);
    }

    @Test
    void processFollow_ShouldFollow() {
        NotificationRequest request = NotificationRequest.builder()
                .notificationType(NotificationType.FOLLOW)
                .userId(TestConstants.USER_ID)
                .userToFollowId(1L)
                .notifiedUserId(1L)
                .build();
        when(userRepository.isUserExist(1L)).thenReturn(true);
        when(blockUserRepository.isUserBlocked(1L, TestConstants.USER_ID)).thenReturn(false);
        when(followerUserRepository.isFollower(TestConstants.USER_ID, 1L)).thenReturn(false);
        when(userRepository.getUserPrivateProfile(1L)).thenReturn(false);
        assertTrue(followerUserService.processFollow(1L));
        verify(authenticationService, times(2)).getAuthenticatedUserId();
        verify(userRepository, times(1)).isUserExist(1L);
        verify(blockUserRepository, times(1)).isUserBlocked(1L, TestConstants.USER_ID);
        verify(followerUserRepository, times(1)).isFollower(TestConstants.USER_ID, 1L);
        verify(userRepository, times(1)).getUserPrivateProfile(1L);
        verify(followerUserRepository, times(1)).follow(TestConstants.USER_ID, 1L);
        verify(notificationClient, times(1)).sendNotification(request);
    }

    @Test
    void processFollow_ShouldFollowPrivateProfile() {
        when(userRepository.isUserExist(1L)).thenReturn(true);
        when(blockUserRepository.isUserBlocked(1L, TestConstants.USER_ID)).thenReturn(false);
        when(followerUserRepository.isFollower(TestConstants.USER_ID, 1L)).thenReturn(false);
        when(userRepository.getUserPrivateProfile(1L)).thenReturn(true);
        assertFalse(followerUserService.processFollow(1L));
        verify(authenticationService, times(2)).getAuthenticatedUserId();
        verify(userRepository, times(1)).isUserExist(1L);
        verify(blockUserRepository, times(1)).isUserBlocked(1L, TestConstants.USER_ID);
        verify(followerUserRepository, times(1)).isFollower(TestConstants.USER_ID, 1L);
        verify(userRepository, times(1)).getUserPrivateProfile(1L);
        verify(followerUserRepository, times(1)).addFollowerRequest(TestConstants.USER_ID, 1L);
    }

    @Test
    void processFollow_ShouldUnfollow() {
        when(userRepository.isUserExist(1L)).thenReturn(true);
        when(blockUserRepository.isUserBlocked(1L, TestConstants.USER_ID)).thenReturn(false);
        when(followerUserRepository.isFollower(TestConstants.USER_ID, 1L)).thenReturn(true);
        assertFalse(followerUserService.processFollow(1L));
        verify(authenticationService, times(2)).getAuthenticatedUserId();
        verify(userRepository, times(1)).isUserExist(1L);
        verify(blockUserRepository, times(1)).isUserBlocked(1L, TestConstants.USER_ID);
        verify(followerUserRepository, times(1)).isFollower(TestConstants.USER_ID, 1L);
        verify(followerUserRepository, times(1)).unfollow(TestConstants.USER_ID, 1L);
        verify(userRepository, times(1)).unsubscribe(TestConstants.USER_ID, 1L);
    }

    @Test
    void processFollow_ShouldThrowUserNotFound() {
        testThrowUserNotFound(() -> followerUserService.processFollow(1L));
    }

    @Test
    void processFollow_ShouldThrowUserProfileBLocked() {
        testThrowUserProfileBlocked(() -> followerUserService.processFollow(1L));
    }

    @Test
    void overallFollowers_ShouldReturnBaseUserProjections() {
        List<BaseUserProjection> baseUserProjections = UserServiceTestHelper.createBaseUserProjections();
        when(userRepository.isUserExist(1L)).thenReturn(true);
        when(userRepository.isUserHavePrivateProfile(1L, TestConstants.USER_ID)).thenReturn(true);
        when(followerUserRepository.getSameFollowers(1L, TestConstants.USER_ID, BaseUserProjection.class)).thenReturn(baseUserProjections);
        assertEquals(baseUserProjections, followerUserService.overallFollowers(1L));
        verify(authenticationService, times(4)).getAuthenticatedUserId();
        verify(userRepository, times(1)).isUserExist(1L);
    }

    @Test
    void overallFollowers_ShouldThrowUserNotFound() {
        testThrowUserNotFound(() -> followerUserService.overallFollowers(1L));
    }

    @Test
    void overallFollowers_ShouldThrowUserProfileBlocked() {
        testThrowUserProfileBlocked(() -> followerUserService.overallFollowers(1L));
    }

    @Test
    void overallFollowers_ShouldThrowUserHavePrivateProfile() {
        testThrowUserHavePrivateProfile(() -> followerUserService.overallFollowers(1L));
    }

    @Test
    void processFollowRequestToPrivateProfile_ShouldAddFollowerRequest() {
        when(userRepository.isUserExist(1L)).thenReturn(true);
        when(blockUserRepository.isUserBlocked(1L, TestConstants.USER_ID)).thenReturn(false);
        when(followerUserRepository.isFollowerRequest(1L, TestConstants.USER_ID)).thenReturn(false);
        when(userRepository.getUserById(1L, UserProfileProjection.class)).thenReturn(Optional.of(userProfileProjection));
        assertEquals(userProfileProjection, followerUserService.processFollowRequestToPrivateProfile(1L));
        verify(userRepository, times(1)).isUserExist(1L);
        verify(blockUserRepository, times(1)).isUserBlocked(1L, TestConstants.USER_ID);
        verify(followerUserRepository, times(1)).addFollowerRequest(TestConstants.USER_ID, 1L);
        verify(followerUserRepository, times(1)).isFollowerRequest(1L, TestConstants.USER_ID);
    }

    @Test
    void processFollowRequestToPrivateProfile_ShouldRemoveFollowerRequest() {
        when(userRepository.isUserExist(1L)).thenReturn(true);
        when(blockUserRepository.isUserBlocked(1L, TestConstants.USER_ID)).thenReturn(false);
        when(followerUserRepository.isFollowerRequest(1L, TestConstants.USER_ID)).thenReturn(true);
        when(userRepository.getUserById(1L, UserProfileProjection.class)).thenReturn(Optional.of(userProfileProjection));
        assertEquals(userProfileProjection, followerUserService.processFollowRequestToPrivateProfile(1L));
        verify(userRepository, times(1)).isUserExist(1L);
        verify(blockUserRepository, times(1)).isUserBlocked(1L, TestConstants.USER_ID);
        verify(followerUserRepository, times(1)).removeFollowerRequest(TestConstants.USER_ID, 1L);
        verify(followerUserRepository, times(1)).isFollowerRequest(1L, TestConstants.USER_ID);
    }

    @Test
    public void processFollowRequestToPrivateProfile_ShouldThrowUserNotFound() {
        testThrowUserNotFound(() -> followerUserService.processFollowRequestToPrivateProfile(1L));
    }

    @Test
    public void processFollowRequestToPrivateProfile_ShouldThrowUserProfileBlocked() {
        testThrowUserProfileBlocked(() -> followerUserService.processFollowRequestToPrivateProfile(1L));
    }

    @Test
    void acceptFollowRequest() {
        when(userRepository.isUserExist(1L)).thenReturn(true);
        assertEquals(String.format("User (id:%s) accepted.", 1L), followerUserService.acceptFollowRequest(1L));
        verify(userRepository, times(1)).isUserExist(1L);
        verify(followerUserRepository, times(1)).removeFollowerRequest(1L, TestConstants.USER_ID);
        verify(followerUserRepository, times(1)).follow(1L, TestConstants.USER_ID);
    }

    @Test
    void declineFollowRequest() {
        when(userRepository.isUserExist(1L)).thenReturn(true);
        assertEquals(String.format("User (id:%s) declined.", 1L), followerUserService.declineFollowRequest(1L));
        verify(userRepository, times(1)).isUserExist(1L);
        verify(followerUserRepository, times(1)).removeFollowerRequest(1L, TestConstants.USER_ID);
    }


    @SneakyThrows
    private void testReturnUserProjections(Executable executable) {
        when(userRepository.isUserExist(TestConstants.USER_ID)).thenReturn(true);
        executable.execute();
        verify(authenticationService, times(1)).getAuthenticatedUserId();
        verify(userRepository, times(1)).isUserExist(TestConstants.USER_ID);
    }

    private void testThrowUserNotFound(Executable executable) {
        when(userRepository.isUserExist(1L)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class, executable);
        assertEquals(String.format(USER_ID_NOT_FOUND, 1L), exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(userRepository, times(1)).isUserExist(1L);
    }

    private void testThrowUserProfileBlocked(Executable executable) {
        when(userRepository.isUserExist(1L)).thenReturn(true);
        when(authenticationService.getAuthenticatedUserId()).thenReturn(TestConstants.USER_ID);
        when(blockUserRepository.isUserBlocked(1L, TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class, executable);
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    private void testThrowUserHavePrivateProfile(Executable executable) {
        when(userRepository.isUserExist(1L)).thenReturn(true);
        when(authenticationService.getAuthenticatedUserId()).thenReturn(TestConstants.USER_ID);
        when(blockUserRepository.isUserBlocked(1L, TestConstants.USER_ID)).thenReturn(false);
        when(userRepository.isUserHavePrivateProfile(1L, TestConstants.USER_ID)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class, executable);
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
