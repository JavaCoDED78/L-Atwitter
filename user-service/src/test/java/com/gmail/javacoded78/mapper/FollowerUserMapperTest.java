package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.repository.projection.UserProjection;
import com.gmail.javacoded78.service.FollowerUserService;
import com.gmail.javacoded78.service.UserServiceTestHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

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
}
