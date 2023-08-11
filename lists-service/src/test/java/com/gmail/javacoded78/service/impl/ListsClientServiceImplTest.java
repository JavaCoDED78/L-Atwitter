package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.response.notification.NotificationListResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetListResponse;
import com.gmail.javacoded78.dto.response.user.CommonUserResponse;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.mapper.BasicMapper;
import com.gmail.javacoded78.repository.ListsRepository;
import com.gmail.javacoded78.repository.projection.NotificationListProjection;
import com.gmail.javacoded78.repository.projection.TweetListProjection;
import com.gmail.javacoded78.service.ListsClientService;
import com.gmail.javacoded78.service.util.ListsServiceTestHelper;
import com.gmail.javacoded78.util.TestConstants;
import com.gmail.javacoded78.util.TestUtil;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.Optional;

import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ListsClientServiceImplTest {

    @Autowired
    private ListsClientService listsClientService;

    @MockBean
    private ListsRepository listsRepository;

    @MockBean
    private UserClient userClient;

    @MockBean
    private BasicMapper basicMapper;

    private static final ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    @Before
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    public void getNotificationList() {
        NotificationListProjection notificationList = factory.createProjection(
                NotificationListProjection.class,
                Map.of("id", 1L, "name", TestConstants.LIST_NAME));
        NotificationListResponse listResponse = new NotificationListResponse();
        listResponse.setId(1L);
        listResponse.setName(TestConstants.LIST_NAME);
        when(listsRepository.getListById(TestConstants.LIST_ID, NotificationListProjection.class)).thenReturn(notificationList);
        when(basicMapper.convertToResponse(notificationList, NotificationListResponse.class)).thenReturn(listResponse);
        assertEquals(listResponse, listsClientService.getNotificationList(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, NotificationListProjection.class);
        verify(basicMapper, times(1)).convertToResponse(notificationList, NotificationListResponse.class);
    }

    @Test
    public void getTweetList() {
        TweetListResponse tweetListResponse = new TweetListResponse();
        tweetListResponse.setId(TestConstants.LIST_ID);
        tweetListResponse.setName(TestConstants.LIST_NAME);
        tweetListResponse.setAltWallpaper(TestConstants.LIST_ALT_WALLPAPER);
        tweetListResponse.setWallpaper("");
        tweetListResponse.setListOwner(new CommonUserResponse());
        tweetListResponse.setPrivate(false);
        tweetListResponse.setMembersSize(1L);
        TweetListProjection tweetListProjection = ListsServiceTestHelper.mockTweetListProjection(TestConstants.LIST_USER_ID);
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class)).thenReturn(Optional.of(tweetListProjection));
        when(userClient.isUserBlocked(tweetListProjection.getListOwnerId(), USER_ID)).thenReturn(false);
        when(userClient.isUserHavePrivateProfile(tweetListProjection.getListOwnerId())).thenReturn(false);
        when(basicMapper.convertToResponse(tweetListProjection, TweetListResponse.class)).thenReturn(tweetListResponse);
        assertEquals(tweetListResponse, listsClientService.getTweetList(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class);
        verify(userClient, times(1)).isUserBlocked(tweetListProjection.getListOwnerId(), USER_ID);
        verify(basicMapper, times(1)).convertToResponse(tweetListProjection, TweetListResponse.class);
    }

    @Test
    public void getTweetList_shouldReturnEmptyTweetListResponse() {
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class)).thenReturn(Optional.empty());
        assertEquals(new TweetListResponse(), listsClientService.getTweetList(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class);
    }

    @Test
    public void getTweetList_shouldUserBlockAndReturnEmptyTweetListResponse() {
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class))
                .thenReturn(Optional.of(ListsServiceTestHelper.mockTweetListProjection(TestConstants.LIST_USER_ID)));
        when(userClient.isUserBlocked(TestConstants.LIST_USER_ID, USER_ID)).thenReturn(true);
        assertEquals(new TweetListResponse(), listsClientService.getTweetList(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class);
        verify(userClient, times(1)).isUserBlocked(TestConstants.LIST_USER_ID, USER_ID);
    }

    @Test
    public void getTweetList_shouldUserHavePrivateProfileAndReturnEmptyTweetListResponse() {
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class))
                .thenReturn(Optional.of(ListsServiceTestHelper.mockTweetListProjection(1L)));
        when(userClient.isUserBlocked(1L, USER_ID)).thenReturn(false);
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
        assertEquals(new TweetListResponse(), listsClientService.getTweetList(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class);
        verify(userClient, times(1)).isUserBlocked(1L, USER_ID);
        verify(userClient, times(1)).isUserHavePrivateProfile(1L);
    }
}