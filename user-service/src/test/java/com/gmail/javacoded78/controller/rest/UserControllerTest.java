package com.gmail.javacoded78.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.UserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_USER;
import static com.gmail.javacoded78.util.TestConstants.ABOUT;
import static com.gmail.javacoded78.util.TestConstants.ABOUT2;
import static com.gmail.javacoded78.util.TestConstants.AVATAR_SRC_1;
import static com.gmail.javacoded78.util.TestConstants.AVATAR_SRC_2;
import static com.gmail.javacoded78.util.TestConstants.BACKGROUND_COLOR;
import static com.gmail.javacoded78.util.TestConstants.BIRTHDAY;
import static com.gmail.javacoded78.util.TestConstants.COLOR_SCHEME;
import static com.gmail.javacoded78.util.TestConstants.COUNTRY;
import static com.gmail.javacoded78.util.TestConstants.FULL_NAME;
import static com.gmail.javacoded78.util.TestConstants.GENDER;
import static com.gmail.javacoded78.util.TestConstants.LIKE_TWEET_COUNT;
import static com.gmail.javacoded78.util.TestConstants.LINK_DESCRIPTION;
import static com.gmail.javacoded78.util.TestConstants.LOCATION;
import static com.gmail.javacoded78.util.TestConstants.MEDIA_TWEET_COUNT;
import static com.gmail.javacoded78.util.TestConstants.PHONE;
import static com.gmail.javacoded78.util.TestConstants.PINNED_TWEET_ID;
import static com.gmail.javacoded78.util.TestConstants.REGISTRATION_DATE;
import static com.gmail.javacoded78.util.TestConstants.TWEET_COUNT;
import static com.gmail.javacoded78.util.TestConstants.USERNAME;
import static com.gmail.javacoded78.util.TestConstants.USERNAME2;
import static com.gmail.javacoded78.util.TestConstants.USER_EMAIL;
import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static com.gmail.javacoded78.util.TestConstants.WALLPAPER_SRC;
import static com.gmail.javacoded78.util.TestConstants.WEBSITE;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = {"/sql-test/populate-user-db.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = {"/sql-test/clear-user-db.sql"}, executionPhase = AFTER_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("[200] GET /ui/v1/user/token - Get user by token")
    public void getUserByToken() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/token")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(USER_ID))
                .andExpect(jsonPath("$.user.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.user.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.user.username").value(USERNAME))
                .andExpect(jsonPath("$.user.location").value(LOCATION))
                .andExpect(jsonPath("$.user.about").value(ABOUT))
                .andExpect(jsonPath("$.user.website").value(WEBSITE))
                .andExpect(jsonPath("$.user.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.user.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.user.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.user.avatar").value(AVATAR_SRC_1))
                .andExpect(jsonPath("$.user.wallpaper").value(WALLPAPER_SRC))
                .andExpect(jsonPath("$.user.profileCustomized").value(true))
                .andExpect(jsonPath("$.user.profileStarted").value(true));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/1 - Get user by id")
    public void getUserById() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/1")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value(USERNAME2))
                .andExpect(jsonPath("$.username").value(USERNAME2))
                .andExpect(jsonPath("$.location").value("Kyiv"))
                .andExpect(jsonPath("$.about").value(ABOUT2))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.birthday").isEmpty())
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(0))
                .andExpect(jsonPath("$.avatar").value(AVATAR_SRC_2))
                .andExpect(jsonPath("$.pinnedTweetId").isEmpty())
                .andExpect(jsonPath("$.followersSize").value(2L))
                .andExpect(jsonPath("$.followingSize").value(1L))
                .andExpect(jsonPath("$.sameFollowers[*]", hasSize(1)))
                .andExpect(jsonPath("$.isMutedDirectMessages").value(true))
                .andExpect(jsonPath("$.isPrivateProfile").value(false))
                .andExpect(jsonPath("$.isUserMuted").value(true))
                .andExpect(jsonPath("$.isUserBlocked").value(false))
                .andExpect(jsonPath("$.isMyProfileBlocked").value(false))
                .andExpect(jsonPath("$.isWaitingForApprove").value(false))
                .andExpect(jsonPath("$.isFollower").value(true))
                .andExpect(jsonPath("$.isSubscriber").value(false));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/99 - Should user Not Found by id")
    public void getUserById_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/99")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User not found")));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/all - Get users")
    public void getUsers() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/all")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(6)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].fullName").isNotEmpty())
                .andExpect(jsonPath("$[*].username").isNotEmpty())
                .andExpect(jsonPath("$[*].about").isNotEmpty())
                .andExpect(jsonPath("$[*].isPrivateProfile").isNotEmpty())
                .andExpect(jsonPath("$[*].isMutedDirectMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].isUserBlocked").isNotEmpty())
                .andExpect(jsonPath("$[*].isMyProfileBlocked").isNotEmpty())
                .andExpect(jsonPath("$[*].isWaitingForApprove").isNotEmpty())
                .andExpect(jsonPath("$[*].isFollower").isNotEmpty());
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/relevant - Get relevant users")
    public void getRelevantUsers() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/relevant")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(5)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].fullName").isNotEmpty())
                .andExpect(jsonPath("$[*].username").isNotEmpty())
                .andExpect(jsonPath("$[*].about").isNotEmpty())
                .andExpect(jsonPath("$[*].isPrivateProfile").isNotEmpty())
                .andExpect(jsonPath("$[*].isMutedDirectMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].isUserBlocked").isNotEmpty())
                .andExpect(jsonPath("$[*].isMyProfileBlocked").isNotEmpty())
                .andExpect(jsonPath("$[*].isWaitingForApprove").isNotEmpty())
                .andExpect(jsonPath("$[*].isFollower").isNotEmpty());
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/search/MrCat - Search users by username")
    public void searchUsersByUsername() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/search/" + USERNAME)
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(6)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].fullName").isNotEmpty())
                .andExpect(jsonPath("$[*].username").isNotEmpty())
                .andExpect(jsonPath("$[*].about").isNotEmpty())
                .andExpect(jsonPath("$[*].isPrivateProfile").isNotEmpty())
                .andExpect(jsonPath("$[*].isMutedDirectMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].isUserBlocked").isNotEmpty())
                .andExpect(jsonPath("$[*].isMyProfileBlocked").isNotEmpty())
                .andExpect(jsonPath("$[*].isWaitingForApprove").isNotEmpty())
                .andExpect(jsonPath("$[*].isFollower").isNotEmpty());
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/search/test - Search users by username Not Found")
    public void searchUsersByUsername_NotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/search/test")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(0)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/start - Start use twitter")
    public void startUseTwitter() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/start")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName("[200] PUT /ui/v1/user - Update user profile")
    public void updateUserProfile() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setFullName("test");
        userRequest.setAbout("test");
        userRequest.setLocation("test");
        userRequest.setWebsite("test");
        mockMvc.perform(put(UI_V1_USER)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value("test"))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value("test"))
                .andExpect(jsonPath("$.about").value("test"))
                .andExpect(jsonPath("$.website").value("test"))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.profileCustomized").value(true))
                .andExpect(jsonPath("$.profileStarted").value(true))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.avatar").value(AVATAR_SRC_1))
                .andExpect(jsonPath("$.wallpaper").value(WALLPAPER_SRC))
                .andExpect(jsonPath("$.pinnedTweetId").value(PINNED_TWEET_ID))
                .andExpect(jsonPath("$.followersSize").value(2L))
                .andExpect(jsonPath("$.followingSize").value(1L))
                .andExpect(jsonPath("$.followerRequestsSize").value(1L))
                .andExpect(jsonPath("$.unreadMessagesCount").value(1L))
                .andExpect(jsonPath("$.isMutedDirectMessages").value(true))
                .andExpect(jsonPath("$.isPrivateProfile").value(false));
    }

    @Test
    @DisplayName("[400] PUT /ui/v1/user - Should username length is 0")
    public void updateUserProfile_ShouldUsernameLengthIs0() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setFullName("");
        mockMvc.perform(put(UI_V1_USER)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Incorrect username length")));
    }

    @Test
    @DisplayName("[400] PUT /ui/v1/user - Should username length more than 50")
    public void updateUserProfile_ShouldUsernameLengthMoreThan50() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setFullName(LINK_DESCRIPTION);
        mockMvc.perform(put(UI_V1_USER)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Incorrect username length")));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/subscribe/1 - Subscribe to notifications")
    public void subscribeToNotifications() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/subscribe/1")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/subscribe/1 - Unsubscribe from notifications")
    public void unsubscribeToNotifications() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/subscribe/2")
                        .header(AUTH_USER_ID_HEADER, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/subscribe/99 - Should user Not Found by id")
    public void processSubscribeToNotifications_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/subscribe/99")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User (id:99) not found")));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/user/subscribe/5 - Should user blocked by other user")
    public void processSubscribeToNotifications_ShouldUserBlockedByOtherUser() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/subscribe/5")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("User profile blocked")));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/pin/tweet/43 - Pin tweet to profile by id")
    public void processPinTweet() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/pin/tweet/43")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(43)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/pin/tweet/40 - Unpin tweet from profile by id")
    public void processUnpinTweet() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/pin/tweet/40")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(0)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/pin/tweet/99 - Should tweet Not Found by id")
    public void processPinTweet_ShouldTweetNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/pin/tweet/99")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Tweet not found")));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/details/1 - Get user details by id")
    public void getUserDetails() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/details/3")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.fullName").value(USERNAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.avatar").value(AVATAR_SRC_1))
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
    public void getUserDetails_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/details/99")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User (id:99) not found")));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/user/details/5 - Should user blocked by other user")
    public void getUserDetails_ShouldUserBlockedByOtherUser() throws Exception {
        mockMvc.perform(get(UI_V1_USER + "/details/5")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("User profile blocked")));
    }
}