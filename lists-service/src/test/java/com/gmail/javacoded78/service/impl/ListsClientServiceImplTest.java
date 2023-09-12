package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.response.notification.NotificationListResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetListResponse;
import com.gmail.javacoded78.dto.response.user.CommonUserResponse;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.mapper.BasicMapper;
import com.gmail.javacoded78.repository.ListsRepository;
import com.gmail.javacoded78.repository.projection.NotificationListProjection;
import com.gmail.javacoded78.repository.projection.TweetListProjection;
import com.gmail.javacoded78.service.util.ListsServiceTestHelper;
import com.gmail.javacoded78.util.TestConstants;
import com.gmail.javacoded78.util.TestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
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
class ListsClientServiceImplTest {

    @Mock
    private final ListsRepository listsRepository;

    @Mock
    private final UserClient userClient;

    @Mock
    private final BasicMapper basicMapper;

    @InjectMocks
    private final ListsClientServiceImpl listsClientService;

    private static final ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    @BeforeEach
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    void getNotificationList() {
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
    void getTweetList() {
        TweetListResponse tweetListResponse = TweetListResponse.builder()
                .id(TestConstants.LIST_ID)
                .name(TestConstants.LIST_NAME)
                .altWallpaper(TestConstants.LIST_ALT_WALLPAPER)
                .wallpaper("")
                .listOwner(new CommonUserResponse())
                .isPrivate(false)
                .membersSize(1L)
                .build();
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
    void getTweetList_shouldReturnEmptyTweetListResponse() {
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class)).thenReturn(Optional.empty());
        assertEquals(new TweetListResponse(), listsClientService.getTweetList(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class);
    }

    @Test
    void getTweetList_shouldUserBlockAndReturnEmptyTweetListResponse() {
        when(listsRepository.getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class))
                .thenReturn(Optional.of(ListsServiceTestHelper.mockTweetListProjection(TestConstants.LIST_USER_ID)));
        when(userClient.isUserBlocked(TestConstants.LIST_USER_ID, USER_ID)).thenReturn(true);
        assertEquals(new TweetListResponse(), listsClientService.getTweetList(TestConstants.LIST_ID));
        verify(listsRepository, times(1)).getListById(TestConstants.LIST_ID, USER_ID, TweetListProjection.class);
        verify(userClient, times(1)).isUserBlocked(TestConstants.LIST_USER_ID, USER_ID);
    }

    @Test
    void getTweetList_shouldUserHavePrivateProfileAndReturnEmptyTweetListResponse() {
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