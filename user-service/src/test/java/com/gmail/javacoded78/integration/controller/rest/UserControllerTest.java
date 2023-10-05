package com.gmail.javacoded78.integration.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.SearchTermsRequest;
import com.gmail.javacoded78.dto.request.UserRequest;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_USERNAME_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;
import static com.gmail.javacoded78.constants.PathConstants.ALL;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.DETAILS_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.PIN_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.RELEVANT;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_RESULTS;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_TEXT;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_USERNAME;
import static com.gmail.javacoded78.constants.PathConstants.START;
import static com.gmail.javacoded78.constants.PathConstants.SUBSCRIBE_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.TOKEN;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_USER;
import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Test
    @DisplayName("[200] GET /ui/v1/user/token - Get user by token")
    void getUserByToken() throws Exception {
        mockMvc.perform(get(UI_V1_USER + TOKEN)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.user.id").value(TestConstants.USER_ID),
                        jsonPath("$.user.email").value(TestConstants.USER_EMAIL),
                        jsonPath("$.user.fullName").value(TestConstants.FULL_NAME),
                        jsonPath("$.user.username").value(TestConstants.USERNAME),
                        jsonPath("$.user.location").value(TestConstants.LOCATION),
                        jsonPath("$.user.about").value(TestConstants.ABOUT),
                        jsonPath("$.user.website").value(TestConstants.WEBSITE),
                        jsonPath("$.user.birthday").value(TestConstants.BIRTHDAY),
                        jsonPath("$.user.registrationDate").value(TestConstants.REGISTRATION_DATE),
                        jsonPath("$.user.tweetCount").value(TestConstants.TWEET_COUNT),
                        jsonPath("$.user.avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$.user.wallpaper").value(TestConstants.WALLPAPER_SRC),
                        jsonPath("$.user.profileCustomized").value(true),
                        jsonPath("$.user.profileStarted").value(true)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/1 - Get user by id")
    void getUserById() throws Exception {
        mockMvc.perform(get(UI_V1_USER + USER_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(1L),
                        jsonPath("$.fullName").value(TestConstants.USERNAME2),
                        jsonPath("$.username").value(TestConstants.USERNAME2),
                        jsonPath("$.location").value("Kyiv"),
                        jsonPath("$.about").value(TestConstants.ABOUT2),
                        jsonPath("$.website").value(TestConstants.WEBSITE),
                        jsonPath("$.country").value(TestConstants.COUNTRY),
                        jsonPath("$.birthday").isEmpty(),
                        jsonPath("$.registrationDate").value(TestConstants.REGISTRATION_DATE),
                        jsonPath("$.tweetCount").value(TestConstants.TWEET_COUNT),
                        jsonPath("$.mediaTweetCount").value(TestConstants.MEDIA_TWEET_COUNT),
                        jsonPath("$.likeCount").value(TestConstants.LIKE_TWEET_COUNT),
                        jsonPath("$.avatar").value(TestConstants.AVATAR_SRC_2),
                        jsonPath("$.pinnedTweetId").isEmpty(),
                        jsonPath("$.followersSize").value(2L),
                        jsonPath("$.followingSize").value(1L),
                        jsonPath("$.sameFollowers[*]", hasSize(1)),
                        jsonPath("$.isMutedDirectMessages").value(true),
                        jsonPath("$.isPrivateProfile").value(false),
                        jsonPath("$.isUserMuted").value(true),
                        jsonPath("$.isUserBlocked").value(false),
                        jsonPath("$.isMyProfileBlocked").value(false),
                        jsonPath("$.isWaitingForApprove").value(false),
                        jsonPath("$.isFollower").value(true),
                        jsonPath("$.isSubscriber").value(false)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/99 - Should user Not Found by id")
    void getUserById_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + USER_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/all - Get users")
    void getUsers() throws Exception {
        mockMvc.perform(get(UI_V1_USER + ALL)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(6)),
                        jsonPath("$[*].id").isNotEmpty(),
                        jsonPath("$[*].fullName").isNotEmpty(),
                        jsonPath("$[*].username").isNotEmpty(),
                        jsonPath("$[*].about").isNotEmpty(),
                        jsonPath("$[*].isPrivateProfile").isNotEmpty(),
                        jsonPath("$[*].isMutedDirectMessages").isNotEmpty(),
                        jsonPath("$[*].isUserBlocked").isNotEmpty(),
                        jsonPath("$[*].isMyProfileBlocked").isNotEmpty(),
                        jsonPath("$[*].isWaitingForApprove").isNotEmpty(),
                        jsonPath("$[*].isFollower").isNotEmpty()
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/relevant - Get relevant users")
    void getRelevantUsers() throws Exception {
        mockMvc.perform(get(UI_V1_USER + RELEVANT)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(5)),
                        jsonPath("$[*].id").isNotEmpty(),
                        jsonPath("$[*].fullName").isNotEmpty(),
                        jsonPath("$[*].username").isNotEmpty(),
                        jsonPath("$[*].about").isNotEmpty(),
                        jsonPath("$[*].isPrivateProfile").isNotEmpty(),
                        jsonPath("$[*].isMutedDirectMessages").isNotEmpty(),
                        jsonPath("$[*].isUserBlocked").isNotEmpty(),
                        jsonPath("$[*].isMyProfileBlocked").isNotEmpty(),
                        jsonPath("$[*].isWaitingForApprove").isNotEmpty(),
                        jsonPath("$[*].isFollower").isNotEmpty()
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/search/Androsor - Search users by username")
    void searchUsersByUsername() throws Exception {
        mockMvc.perform(get(UI_V1_USER + SEARCH_USERNAME, TestConstants.USERNAME)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(6)),
                        jsonPath("$[*].id").isNotEmpty(),
                        jsonPath("$[*].fullName").isNotEmpty(),
                        jsonPath("$[*].username").isNotEmpty(),
                        jsonPath("$[*].about").isNotEmpty(),
                        jsonPath("$[*].isPrivateProfile").isNotEmpty(),
                        jsonPath("$[*].isMutedDirectMessages").isNotEmpty(),
                        jsonPath("$[*].isUserBlocked").isNotEmpty(),
                        jsonPath("$[*].isMyProfileBlocked").isNotEmpty(),
                        jsonPath("$[*].isWaitingForApprove").isNotEmpty(),
                        jsonPath("$[*].isFollower").isNotEmpty()
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/items/search/test - Search users by username Not Found")
    void searchUsersByUsername_NotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + SEARCH_USERNAME, "test")
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(0)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/search/Androsor - Search by text")
    void searchByText() throws Exception {
        mockMvc.perform(get(UI_V1_USER + SEARCH_TEXT, TestConstants.USERNAME)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tweetCount").value(0L))
                .andExpect(jsonPath("$.tags[*]", hasSize(0)))
                .andExpect(jsonPath("$.users[*]", hasSize(6)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/user/search/results - Get Search Results")
    void getSearchResults() throws Exception {
        SearchTermsRequest request = new SearchTermsRequest();
        request.setUsers(List.of(1L, 2L));
        mockMvc.perform(post(UI_V1_USER + SEARCH_RESULTS)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(2)),
                        jsonPath("$[1].id").value(TestConstants.USER_ID),
                        jsonPath("$[1].fullName").value(TestConstants.USERNAME),
                        jsonPath("$[1].username").value(TestConstants.USERNAME),
                        jsonPath("$[1].avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$[1].isPrivateProfile").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/start - Start use twitter")
    void startUseTwitter() throws Exception {
        mockMvc.perform(get(UI_V1_USER + START)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName("[200] PUT /ui/v1/user - Update user profile")
    void updateUserProfile() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setFullName("test");
        userRequest.setAbout("test");
        userRequest.setLocation("test");
        userRequest.setWebsite("test");
        mockMvc.perform(put(UI_V1_USER)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(TestConstants.USER_ID),
                        jsonPath("$.email").value(TestConstants.USER_EMAIL),
                        jsonPath("$.fullName").value("test"),
                        jsonPath("$.username").value(TestConstants.USERNAME),
                        jsonPath("$.location").value("test"),
                        jsonPath("$.about").value("test"),
                        jsonPath("$.website").value("test"),
                        jsonPath("$.countryCode").value(TestConstants.COUNTRY),
                        jsonPath("$.phone").value(TestConstants.PHONE),
                        jsonPath("$.country").value(TestConstants.COUNTRY),
                        jsonPath("$.gender").value(TestConstants.GENDER),
                        jsonPath("$.birthday").value(TestConstants.BIRTHDAY),
                        jsonPath("$.registrationDate").value(TestConstants.REGISTRATION_DATE),
                        jsonPath("$.tweetCount").value(TestConstants.TWEET_COUNT),
                        jsonPath("$.mediaTweetCount").value(TestConstants.MEDIA_TWEET_COUNT),
                        jsonPath("$.likeCount").value(TestConstants.LIKE_TWEET_COUNT),
                        jsonPath("$.notificationsCount").value(3),
                        jsonPath("$.active").value(true),
                        jsonPath("$.profileCustomized").value(true),
                        jsonPath("$.profileStarted").value(true),
                        jsonPath("$.backgroundColor").value(TestConstants.BACKGROUND_COLOR),
                        jsonPath("$.colorScheme").value(TestConstants.COLOR_SCHEME),
                        jsonPath("$.avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$.wallpaper").value(TestConstants.WALLPAPER_SRC),
                        jsonPath("$.pinnedTweetId").value(TestConstants.PINNED_TWEET_ID),
                        jsonPath("$.followersSize").value(2L),
                        jsonPath("$.followingSize").value(1L),
                        jsonPath("$.followerRequestsSize").value(1L),
                        jsonPath("$.unreadMessagesCount").value(1L),
                        jsonPath("$.isMutedDirectMessages").value(true),
                        jsonPath("$.isPrivateProfile").value(false)
                );
    }

    @Test
    @DisplayName("[400] PUT /ui/v1/user - Should username length is 0")
    void updateUserProfile_ShouldUsernameLengthIs0() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setFullName("");
        mockMvc.perform(put(UI_V1_USER)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_USERNAME_LENGTH)));
    }

    @Test
    @DisplayName("[400] PUT /ui/v1/user - Should username length more than 50")
    void updateUserProfile_ShouldUsernameLengthMoreThan50() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setFullName(TestConstants.LINK_DESCRIPTION);
        mockMvc.perform(put(UI_V1_USER)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_USERNAME_LENGTH)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/subscribe/1 - Subscribe to notifications")
    void subscribeToNotifications() throws Exception {
        mockMvc.perform(get(UI_V1_USER + SUBSCRIBE_USER_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/subscribe/1 - Unsubscribe from notifications")
    void unsubscribeToNotifications() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/subscribe/2")
                        .header(AUTH_USER_ID_HEADER, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/subscribe/99 - Should user Not Found by id")
    void processSubscribeToNotifications_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + SUBSCRIBE_USER_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/user/subscribe/5 - Should user blocked by other user")
    void processSubscribeToNotifications_ShouldUserBlockedByOtherUser() throws Exception {
        mockMvc.perform(get(UI_V1_USER + SUBSCRIBE_USER_ID, 5)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/pin/tweet/43 - Pin tweet to profile by id")
    void processPinTweet() throws Exception {
        mockMvc.perform(get(UI_V1_USER + PIN_TWEET_ID, 43)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(43)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/pin/tweet/40 - Unpin tweet from profile by id")
    void processUnpinTweet() throws Exception {
        mockMvc.perform(get(UI_V1_USER + PIN_TWEET_ID, 40)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(0)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/pin/tweet/99 - Should tweet Not Found by id")
    void processPinTweet_ShouldTweetNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + PIN_TWEET_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/details/1 - Get user details by id")
    void getUserDetails() throws Exception {
        mockMvc.perform(get(UI_V1_USER + DETAILS_USER_ID, 3)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.fullName").value(TestConstants.USERNAME))
                .andExpect(jsonPath("$.username").value(TestConstants.USERNAME))
                .andExpect(jsonPath("$.about").value(TestConstants.ABOUT))
                .andExpect(jsonPath("$.avatar").value(TestConstants.AVATAR_SRC_1))
                .andExpect(jsonPath("$.followersSize").value(0L))
                .andExpect(jsonPath("$.followingSize").value(0L))
                .andExpect(jsonPath("$.sameFollowers[*]", hasSize(0)))
                .andExpect(jsonPath("$.isPrivateProfile").value(true))
                .andExpect(jsonPath("$.isUserBlocked").value(false))
                .andExpect(jsonPath("$.isMyProfileBlocked").value(false))
                .andExpect(jsonPath("$.isWaitingForApprove").value(true))
                .andExpect(jsonPath("$.isFollower").value(false));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/details/99 - Should user details Not Found by id")
    void getUserDetails_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + DETAILS_USER_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/user/details/5 - Should user blocked by other user")
    void getUserDetails_ShouldUserBlockedByOtherUser() throws Exception {
        mockMvc.perform(get(UI_V1_USER + DETAILS_USER_ID, 5)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }
}
