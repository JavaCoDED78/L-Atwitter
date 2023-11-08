package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.repository.projection.BaseUserProjection;
import com.gmail.javacoded78.repository.projection.UserProfileProjection;
import com.gmail.javacoded78.repository.projection.UserProjection;
import com.gmail.javacoded78.service.FollowerUserService;
import com.gmail.javacoded78.service.UserServiceTestHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class FollowerUserMapperTest extends AbstractAuthTest {

    private final FollowerUserMapper followerUserMapper;

    @MockBean
    private final FollowerUserService followerUserService;

    @Test
    void getFollowers() {
        Page<UserProjection> userProjections = UserServiceTestHelper.createUserProjections();
        when(followerUserService.getFollowers(TestConstants.USER_ID, pageable)).thenReturn(userProjections);
        followerUserMapper.getFollowers(TestConstants.USER_ID, pageable);
        verify(followerUserService, times(1)).getFollowers(TestConstants.USER_ID, pageable);
    }

    @Test
    void getFollowing() {
        Page<UserProjection> userProjections = UserServiceTestHelper.createUserProjections();
        when(followerUserService.getFollowing(TestConstants.USER_ID, pageable)).thenReturn(userProjections);
        followerUserMapper.getFollowing(TestConstants.USER_ID, pageable);
        verify(followerUserService, times(1)).getFollowing(TestConstants.USER_ID, pageable);
    }

    @Test
    void processFollow() {
        when(followerUserService.processFollow(TestConstants.USER_ID)).thenReturn(true);
        assertTrue(followerUserMapper.processFollow(TestConstants.USER_ID));
        verify(followerUserService, times(1)).processFollow(TestConstants.USER_ID);
    }

    @Test
    void overallFollowers() {
        List<BaseUserProjection> baseUserProjections = UserServiceTestHelper.createBaseUserProjections();
        when(followerUserService.overallFollowers(TestConstants.USER_ID)).thenReturn(baseUserProjections);
        followerUserMapper.overallFollowers(TestConstants.USER_ID);
        verify(followerUserService, times(1)).overallFollowers(TestConstants.USER_ID);
    }

    @Test
    void processFollowRequestToPrivateProfile() {
        UserProfileProjection userProfileProjection = UserServiceTestHelper.createUserProfileProjection();
        when(followerUserService.processFollowRequestToPrivateProfile(TestConstants.USER_ID)).thenReturn(userProfileProjection);
        followerUserMapper.processFollowRequestToPrivateProfile(TestConstants.USER_ID);
        verify(followerUserService, times(1)).processFollowRequestToPrivateProfile(TestConstants.USER_ID);
    }

    @Test
    void acceptFollowRequest() {
        String message = String.format("User (id:%s) accepted.", TestConstants.USER_ID);
        when(followerUserService.acceptFollowRequest(TestConstants.USER_ID)).thenReturn(message);
        assertEquals(message, followerUserMapper.acceptFollowRequest(TestConstants.USER_ID));
        verify(followerUserService, times(1)).acceptFollowRequest(TestConstants.USER_ID);
    }

    @Test
    void declineFollowRequest() {
        String message = String.format("User (id:%s) declined.", TestConstants.USER_ID);
        when(followerUserService.declineFollowRequest(TestConstants.USER_ID)).thenReturn(message);
        assertEquals(message, followerUserMapper.declineFollowRequest(TestConstants.USER_ID));
        verify(followerUserService, times(1)).declineFollowRequest(TestConstants.USER_ID);
    }
}
