package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.repository.projection.UserProfileProjection;
import com.gmail.javacoded78.repository.projection.UserProjection;
import com.gmail.javacoded78.service.UserService;
import com.gmail.javacoded78.service.UserServiceTestHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class UserMapperTest extends AbstractAuthTest {

    private final UserMapper userMapper;

    @MockBean
    private final UserService userService;

    @Test
    void getUserById() {
        UserProfileProjection userProfileProjection = UserServiceTestHelper.createUserProfileProjection();
        when(userService.getUserById(TestConstants.USER_ID)).thenReturn(userProfileProjection);
        userMapper.getUserById(TestConstants.USER_ID);
        verify(userService, times(1)).getUserById(TestConstants.USER_ID);
    }

    @Test
    void getUsers() {
        Page<UserProjection> userProjections = UserServiceTestHelper.createUserProjections();
        when(userService.getUsers(pageable)).thenReturn(userProjections);
        userMapper.getUsers(pageable);
        verify(userService, times(1)).getUsers(pageable);
    }

    @Test
    void getRelevantUsers() {
        Page<UserProjection> userProjections = UserServiceTestHelper.createUserProjections();
        when(userService.getRelevantUsers()).thenReturn(userProjections.getContent());
        userMapper.getRelevantUsers();
        verify(userService, times(1)).getRelevantUsers();
    }
}
