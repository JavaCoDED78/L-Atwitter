package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.chat.ChatUserParticipantResponse;
import com.gmail.javacoded78.dto.response.lists.ListMemberResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationUserResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetAdditionalInfoUserResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetAuthorResponse;
import com.gmail.javacoded78.dto.response.user.CommonUserResponse;
import com.gmail.javacoded78.dto.response.user.TaggedUserResponse;
import com.gmail.javacoded78.dto.response.user.UserChatResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.mapper.BasicMapper;
import com.gmail.javacoded78.repository.BlockUserRepository;
import com.gmail.javacoded78.repository.FollowerUserRepository;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.repository.projection.ChatUserParticipantProjection;
import com.gmail.javacoded78.repository.projection.CommonUserProjection;
import com.gmail.javacoded78.repository.projection.ListMemberProjection;
import com.gmail.javacoded78.repository.projection.NotificationUserProjection;
import com.gmail.javacoded78.repository.projection.TaggedUserProjection;
import com.gmail.javacoded78.repository.projection.TweetAdditionalInfoUserProjection;
import com.gmail.javacoded78.repository.projection.TweetAuthorProjection;
import com.gmail.javacoded78.repository.projection.UserChatProjection;
import com.gmail.javacoded78.repository.projection.UserProjection;
import com.gmail.javacoded78.service.AuthenticationService;
import com.gmail.javacoded78.service.UserServiceTestHelper;
import com.gmail.javacoded78.service.util.UserServiceHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
class UserClientServiceImplTest extends AbstractAuthTest {

    private final UserClientServiceImpl userClientService;

    @MockBean
    private final UserRepository userRepository;

    @MockBean
    private final FollowerUserRepository followerUserRepository;

    @MockBean
    private final BlockUserRepository blockUserRepository;

    @MockBean
    private final BasicMapper basicMapper;

    @MockBean
    private final AuthenticationService authenticationService;

    @MockBean
    private final UserServiceHelper userServiceHelper;

    @BeforeEach
    public void setUp() {
        super.setUp();
        when(authenticationService.getAuthenticatedUserId()).thenReturn(TestConstants.USER_ID);
    }

    @Test
    void getUserFollowersIds_ShouldReturnUserIds() {
        List<Long> ids = new ArrayList<>(List.of(1L, 2L, 3L));
        when(followerUserRepository.getUserFollowersIds(TestConstants.USER_ID)).thenReturn(ids);
        assertEquals(4, userClientService.getUserFollowersIds().size());
        verify(authenticationService, times(1)).getAuthenticatedUserId();
        verify(followerUserRepository, times(1)).getUserFollowersIds(TestConstants.USER_ID);
    }

    @Test
    void searchUsersByUsername_ShouldReturnUserChatResponse() {
        UserChatProjection userChatProjection = UserServiceTestHelper.createUserChatProjection();
        PageImpl<UserChatProjection> userChatProjections = new PageImpl<>(List.of(userChatProjection), pageable, 20);
        HeaderResponse<UserChatResponse> headerResponse = new HeaderResponse<>(
                List.of(new UserChatResponse(), new UserChatResponse()), new HttpHeaders());
        when(userRepository.searchUsersByUsername("test", pageable, UserChatProjection.class)).thenReturn(userChatProjections);
        when(basicMapper.getHeaderResponse(userChatProjections, UserChatResponse.class)).thenReturn(headerResponse);
        assertEquals(headerResponse, userClientService.searchUsersByUsername("test", pageable));
        verify(userRepository, times(1)).searchUsersByUsername("test", pageable, UserChatProjection.class);
        verify(basicMapper, times(1)).getHeaderResponse(userChatProjections, UserChatResponse.class);
    }

    @Test
    void getSubscribersByUserId() {
        when(userRepository.getSubscribersByUserId(TestConstants.USER_ID)).thenReturn(ids);
        assertEquals(ids, userClientService.getSubscribersByUserId(TestConstants.USER_ID));
        verify(userRepository, times(1)).getSubscribersByUserId(TestConstants.USER_ID);
    }

    @Test
    void isUserFollowByOtherUser() {
        when(userServiceHelper.isUserFollowByOtherUser(TestConstants.USER_ID)).thenReturn(true);
        assertTrue(userClientService.isUserFollowByOtherUser(TestConstants.USER_ID));
        verify(userServiceHelper, times(1)).isUserFollowByOtherUser(TestConstants.USER_ID);
    }

    @Test
    void isUserHavePrivateProfile() {
        when(userServiceHelper.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(true);
        assertTrue(userClientService.isUserHavePrivateProfile(TestConstants.USER_ID));
        verify(userServiceHelper, times(1)).isUserHavePrivateProfile(TestConstants.USER_ID);
    }

    @Test
    void isUserBlocked() {
        when(blockUserRepository.isUserBlocked(TestConstants.USER_ID, 1L)).thenReturn(true);
        assertTrue(userClientService.isUserBlocked(TestConstants.USER_ID, 1L));
        verify(blockUserRepository, times(1)).isUserBlocked(TestConstants.USER_ID, 1L);
    }

    @Test
    void isUserBlockedByMyProfile() {
        when(userServiceHelper.isUserBlockedByMyProfile(TestConstants.USER_ID)).thenReturn(true);
        assertTrue(userClientService.isUserBlockedByMyProfile(TestConstants.USER_ID));
        verify(userServiceHelper, times(1)).isUserBlockedByMyProfile(TestConstants.USER_ID);
    }

    @Test
    void isMyProfileBlockedByUser() {
        when(userServiceHelper.isMyProfileBlockedByUser(TestConstants.USER_ID)).thenReturn(true);
        assertTrue(userClientService.isMyProfileBlockedByUser(TestConstants.USER_ID));
        verify(userServiceHelper, times(1)).isMyProfileBlockedByUser(TestConstants.USER_ID);
    }

    @Test
    void increaseNotificationsCount() {
        userClientService.increaseNotificationsCount(TestConstants.USER_ID);
        verify(userRepository, times(1)).increaseNotificationsCount(TestConstants.USER_ID);
    }

    @Test
    void increaseMentionsCount() {
        userClientService.increaseMentionsCount(TestConstants.USER_ID);
        verify(userRepository, times(1)).increaseMentionsCount(TestConstants.USER_ID);
    }

    @Test
    void updateLikeCount() {
        userClientService.updateLikeCount(true);
        verify(userRepository, times(1)).updateLikeCount(true, TestConstants.USER_ID);
    }

    @Test
    void updateTweetCount() {
        userClientService.updateTweetCount(true);
        verify(userRepository, times(1)).updateTweetCount(true, TestConstants.USER_ID);
    }

    @Test
    void updateMediaTweetCount() {
        userClientService.updateMediaTweetCount(true);
        verify(userRepository, times(1)).updateMediaTweetCount(true, TestConstants.USER_ID);
    }

    @Test
    void getListOwnerById() {
        CommonUserResponse commonUserResponse = new CommonUserResponse();
        CommonUserProjection commonUserProjection = UserServiceTestHelper.createCommonUserProjection();
        when(userRepository.getUserById(TestConstants.USER_ID, CommonUserProjection.class)).thenReturn(Optional.of(commonUserProjection));
        when(basicMapper.convertToResponse(commonUserProjection, CommonUserResponse.class)).thenReturn(commonUserResponse);
        assertEquals(commonUserResponse, userClientService.getListOwnerById(TestConstants.USER_ID));
        verify(userRepository, times(1)).getUserById(TestConstants.USER_ID, CommonUserProjection.class);
        verify(basicMapper, times(1)).convertToResponse(commonUserProjection, CommonUserResponse.class);
    }

    @Test
    void getListParticipantsByIds() {
        List<ListMemberResponse> listMemberResponses = List.of(new ListMemberResponse(), new ListMemberResponse());
        List<ListMemberProjection> listMemberProjections = UserServiceTestHelper.createListMemberProjections();
        when(userRepository.getUsersByIds(ids, ListMemberProjection.class)).thenReturn(listMemberProjections);
        when(basicMapper.convertToResponseList(listMemberProjections, ListMemberResponse.class)).thenReturn(listMemberResponses);
        assertEquals(listMemberResponses, userClientService.getListParticipantsByIds(new IdsRequest(ids)));
        verify(userRepository, times(1)).getUsersByIds(ids, ListMemberProjection.class);
        verify(basicMapper, times(1)).convertToResponseList(listMemberProjections, ListMemberResponse.class);
    }

    @Test
    void searchListMembersByUsername() {
        List<ListMemberResponse> listMemberResponses = List.of(new ListMemberResponse(), new ListMemberResponse());
        List<ListMemberProjection> listMemberProjections = UserServiceTestHelper.createListMemberProjections();
        when(userRepository.searchListMembersByUsername("test")).thenReturn(listMemberProjections);
        when(basicMapper.convertToResponseList(listMemberProjections, ListMemberResponse.class)).thenReturn(listMemberResponses);
        assertEquals(listMemberResponses, userClientService.searchListMembersByUsername("test"));
        verify(userRepository, times(1)).searchListMembersByUsername("test");
        verify(basicMapper, times(1)).convertToResponseList(listMemberProjections, ListMemberResponse.class);
    }

    @Test
    void getNotificationUser() {
        NotificationUserResponse listMemberResponse = new NotificationUserResponse();
        NotificationUserProjection userProjection = UserServiceTestHelper.createNotificationUserProjection();
        when(userRepository.getUserById(TestConstants.USER_ID, NotificationUserProjection.class)).thenReturn(Optional.of(userProjection));
        when(basicMapper.convertToResponse(userProjection, NotificationUserResponse.class)).thenReturn(listMemberResponse);
        assertEquals(listMemberResponse, userClientService.getNotificationUser(TestConstants.USER_ID));
        verify(userRepository, times(1)).getUserById(TestConstants.USER_ID, NotificationUserProjection.class);
        verify(basicMapper, times(1)).convertToResponse(userProjection, NotificationUserResponse.class);
    }

    @Test
    void getTweetAuthor() {
        TweetAuthorResponse tweetAuthorResponse = new TweetAuthorResponse();
        TweetAuthorProjection tweetAuthorProjection = UserServiceTestHelper.createTweetAuthorProjection();
        when(userRepository.getUserById(TestConstants.USER_ID, TweetAuthorProjection.class)).thenReturn(Optional.of(tweetAuthorProjection));
        when(basicMapper.convertToResponse(tweetAuthorProjection, TweetAuthorResponse.class)).thenReturn(tweetAuthorResponse);
        assertEquals(tweetAuthorResponse, userClientService.getTweetAuthor(TestConstants.USER_ID));
        verify(userRepository, times(1)).getUserById(TestConstants.USER_ID, TweetAuthorProjection.class);
        verify(basicMapper, times(1)).convertToResponse(tweetAuthorProjection, TweetAuthorResponse.class);
    }

    @Test
    public void getTweetAdditionalInfoUser() {
        TweetAdditionalInfoUserResponse infoUserResponse = new TweetAdditionalInfoUserResponse();
        TweetAdditionalInfoUserProjection tweetAdditionalInfoUserProjection = UserServiceTestHelper.createTweetAdditionalInfoUserProjection();
        when(userRepository.getUserById(TestConstants.USER_ID, TweetAdditionalInfoUserProjection.class))
                .thenReturn(Optional.of(tweetAdditionalInfoUserProjection));
        when(basicMapper.convertToResponse(tweetAdditionalInfoUserProjection, TweetAdditionalInfoUserResponse.class))
                .thenReturn(infoUserResponse);
        assertEquals(infoUserResponse, userClientService.getTweetAdditionalInfoUser(TestConstants.USER_ID));
        verify(userRepository, times(1)).getUserById(TestConstants.USER_ID, TweetAdditionalInfoUserProjection.class);
        verify(basicMapper, times(1)).convertToResponse(tweetAdditionalInfoUserProjection, TweetAdditionalInfoUserResponse.class);
    }

    @Test
    void getUsersByIds() {
        HeaderResponse<UserResponse> headerResponse = new HeaderResponse<>(
                List.of(new UserResponse(), new UserResponse()), new HttpHeaders());
        Page<UserProjection> userProjections = UserServiceTestHelper.createUserProjections();
        when(userRepository.getUsersByIds(ids, pageable)).thenReturn(userProjections);
        when(basicMapper.getHeaderResponse(userProjections, UserResponse.class)).thenReturn(headerResponse);
        assertEquals(headerResponse, userClientService.getUsersByIds(new IdsRequest(ids), pageable));
        verify(userRepository, times(1)).getUsersByIds(ids, pageable);
        verify(basicMapper, times(1)).getHeaderResponse(userProjections, UserResponse.class);
    }

    @Test
    public void getTaggedImageUsers() {
        List<TaggedUserProjection> taggedUserProjections = UserServiceTestHelper.createTaggedUserProjectionList();
        List<TaggedUserResponse> taggedUserResponses = List.of(new TaggedUserResponse(), new TaggedUserResponse());
        when(userRepository.getTaggedImageUsers(ids)).thenReturn(taggedUserProjections);
        when(basicMapper.convertToResponseList(taggedUserProjections, TaggedUserResponse.class)).thenReturn(taggedUserResponses);
        assertEquals(taggedUserResponses, userClientService.getTaggedImageUsers(new IdsRequest(ids)));
        verify(userRepository, times(1)).getTaggedImageUsers(ids);
        verify(basicMapper, times(1)).convertToResponseList(taggedUserProjections, TaggedUserResponse.class);
    }

    @Test
    void updatePinnedTweetId() {
        when(userRepository.getPinnedTweetId(TestConstants.USER_ID)).thenReturn(TestConstants.TWEET_ID);
        userClientService.updatePinnedTweetId(TestConstants.TWEET_ID);
        verify(userRepository, times(1)).getPinnedTweetId(TestConstants.USER_ID);
        verify(userRepository, times(1)).updatePinnedTweetId(null, TestConstants.USER_ID);
    }

    @Test
    void getUserPinnedTweetId() {
        when(userRepository.getPinnedTweetId(TestConstants.USER_ID)).thenReturn(TestConstants.TWEET_ID);
        assertEquals(TestConstants.TWEET_ID, userClientService.getUserPinnedTweetId(TestConstants.USER_ID));
        verify(userRepository, times(1)).getPinnedTweetId(TestConstants.USER_ID);
    }

    @Test
    void getValidTweetUserIds() {
        when(userRepository.getValidUserIdsByIds(ids, TestConstants.USER_ID)).thenReturn(ids);
        when(userRepository.getValidUserIdsByName("test", ids)).thenReturn(ids);
        assertEquals(ids, userClientService.getValidTweetUserIds(new IdsRequest(ids), "test"));
        verify(userRepository, times(1)).getValidUserIdsByIds(ids, TestConstants.USER_ID);
        verify(userRepository, times(1)).getValidUserIdsByName("test", ids);
    }

    @Test
    void getValidUserIds() {
        List<Long> ids = new ArrayList<>(List.of(1L, 2L, 3L));
        when(userRepository.getUserIdsWhoBlockedMyProfile(ids, TestConstants.USER_ID)).thenReturn(ids);
        when(userRepository.getValidUserIdsByIds(ids, TestConstants.USER_ID)).thenReturn(new ArrayList<>());
        assertEquals(0, userClientService.getValidUserIds(new IdsRequest(ids)).size());
        verify(userRepository, times(1)).getUserIdsWhoBlockedMyProfile(ids, TestConstants.USER_ID);
        verify(userRepository, times(1)).getValidUserIdsByIds(ids, TestConstants.USER_ID);
    }

    @Test
    void getChatParticipant() {
        ChatUserParticipantProjection chatUserParticipantProjection = UserServiceTestHelper.createChatUserParticipantProjection();
        ChatUserParticipantResponse chatUserParticipantResponse = new ChatUserParticipantResponse();
        when(userRepository.getUserById(TestConstants.USER_ID, ChatUserParticipantProjection.class))
                .thenReturn(Optional.of(chatUserParticipantProjection));
        when(basicMapper.convertToResponse(chatUserParticipantProjection, ChatUserParticipantResponse.class))
                .thenReturn(chatUserParticipantResponse);
        assertEquals(chatUserParticipantResponse, userClientService.getChatParticipant(TestConstants.USER_ID));
        verify(userRepository, times(1)).getUserById(TestConstants.USER_ID, ChatUserParticipantProjection.class);
        verify(basicMapper, times(1)).convertToResponse(chatUserParticipantProjection, ChatUserParticipantResponse.class);
    }

    @Test
    void isUserExists() {
        when(userRepository.isUserExists(TestConstants.USER_ID)).thenReturn(true);
        assertTrue(userClientService.isUserExists(TestConstants.USER_ID));
        verify(userRepository, times(1)).isUserExists(TestConstants.USER_ID);
    }
}
