package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.response.notification.NotificationListResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetListResponse;
import com.gmail.javacoded78.dto.response.user.CommonUserResponse;
import com.gmail.javacoded78.mapper.BasicMapper;
import com.gmail.javacoded78.repository.ListsRepository;
import com.gmail.javacoded78.repository.projection.NotificationListProjection;
import com.gmail.javacoded78.repository.projection.TweetListProjection;
import com.gmail.javacoded78.service.ListsClientService;
import com.gmail.javacoded78.service.UserService;
import com.gmail.javacoded78.service.util.ListsServiceTestHelper;
import com.gmail.javacoded78.util.TestConstants;
import com.gmail.javacoded78.util.TestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.Map;
import java.util.Optional;

import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
public class ListsClientServiceImplTest {

    @Autowired
    private ListsClientService listsClientService;

    @MockBean
    private ListsRepository listsRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private BasicMapper basicMapper;

    private static final ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    @BeforeEach
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @org.junit.Test
    public void getNotificationList() {
        NotificationListProjection notificationList = factory.createProjection(
                NotificationListProjection.class,
                Map.of("id", 1L, "name", TestConstants.LIST_NAME));
        NotificationListResponse listResponse = new NotificationListResponse();
        listResponse.setId(1L);
        listResponse.setListName(TestConstants.LIST_NAME);
        when(listsRepository.getListById(TestConstants.LIST_ID, NotificationListProjection.class)).thenReturn(notificationList);
        when(basicMapper.convertToResponse(notificationList, NotificationListResponse.class)).thenReturn(listResponse);
        assertEquals(listResponse, listsClientService.getNotificationList(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, NotificationListProjection.class);
        verify(basicMapper, times(1)).convertToResponse(notificationList, NotificationListResponse.class);
    }

    @org.junit.Test
    public void getTweetList() {
        TweetListResponse tweetListResponse = new TweetListResponse();
        tweetListResponse.setId(TestConstants.LIST_ID);
        tweetListResponse.setListName(TestConstants.LIST_NAME);
        tweetListResponse.setAltWallpaper(TestConstants.LIST_ALT_WALLPAPER);
        tweetListResponse.setWallpaper("");
        tweetListResponse.setListOwner(new CommonUserResponse());
        tweetListResponse.setPrivate(false);
        tweetListResponse.setMembersSize(1L);
        TweetListProjection tweetListProjection = ListsServiceTestHelper.mockTweetListProjection(TestConstants.LIST_USER_ID);
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class)).thenReturn(Optional.of(tweetListProjection));
        when(userService.isUserBlocked(tweetListProjection.getListOwner().getId(), USER_ID)).thenReturn(false);
        when(userService.isUserHavePrivateProfile(tweetListProjection.getListOwner().getId(), USER_ID)).thenReturn(false);
        when(basicMapper.convertToResponse(tweetListProjection, TweetListResponse.class)).thenReturn(tweetListResponse);
        assertEquals(tweetListResponse, listsClientService.getTweetList(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class);
        verify(userService, times(1)).isUserBlocked(tweetListProjection.getListOwner().getId(), USER_ID);
        verify(basicMapper, times(1)).convertToResponse(tweetListProjection, TweetListResponse.class);
    }

    @org.junit.Test
    public void getTweetList_shouldReturnEmptyTweetListResponse() {
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class)).thenReturn(Optional.empty());
        assertEquals(new TweetListResponse(), listsClientService.getTweetList(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class);
    }

    @org.junit.Test
    public void getTweetList_shouldUserBlockAndReturnEmptyTweetListResponse() {
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class))
                .thenReturn(Optional.of(ListsServiceTestHelper.mockTweetListProjection(TestConstants.LIST_USER_ID)));
        when(userService.isUserBlocked(TestConstants.LIST_USER_ID, USER_ID)).thenReturn(true);
        assertEquals(new TweetListResponse(), listsClientService.getTweetList(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class);
        verify(userService, times(1)).isUserBlocked(TestConstants.LIST_USER_ID, USER_ID);
    }

    @Test
    public void getTweetList_shouldUserHavePrivateProfileAndReturnEmptyTweetListResponse() {
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class))
                .thenReturn(Optional.of(ListsServiceTestHelper.mockTweetListProjection(1L)));
        when(userService.isUserBlocked(1L, USER_ID)).thenReturn(false);
        when(userService.isUserHavePrivateProfile(1L, USER_ID)).thenReturn(true);
        assertEquals(new TweetListResponse(), listsClientService.getTweetList(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class);
        verify(userService, times(1)).isUserBlocked(1L, USER_ID);
        verify(userService, times(1)).isUserHavePrivateProfile(1L, USER_ID);
    }
}