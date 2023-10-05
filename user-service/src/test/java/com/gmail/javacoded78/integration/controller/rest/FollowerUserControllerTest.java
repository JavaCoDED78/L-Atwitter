package com.gmail.javacoded78.integration.controller.rest;

import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOWERS_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOWER_REQUESTS;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOWING_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOW_ACCEPT;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOW_DECLINE;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOW_OVERALL;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOW_PRIVATE;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOW_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_USER;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class FollowerUserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    @DisplayName("[200] GET /ui/v1/user/followers/1 - Get followers by user id")
    void getFollowers() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOWERS_USER_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]",hasSize(2)),
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
    @DisplayName("[404] GET /ui/v1/user/followers/99 - Should user Not Found by id")
    void getFollowers_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOWERS_USER_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/followers/3 - Should user have private profile")
    void getFollowers_ShouldUserHavePrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOWERS_USER_ID, 3)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/user/followers/5 - Should user blocked by other user")
    void getFollowers_ShouldUserBlockedByOtherUser() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOWERS_USER_ID, 5)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/following/4 - Get following by user id")
    void getFollowing() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOWING_USER_ID, 4)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
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
    @DisplayName("[404] GET /ui/v1/user/following/99 - Should user Not Found by id")
    void getFollowing_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOWING_USER_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/following/3 -  Should user have private profile")
    void getFollowing_ShouldUserHavePrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOWING_USER_ID, 3)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/user/following/6 - Should user blocked by other user")
    void getFollowing_ShouldUserBlockedByOtherUser() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOWING_USER_ID, 6)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/follower-requests - Follow user by id")
    void getFollowerRequests() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOWER_REQUESTS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(7L))
                .andExpect(jsonPath("$[0].fullName").value(TestConstants.USERNAME))
                .andExpect(jsonPath("$[0].username").value(TestConstants.USERNAME))
                .andExpect(jsonPath("$[0].about").value(TestConstants.ABOUT))
                .andExpect(jsonPath("$[0].avatar").value(TestConstants.AVATAR_SRC_1));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/follow/7 - Follow user by id")
    void processFollow() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_USER_ID, 7)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/follow/1 - Unfollow user by id")
    void processUnfollow() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_USER_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/follow/3 - Follow to user private profile by id")
    void processFollowToPrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_USER_ID, 3)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/follow/99 - Should user Not Found by id")
    void processFollow_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_USER_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/user/follow/6 - Should user blocked by other user")
    void processFollow_ShouldUserBlockedByOtherUser() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_USER_ID, 6)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/follow/overall/1 - Get overall followers if exist")
    void overallFollowers_exist() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_OVERALL, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$[*]", hasSize(1)),
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
    @DisplayName("[200] GET /ui/v1/user/follow/overall/7 - Get overall followers if not exist")
    void overallFollowers_NotExist() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_OVERALL, 7)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(0)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/follow/overall/99 - Should user Not Found by id")
    void overallFollowers_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_OVERALL, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/follow/overall/3 - Should user have private profile")
    void overallFollowers_ShouldUserHavePrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_OVERALL, 3)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/user/follow/overall/6 - Should user blocked by other user")
    void overallFollowers_ShouldUserBlockedByOtherUser() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_OVERALL, 6)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/follow/private/4 - Follow request from private profile")
    void followRequestToPrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_PRIVATE, 4)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(4L),
                        jsonPath("$.fullName").value(TestConstants.FULL_NAME),
                        jsonPath("$.username").value(TestConstants.USERNAME),
                        jsonPath("$.location").value(TestConstants.LOCATION),
                        jsonPath("$.about").value(TestConstants.ABOUT),
                        jsonPath("$.website").value(TestConstants.WEBSITE),
                        jsonPath("$.country").value(TestConstants.COUNTRY),
                        jsonPath("$.birthday").value(TestConstants.BIRTHDAY),
                        jsonPath("$.registrationDate").value(TestConstants.REGISTRATION_DATE),
                        jsonPath("$.tweetCount").value(TestConstants.TWEET_COUNT),
                        jsonPath("$.mediaTweetCount").value(TestConstants.MEDIA_TWEET_COUNT),
                        jsonPath("$.likeCount").value(TestConstants.LIKE_TWEET_COUNT),
                        jsonPath("$.avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$.wallpaper").value(TestConstants.WALLPAPER_SRC),
                        jsonPath("$.pinnedTweetId").isEmpty(),
                        jsonPath("$.followersSize").value(0L),
                        jsonPath("$.followingSize").value(2L),
                        jsonPath("$.sameFollowers[*]", hasSize(0)),
                        jsonPath("$.isMutedDirectMessages").value(true),
                        jsonPath("$.isPrivateProfile").value(true),
                        jsonPath("$.isUserMuted").value(false),
                        jsonPath("$.isUserBlocked").value(true),
                        jsonPath("$.isMyProfileBlocked").value(false),
                        jsonPath("$.isWaitingForApprove").value(false),
                        jsonPath("$.isFollower").value(true),
                        jsonPath("$.isSubscriber").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/follow/private/7 - Unfollow request to private profile")
    void unfollowRequestToPrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_PRIVATE, 7)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(7L),
                        jsonPath("$.fullName").value(TestConstants.FULL_NAME),
                        jsonPath("$.username").value(TestConstants.USERNAME),
                        jsonPath("$.location").value(TestConstants.LOCATION),
                        jsonPath("$.about").value(TestConstants.ABOUT),
                        jsonPath("$.website").value(TestConstants.WEBSITE),
                        jsonPath("$.country").value(TestConstants.COUNTRY),
                        jsonPath("$.birthday").value(TestConstants.BIRTHDAY),
                        jsonPath("$.registrationDate").value(TestConstants.REGISTRATION_DATE),
                        jsonPath("$.tweetCount").value(TestConstants.TWEET_COUNT),
                        jsonPath("$.mediaTweetCount").value(TestConstants.MEDIA_TWEET_COUNT),
                        jsonPath("$.likeCount").value(TestConstants.LIKE_TWEET_COUNT),
                        jsonPath("$.avatar").value(TestConstants.AVATAR_SRC_1),
                        jsonPath("$.wallpaper").value(TestConstants.WALLPAPER_SRC),
                        jsonPath("$.pinnedTweetId").isEmpty(),
                        jsonPath("$.followersSize").value(0L),
                        jsonPath("$.followingSize").value(0L),
                        jsonPath("$.sameFollowers[*]").isEmpty(),
                        jsonPath("$.isMutedDirectMessages").value(true),
                        jsonPath("$.isPrivateProfile").value(false),
                        jsonPath("$.isUserMuted").value(false),
                        jsonPath("$.isUserBlocked").value(false),
                        jsonPath("$.isMyProfileBlocked").value(false),
                        jsonPath("$.isWaitingForApprove").value(false),
                        jsonPath("$.isFollower").value(false),
                        jsonPath("$.isSubscriber").value(false)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/follow/private/99 - Should user Not Found by id")
    void processFollowRequestToPrivateProfile_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_PRIVATE, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/user/follow/private/6 -  Should user blocked by other user")
    void processFollowRequestToPrivateProfile_ShouldUserBlockedByOtherUser() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_PRIVATE, 6)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/follow/accept/2 - Accept follow request")
    void acceptFollowRequest() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_ACCEPT, 2)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User (id:2) accepted."));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/follow/accept/99 - Should user Not Found by id")
    void acceptFollowRequest_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_ACCEPT, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/follow/decline/2 - Decline follow request")
    void declineFollowRequest() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_DECLINE, 2)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User (id:2) declined."));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/follow/decline/99 - Should user Not Found by id")
    void declineFollowRequest_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + FOLLOW_DECLINE, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }
}
