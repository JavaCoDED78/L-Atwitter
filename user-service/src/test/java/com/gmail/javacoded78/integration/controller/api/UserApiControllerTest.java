package com.gmail.javacoded78.integration.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.API_V1_USER;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_PARTICIPANT_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_VALID_IDS;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOWERS_IDS;
import static com.gmail.javacoded78.constants.PathConstants.IDS;
import static com.gmail.javacoded78.constants.PathConstants.IS_BLOCKED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.IS_EXISTS_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.IS_FOLLOWED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.IS_MY_PROFILE_BLOCKED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.IS_PRIVATE_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.IS_USER_BLOCKED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.LIKE_COUNT;
import static com.gmail.javacoded78.constants.PathConstants.LIST_OWNER_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.LIST_PARTICIPANTS;
import static com.gmail.javacoded78.constants.PathConstants.LIST_PARTICIPANTS_USERNAME;
import static com.gmail.javacoded78.constants.PathConstants.MEDIA_COUNT;
import static com.gmail.javacoded78.constants.PathConstants.MENTION_RESET;
import static com.gmail.javacoded78.constants.PathConstants.MENTION_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.NOTIFICATION_RESET;
import static com.gmail.javacoded78.constants.PathConstants.NOTIFICATION_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.NOTIFICATION_USER_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_USERNAME;
import static com.gmail.javacoded78.constants.PathConstants.SUBSCRIBERS;
import static com.gmail.javacoded78.constants.PathConstants.SUBSCRIBERS_IDS;
import static com.gmail.javacoded78.constants.PathConstants.SUBSCRIBERS_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.TAGGED_IMAGE;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ADDITIONAL_INFO_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_AUTHOR_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_COUNT;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_PINNED_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_PINNED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_VALID_IDS;
import static com.gmail.javacoded78.constants.PathConstants.USER_ID_USERNAME;
import static com.gmail.javacoded78.constants.PathConstants.VALID_IDS;
import static com.gmail.javacoded78.util.TestConstants.USERNAME;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public class UserApiControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    private final ObjectMapper mapper;

    @Test
    @DisplayName("[200] GET /api/v1/user/followers/ids - Get user followers ids")
    void getUserFollowersIds() throws Exception {
        mockMvc.perform(get(API_V1_USER + FOLLOWERS_IDS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(List.of(1, 4, 2))));
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/search/Androsor - Search Users By Username")
    public void searchUsersByUsername() throws Exception {
        mockMvc.perform(get(API_V1_USER + SEARCH_USERNAME, TestConstants.USERNAME)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.items[*]", hasSize(6)),
                        jsonPath("$.items[0].fullName").value(TestConstants.USERNAME),
                        jsonPath("$.items[0].username").value(TestConstants.USERNAME),
                        jsonPath("$.items[0].about").value(TestConstants.ABOUT),
                        jsonPath("$.items[0].avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$.items[0].isPrivateProfile").isNotEmpty(),
                        jsonPath("$.items[0].isMutedDirectMessages").isNotEmpty(),
                        jsonPath("$.items[0].isUserBlocked").isNotEmpty(),
                        jsonPath("$.items[0].isMyProfileBlocked").isNotEmpty(),
                        jsonPath("$.items[0].isWaitingForApprove").isNotEmpty(),
                        jsonPath("$.items[0].isFollower").isNotEmpty(),
                        jsonPath("$.items[0].isUserChatParticipant").isNotEmpty()
                );
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/subscribers/2 - Get subscribers by user id")
    void getSubscribersByUserId() throws Exception {
        mockMvc.perform(get(API_V1_USER + SUBSCRIBERS_USER_ID, TestConstants.USER_ID)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(List.of(1))));
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/is_followed/1 - Get is user follow by other user")
    void isUserFollowByOtherUser() throws Exception {
        mockMvc.perform(get(API_V1_USER + IS_FOLLOWED_USER_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/is_private/1 - Get is user have private profile")
    void isUserHavePrivateProfile() throws Exception {
        mockMvc.perform(get(API_V1_USER + IS_PRIVATE_USER_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/is_blocked/2/1 - Get is user blocked")
    void isUserBlocked() throws Exception {
        mockMvc.perform(get(API_V1_USER + IS_BLOCKED_USER_ID, 2, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/is_user_blocked/1 - Get is user blocked by my profile")
    void isUserBlockedByMyProfile() throws Exception {
        mockMvc.perform(get(API_V1_USER + IS_USER_BLOCKED_USER_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/is_my_profile_blocked/1 - Get is my profile blocked by user")
    void isMyProfileBlockedByUser() throws Exception {
        mockMvc.perform(get(API_V1_USER + IS_MY_PROFILE_BLOCKED_USER_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/notification/2 - Increase notifications count")
    void increaseNotificationsCount() throws Exception {
        mockMvc.perform(get(API_V1_USER + NOTIFICATION_USER_ID, 2)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/mention/2 - Increase mentions count")
    void increaseMentionsCount() throws Exception {
        mockMvc.perform(get(API_V1_USER + MENTION_USER_ID, 2)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] PUT /api/v1/user/like/count/true - Update like count")
    void updateLikeCount() throws Exception {
        mockMvc.perform(put(API_V1_USER + LIKE_COUNT, true)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] PUT /api/v1/user/tweet/count/true - Update tweet count")
    void updateTweetCount() throws Exception {
        mockMvc.perform(put(API_V1_USER + TWEET_COUNT, true)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] PUT /api/v1/user/media/count/true - Update media tweet count")
    void updateMediaTweetCount() throws Exception {
        mockMvc.perform(put(API_V1_USER + MEDIA_COUNT, true)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/list/owner/2 - Get list owner by id")
    void getListOwnerById() throws Exception {
        mockMvc.perform(get(API_V1_USER + LIST_OWNER_USER_ID, 2)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(TestConstants.USER_ID),
                        jsonPath("$.fullName").value(TestConstants.USERNAME),
                        jsonPath("$.username").value(TestConstants.USERNAME),
                        jsonPath("$.avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$.isPrivateProfile").value(false)
                );
    }

    @Test
    @DisplayName("[200] POST /api/v1/user/list/participants - Get list participants by ids")
    void getListParticipantsByIds() throws Exception {
        mockMvc.perform(post(API_V1_USER + LIST_PARTICIPANTS)
                        .content(mapper.writeValueAsString(new IdsRequest(List.of(2L, 3L))))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].fullName").value(TestConstants.USERNAME))
                .andExpect(jsonPath("$[0].username").value(TestConstants.USERNAME))
                .andExpect(jsonPath("$[0].about").value(TestConstants.ABOUT))
                .andExpect(jsonPath("$[0].avatar").value(TestConstants.AVATAR_SRC_1))
                .andExpect(jsonPath("$[0].isMemberInList").value(false))
                .andExpect(jsonPath("$[0].isPrivateProfile").value(false));
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/list/participants/Androsor - Search list members by username")
    void searchListMembersByUsername() throws Exception {
        mockMvc.perform(get(API_V1_USER + LIST_PARTICIPANTS_USERNAME, USERNAME)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$[*]", hasSize(2)),
                        jsonPath("$[0].fullName").value(TestConstants.USERNAME),
                        jsonPath("$[0].username").value(TestConstants.USERNAME),
                        jsonPath("$[0].about").value(TestConstants.ABOUT),
                        jsonPath("$[0].avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$[0].isMemberInList").value(false),
                        jsonPath("$[0].isPrivateProfile").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/notification/user/2 - Get Notification User")
    void getNotificationUser() throws Exception {
        mockMvc.perform(get(API_V1_USER + NOTIFICATION_USER_USER_ID, TestConstants.USER_ID)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(TestConstants.USER_ID),
                        jsonPath("$.username").value(TestConstants.USERNAME),
                        jsonPath("$.avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$.isFollower").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/tweet/author/2 - Get tweet author")
    void getTweetAuthor() throws Exception {
        mockMvc.perform(get(API_V1_USER + TWEET_AUTHOR_USER_ID, TestConstants.USER_ID)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(TestConstants.USER_ID),
                        jsonPath("$.email").value(TestConstants.USER_EMAIL),
                        jsonPath("$.fullName").value(TestConstants.USERNAME),
                        jsonPath("$.username").value(TestConstants.USERNAME),
                        jsonPath("$.avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$.isPrivateProfile").value(false),
                        jsonPath("$.isFollower").value(false),
                        jsonPath("$.isMyProfileBlocked").value(false),
                        jsonPath("$.isUserBlocked").value(false),
                        jsonPath("$.isUserMuted").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/tweet/additional/info/2 - Get tweet additional info user")
    void getTweetAdditionalInfoUser() throws Exception {
        mockMvc.perform(get(API_V1_USER + TWEET_ADDITIONAL_INFO_USER_ID, TestConstants.USER_ID)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(TestConstants.USER_ID),
                        jsonPath("$.fullName").value(TestConstants.USERNAME),
                        jsonPath("$.username").value(TestConstants.USERNAME),
                        jsonPath("$.isFollower").value(false),
                        jsonPath("$.isMyProfileBlocked").value(false),
                        jsonPath("$.isUserBlocked").value(false),
                        jsonPath("$.isUserMuted").value(false)
                );
    }

    @Test
    @DisplayName("[200] POST /api/v1/user/ids - Get tweet users by ids")
    void getTweetLikedUsersByIds() throws Exception {
        mockMvc.perform(post(API_V1_USER + IDS)
                        .content(mapper.writeValueAsString(new IdsRequest(List.of(2L, 3L))))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$[*]", hasSize(2)),
                        jsonPath("$.items[0].id").value(TestConstants.USER_ID),
                        jsonPath("$.items[0].fullName").value(TestConstants.USERNAME),
                        jsonPath("$.items[0].username").value(TestConstants.USERNAME),
                        jsonPath("$.items[0].about").value(TestConstants.ABOUT),
                        jsonPath("$.items[0].avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$.items[0].isPrivateProfile").value(false),
                        jsonPath("$.items[0].isMutedDirectMessages").value(true),
                        jsonPath("$.items[0].isUserBlocked").value(false),
                        jsonPath("$.items[0].isMyProfileBlocked").value(false),
                        jsonPath("$.items[0].isWaitingForApprove").value(false),
                        jsonPath("$.items[0].isFollower").value(false)
                );
    }

    @Test
    @DisplayName("[200] POST /api/v1/user/tagged/image - Get tagged image users")
    void getTaggedImageUsers() throws Exception {
        mockMvc.perform(post(API_V1_USER + TAGGED_IMAGE)
                        .content(mapper.writeValueAsString(new IdsRequest(List.of(2L, 3L))))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(TestConstants.USER_ID))
                .andExpect(jsonPath("$[0].fullName").value(TestConstants.USERNAME));
    }

    @Test
    @DisplayName("[200] PUT /api/v1/user/tweet/pinned/99 - Update pinned tweet id")
    void updatePinnedTweetId() throws Exception {
        mockMvc.perform(put(API_V1_USER + TWEET_PINNED_TWEET_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/tweet/pinned/2 - Get user pinned tweet id")
    public void getUserPinnedTweetId() throws Exception {
        mockMvc.perform(get(API_V1_USER + TWEET_PINNED_USER_ID, 2)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(40)));
    }

    @Test
    @DisplayName("[200] POST /api/v1/user/tweet/valid/ids/MrCat - Get valid tweet user ids")
    void getValidTweetUserIds() throws Exception {
        mockMvc.perform(post(API_V1_USER + TWEET_VALID_IDS, USERNAME)
                        .content(mapper.writeValueAsString(new IdsRequest(List.of(2L, 3L))))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(List.of(2, 4, 6, 7))));
    }

    @Test
    @DisplayName("[200] POST /api/v1/user/valid/ids - Get valid ids")
    void getValidUserIds() throws Exception {
        mockMvc.perform(post(API_V1_USER + VALID_IDS)
                        .content(mapper.writeValueAsString(new IdsRequest(List.of(2L, 3L))))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(List.of(2))));
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/chat/participant/3 - Get chat participant")
    void getChatParticipant() throws Exception {
        mockMvc.perform(get(API_V1_USER + CHAT_PARTICIPANT_USER_ID, 3)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(3),
                        jsonPath("$.fullName").value(TestConstants.USERNAME),
                        jsonPath("$.username").value(TestConstants.USERNAME),
                        jsonPath("$.avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$.isMutedDirectMessages").value(true),
                        jsonPath("$.isUserBlocked").value(false),
                        jsonPath("$.isMyProfileBlocked").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/is_exists/1 - Is user exists")
    void isUserExists() throws Exception {
        mockMvc.perform(get(API_V1_USER + IS_EXISTS_USER_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/2 - Get user by id")
    void getUserById() throws Exception {
        mockMvc.perform(get(API_V1_USER + "/2")
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(TestConstants.USER_ID),
                        jsonPath("$.fullName").value(TestConstants.USERNAME),
                        jsonPath("$.username").value(TestConstants.USERNAME),
                        jsonPath("$.about").value(TestConstants.ABOUT),
                        jsonPath("$.avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$.isPrivateProfile").value(false),
                        jsonPath("$.isMutedDirectMessages").value(true),
                        jsonPath("$.isUserBlocked").value(false),
                        jsonPath("$.isMyProfileBlocked").value(false),
                        jsonPath("$.isWaitingForApprove").value(false),
                        jsonPath("$.isFollower").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/id/@Andrei Soroka - Get user id by username")
    void getUserIdByUsername() throws Exception {
        mockMvc.perform(get(API_V1_USER + USER_ID_USERNAME, "@Andrei Soroka")
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1L));
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/chat/2 - Get chat tweet user")
    void getChatTweetUser() throws Exception {
        mockMvc.perform(get(API_V1_USER + CHAT_USER_ID, TestConstants.USER_ID)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TestConstants.USER_ID))
                .andExpect(jsonPath("$.fullName").value(TestConstants.USERNAME))
                .andExpect(jsonPath("$.username").value(TestConstants.USERNAME))
                .andExpect(jsonPath("$.avatar").value(TestConstants.AVATAR_SRC_1));
    }

    @Test
    @DisplayName("[200] POST /api/v1/user/chat/valid/ids - Validate chat users ids")
    void validateChatUsersIds() throws Exception {
        mockMvc.perform(post(API_V1_USER + CHAT_VALID_IDS)
                        .content(mapper.writeValueAsString(new IdsRequest(List.of(2L, 3L))))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(List.of(2, 3))));
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/subscribers - Get users which user subscribed")
    void getUsersWhichUserSubscribed() throws Exception {
        mockMvc.perform(get(API_V1_USER + SUBSCRIBERS)
                        .header(AUTH_USER_ID_HEADER, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(TestConstants.USER_ID))
                .andExpect(jsonPath("$[0].username").value(TestConstants.USERNAME))
                .andExpect(jsonPath("$[0].avatar").value(TestConstants.AVATAR_SRC_1))
                .andExpect(jsonPath("$[0].isFollower").value(false));
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/subscribers/ids - Get user id which user subscribed")
    void getUserIdsWhichUserSubscribed() throws Exception {
        mockMvc.perform(get(API_V1_USER + SUBSCRIBERS_IDS)
                        .header(AUTH_USER_ID_HEADER, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(List.of(2))));
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/notification/reset - Reset notification count")
    void resetNotificationCount() throws Exception {
        mockMvc.perform(get(API_V1_USER + NOTIFICATION_RESET)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] GET /api/v1/user/mention/reset - Reset mention count")
    void resetMentionCount() throws Exception {
        mockMvc.perform(get(API_V1_USER + MENTION_RESET)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk());
    }
}
