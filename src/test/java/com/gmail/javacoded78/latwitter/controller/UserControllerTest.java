package com.gmail.javacoded78.latwitter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.latwitter.dto.request.UserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;

import static com.gmail.javacoded78.latwitter.util.TestConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/populate-table-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/populate-table-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/2 - Get user by id")
    public void getUserById() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/" + USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(PRIVATE_PROFILE))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$.bookmarks").isNotEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(PROFILE_CUSTOMIZED))
                .andExpect(jsonPath("$.profileStarted").value(PROFILE_STARTED))
                .andExpect(jsonPath("$.unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$.userMutedList").isNotEmpty())
                .andExpect(jsonPath("$.userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isNotEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isNotEmpty())
                .andExpect(jsonPath("$.following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/99 - Should user Not Found by id")
    public void getUserById_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/all - Get users")
    public void getUsers() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(6)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].email").isNotEmpty())
                .andExpect(jsonPath("$[*].fullName").isNotEmpty())
                .andExpect(jsonPath("$[*].username").isNotEmpty())
                .andExpect(jsonPath("$[*].location").isNotEmpty())
                .andExpect(jsonPath("$[*].about").isNotEmpty())
                .andExpect(jsonPath("$[*].countryCode").isNotEmpty())
                .andExpect(jsonPath("$[*].phone").isNotEmpty())
                .andExpect(jsonPath("$[*].country").isNotEmpty())
                .andExpect(jsonPath("$[*].gender").isNotEmpty())
                .andExpect(jsonPath("$[*].language").isNotEmpty())
                .andExpect(jsonPath("$[*].backgroundColor").isNotEmpty())
                .andExpect(jsonPath("$[*].colorScheme").isNotEmpty())
                .andExpect(jsonPath("$[*].mutedDirectMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].privateProfile").isNotEmpty())
                .andExpect(jsonPath("$[*].website").isNotEmpty())
                .andExpect(jsonPath("$[*].birthday").isNotEmpty())
                .andExpect(jsonPath("$[*].registrationDate").isNotEmpty())
                .andExpect(jsonPath("$[*].tweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].mediaTweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].likeCount").isNotEmpty())
                .andExpect(jsonPath("$[*].notificationsCount").isNotEmpty())
                .andExpect(jsonPath("$[*].pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].bookmarks").isNotEmpty())
                .andExpect(jsonPath("$[*].avatar.id").isNotEmpty())
                .andExpect(jsonPath("$[*].wallpaper.id").isNotEmpty())
                .andExpect(jsonPath("$[*].profileCustomized").isNotEmpty())
                .andExpect(jsonPath("$[*].profileStarted").isNotEmpty())
                .andExpect(jsonPath("$[*].unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].userMutedList").isNotEmpty())
                .andExpect(jsonPath("$[*].userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$[*].subscribers").isNotEmpty())
                .andExpect(jsonPath("$[*].followerRequests").isNotEmpty())
                .andExpect(jsonPath("$[*].followers").isNotEmpty())
                .andExpect(jsonPath("$[*].following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/relevant - Get relevant users")
    public void getRelevantUsers() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/relevant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(5)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].email").isNotEmpty())
                .andExpect(jsonPath("$[*].fullName").isNotEmpty())
                .andExpect(jsonPath("$[*].username").isNotEmpty())
                .andExpect(jsonPath("$[*].location").isNotEmpty())
                .andExpect(jsonPath("$[*].about").isNotEmpty())
                .andExpect(jsonPath("$[*].countryCode").isNotEmpty())
                .andExpect(jsonPath("$[*].phone").isNotEmpty())
                .andExpect(jsonPath("$[*].country").isNotEmpty())
                .andExpect(jsonPath("$[*].gender").isNotEmpty())
                .andExpect(jsonPath("$[*].language").isNotEmpty())
                .andExpect(jsonPath("$[*].backgroundColor").isNotEmpty())
                .andExpect(jsonPath("$[*].colorScheme").isNotEmpty())
                .andExpect(jsonPath("$[*].mutedDirectMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].privateProfile").isNotEmpty())
                .andExpect(jsonPath("$[*].website").isNotEmpty())
                .andExpect(jsonPath("$[*].birthday").isNotEmpty())
                .andExpect(jsonPath("$[*].registrationDate").isNotEmpty())
                .andExpect(jsonPath("$[*].tweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].mediaTweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].likeCount").isNotEmpty())
                .andExpect(jsonPath("$[*].notificationsCount").isNotEmpty())
                .andExpect(jsonPath("$[*].pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].bookmarks").isNotEmpty())
                .andExpect(jsonPath("$[*].avatar.id").isNotEmpty())
                .andExpect(jsonPath("$[*].wallpaper.id").isNotEmpty())
                .andExpect(jsonPath("$[*].profileCustomized").isNotEmpty())
                .andExpect(jsonPath("$[*].profileStarted").isNotEmpty())
                .andExpect(jsonPath("$[*].unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].userMutedList").isNotEmpty())
                .andExpect(jsonPath("$[*].userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$[*].subscribers").isNotEmpty())
                .andExpect(jsonPath("$[*].followerRequests").isNotEmpty())
                .andExpect(jsonPath("$[*].followers").isNotEmpty())
                .andExpect(jsonPath("$[*].following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/search/MrCat - Search users by username")
    public void searchUsersByUsername() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/search/" + USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(6)))
                .andExpect(jsonPath("$[0].id").value(USER_ID))
                .andExpect(jsonPath("$[0].email").value(USER_EMAIL))
                .andExpect(jsonPath("$[0].fullName").value(FULL_NAME))
                .andExpect(jsonPath("$[0].username").value(USERNAME))
                .andExpect(jsonPath("$[0].location").value(LOCATION))
                .andExpect(jsonPath("$[0].about").value(ABOUT))
                .andExpect(jsonPath("$[0].website").value(WEBSITE))
                .andExpect(jsonPath("$[0].birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$[0].registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$[0].tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$[0].notificationsCount").value(3))
                .andExpect(jsonPath("$[0].pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$[0].bookmarks").isNotEmpty())
                .andExpect(jsonPath("$[0].avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$[0].wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$[0].profileCustomized").value(true))
                .andExpect(jsonPath("$[0].profileStarted").value(true))
                .andExpect(jsonPath("$[0].unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$[0].followers").isNotEmpty())
                .andExpect(jsonPath("$[0].following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/search/test - Search users by username Not Found")
    public void searchUsersByUsername_NotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/search/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(0)));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/2/start - Start use twitter")
    public void startUseTwitter() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/" + USER_ID + "/start"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(PRIVATE_PROFILE))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$.bookmarks").isNotEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(PROFILE_CUSTOMIZED))
                .andExpect(jsonPath("$.profileStarted").value(PROFILE_STARTED))
                .andExpect(jsonPath("$.unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$.userMutedList").isNotEmpty())
                .andExpect(jsonPath("$.userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isNotEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isNotEmpty())
                .andExpect(jsonPath("$.following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/2/tweets - Get user tweets by id")
    public void getUserTweets() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/" + USER_ID + "/tweets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(8)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].text").isNotEmpty())
                .andExpect(jsonPath("$[*].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedUsername").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedId").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedTweetId").isNotEmpty())
                .andExpect(jsonPath("$[*].replyType").isNotEmpty())
                .andExpect(jsonPath("$[*].link").isNotEmpty())
                .andExpect(jsonPath("$[*].linkTitle").isNotEmpty())
                .andExpect(jsonPath("$[*].linkDescription").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCover").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCoverSize").isNotEmpty())
                .andExpect(jsonPath("$[*].quoteTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].user").isNotEmpty())
                .andExpect(jsonPath("$[*].poll").isNotEmpty())
                .andExpect(jsonPath("$[*].images").isNotEmpty())
                .andExpect(jsonPath("$[*].likedTweets").isNotEmpty())
                .andExpect(jsonPath("$[*].retweets").isNotEmpty())
                .andExpect(jsonPath("$[*].replies").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/99/tweets - Should user Not Found by id")
    public void getUserTweets_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/99/tweets"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User (id:99) not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/2/liked - Get user liked tweets by id")
    public void getUserLikedTweets() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/" + USER_ID + "/liked"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].text").isNotEmpty())
                .andExpect(jsonPath("$[*].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedUsername").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedId").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedTweetId").isNotEmpty())
                .andExpect(jsonPath("$[*].replyType").isNotEmpty())
                .andExpect(jsonPath("$[*].link").isNotEmpty())
                .andExpect(jsonPath("$[*].linkTitle").isNotEmpty())
                .andExpect(jsonPath("$[*].linkDescription").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCover").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCoverSize").isNotEmpty())
                .andExpect(jsonPath("$[*].quoteTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].user").isNotEmpty())
                .andExpect(jsonPath("$[*].poll").isNotEmpty())
                .andExpect(jsonPath("$[*].images").isNotEmpty())
                .andExpect(jsonPath("$[*].likedTweets").isNotEmpty())
                .andExpect(jsonPath("$[*].retweets").isNotEmpty())
                .andExpect(jsonPath("$[*].replies").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/99/liked - Should user Not Found by id")
    public void getUserLikedTweets_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/99/liked"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User (id:99) not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/2/media - Get user media tweets by id")
    public void getUserMediaTweets() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/" + USER_ID + "/media"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].text").isNotEmpty())
                .andExpect(jsonPath("$[*].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedUsername").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedId").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedTweetId").isNotEmpty())
                .andExpect(jsonPath("$[*].replyType").isNotEmpty())
                .andExpect(jsonPath("$[*].link").isNotEmpty())
                .andExpect(jsonPath("$[*].linkTitle").isNotEmpty())
                .andExpect(jsonPath("$[*].linkDescription").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCover").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCoverSize").isNotEmpty())
                .andExpect(jsonPath("$[*].quoteTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].user").isNotEmpty())
                .andExpect(jsonPath("$[*].poll").isNotEmpty())
                .andExpect(jsonPath("$[*].images").isNotEmpty())
                .andExpect(jsonPath("$[*].likedTweets").isNotEmpty())
                .andExpect(jsonPath("$[*].retweets").isNotEmpty())
                .andExpect(jsonPath("$[*].replies").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/99/media - Should user Not Found by id")
    public void getUserMediaTweets_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/99/media"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User (id:99) not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/2/replies - Get user retweets and replies by id")
    public void getUserRetweetsAndReplies() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/" + USER_ID + "/replies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].text").isNotEmpty())
                .andExpect(jsonPath("$[*].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedUsername").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedId").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedTweetId").isNotEmpty())
                .andExpect(jsonPath("$[*].replyType").isNotEmpty())
                .andExpect(jsonPath("$[*].link").isNotEmpty())
                .andExpect(jsonPath("$[*].linkTitle").isNotEmpty())
                .andExpect(jsonPath("$[*].linkDescription").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCover").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCoverSize").isNotEmpty())
                .andExpect(jsonPath("$[*].quoteTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].user").isNotEmpty())
                .andExpect(jsonPath("$[*].poll").isNotEmpty())
                .andExpect(jsonPath("$[*].images").isNotEmpty())
                .andExpect(jsonPath("$[*].likedTweets").isNotEmpty())
                .andExpect(jsonPath("$[*].retweets").isNotEmpty())
                .andExpect(jsonPath("$[*].replies").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/99/replies - Should user Not Found by id")
    public void getUserRetweetsAndReplies_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/99/replies"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User (id:99) not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/notifications - Get user notifications")
    public void getUserNotifications() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notifications[*]", hasSize(3)))
                .andExpect(jsonPath("$.tweetAuthors[*]", hasSize(0)));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/bookmarks - Get user bookmarks")
    public void getUserBookmarks() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/bookmarks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].text").isNotEmpty())
                .andExpect(jsonPath("$[*].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedUsername").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedId").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedTweetId").isNotEmpty())
                .andExpect(jsonPath("$[*].replyType").isNotEmpty())
                .andExpect(jsonPath("$[*].link").isNotEmpty())
                .andExpect(jsonPath("$[*].linkTitle").isNotEmpty())
                .andExpect(jsonPath("$[*].linkDescription").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCover").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCoverSize").isNotEmpty())
                .andExpect(jsonPath("$[*].quoteTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].user").isNotEmpty())
                .andExpect(jsonPath("$[*].poll").isNotEmpty())
                .andExpect(jsonPath("$[*].images").isNotEmpty())
                .andExpect(jsonPath("$[*].likedTweets").isNotEmpty())
                .andExpect(jsonPath("$[*].retweets").isNotEmpty())
                .andExpect(jsonPath("$[*].replies").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/bookmarks/43 - Add tweet to bookmarks")
    public void processUserBookmarks_addBookmark() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/bookmarks/43"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(PRIVATE_PROFILE))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$.bookmarks[1].tweet.id").value(43))
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(PROFILE_CUSTOMIZED))
                .andExpect(jsonPath("$.profileStarted").value(PROFILE_STARTED))
                .andExpect(jsonPath("$.unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$.userMutedList").isNotEmpty())
                .andExpect(jsonPath("$.userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isNotEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isNotEmpty())
                .andExpect(jsonPath("$.following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/bookmarks/40 - Remove tweet from bookmarks")
    public void processUserBookmarks_removeBookmark() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/bookmarks/40"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(PRIVATE_PROFILE))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$.bookmarks").isEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(true))
                .andExpect(jsonPath("$.profileStarted").value(true))
                .andExpect(jsonPath("$.unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$.userMutedList").isNotEmpty())
                .andExpect(jsonPath("$.userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isNotEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isNotEmpty())
                .andExpect(jsonPath("$.following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/bookmarks/99 - Should Tweet Not Found")
    public void processUserBookmarks_ShouldTweetNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/bookmarks/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Tweet not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] PUT /api/v1/user - Update user profile")
    public void updateUserProfile() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("test");
        userRequest.setAbout("test");
        userRequest.setLocation("test");
        userRequest.setWebsite("test");

        mockMvc.perform(put(URL_USER_BASIC)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.location").value("test"))
                .andExpect(jsonPath("$.about").value("test"))
                .andExpect(jsonPath("$.website").value("test"))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(PRIVATE_PROFILE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$.bookmarks").isNotEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(true))
                .andExpect(jsonPath("$.profileStarted").value(true))
                .andExpect(jsonPath("$.unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$.userMutedList").isNotEmpty())
                .andExpect(jsonPath("$.userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isNotEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isNotEmpty())
                .andExpect(jsonPath("$.following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[400] PUT /api/v1/user - Should username length is 0")
    public void updateUserProfile_ShouldUsernameLengthIs0() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("");

        mockMvc.perform(put(URL_USER_BASIC)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Incorrect username length")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[400] PUT /api/v1/user - Should username length more than 50")
    public void updateUserProfile_ShouldUsernameLengthMoreThan50() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(LINK_DESCRIPTION);

        mockMvc.perform(put(URL_USER_BASIC)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Incorrect username length")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] POST /api/v1/user/upload - Upload user image to S3 bucket")
    public void uploadImage() throws Exception {
        FileInputStream inputFile = new FileInputStream("src/test/resources/test.png");
        MockMultipartFile file = new MockMultipartFile("file", "test.png", MediaType.MULTIPART_FORM_DATA_VALUE, inputFile);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // TODO create new s3 bucket
//        mockMvc.perform(multipart(URL_USER_BASIC + "/upload")
//                .file(file))
//                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/followers/1 - Get followers by user id")
    public void getFollowers() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/followers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].email").isNotEmpty())
                .andExpect(jsonPath("$[*].fullName").isNotEmpty())
                .andExpect(jsonPath("$[*].username").isNotEmpty())
                .andExpect(jsonPath("$[*].location").isNotEmpty())
                .andExpect(jsonPath("$[*].about").isNotEmpty())
                .andExpect(jsonPath("$[*].countryCode").isNotEmpty())
                .andExpect(jsonPath("$[*].phone").isNotEmpty())
                .andExpect(jsonPath("$[*].country").isNotEmpty())
                .andExpect(jsonPath("$[*].gender").isNotEmpty())
                .andExpect(jsonPath("$[*].language").isNotEmpty())
                .andExpect(jsonPath("$[*].backgroundColor").isNotEmpty())
                .andExpect(jsonPath("$[*].colorScheme").isNotEmpty())
                .andExpect(jsonPath("$[*].mutedDirectMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].privateProfile").isNotEmpty())
                .andExpect(jsonPath("$[*].website").isNotEmpty())
                .andExpect(jsonPath("$[*].birthday").isNotEmpty())
                .andExpect(jsonPath("$[*].registrationDate").isNotEmpty())
                .andExpect(jsonPath("$[*].tweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].mediaTweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].likeCount").isNotEmpty())
                .andExpect(jsonPath("$[*].notificationsCount").isNotEmpty())
                .andExpect(jsonPath("$[*].pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].bookmarks").isNotEmpty())
                .andExpect(jsonPath("$[*].avatar.id").isNotEmpty())
                .andExpect(jsonPath("$[*].wallpaper.id").isNotEmpty())
                .andExpect(jsonPath("$[*].profileCustomized").isNotEmpty())
                .andExpect(jsonPath("$[*].profileStarted").isNotEmpty())
                .andExpect(jsonPath("$[*].unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].userMutedList").isNotEmpty())
                .andExpect(jsonPath("$[*].userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$[*].subscribers").isNotEmpty())
                .andExpect(jsonPath("$[*].followerRequests").isNotEmpty())
                .andExpect(jsonPath("$[*].followers").isNotEmpty())
                .andExpect(jsonPath("$[*].following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/followers/99 - Should user Not Found by id")
    public void getFollowers_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/followers/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User (id:99) not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[400] GET /api/v1/user/followers/5 - Should user blocked by other user")
    public void getFollowers_ShouldUserBlockedByOtherUser() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/followers/5"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("User (id:2) is blocked")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/following/4 - Get following by user id")
    public void getFollowing() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/following/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].email").isNotEmpty())
                .andExpect(jsonPath("$[*].fullName").isNotEmpty())
                .andExpect(jsonPath("$[*].username").isNotEmpty())
                .andExpect(jsonPath("$[*].location").isNotEmpty())
                .andExpect(jsonPath("$[*].about").isNotEmpty())
                .andExpect(jsonPath("$[*].countryCode").isNotEmpty())
                .andExpect(jsonPath("$[*].phone").isNotEmpty())
                .andExpect(jsonPath("$[*].country").isNotEmpty())
                .andExpect(jsonPath("$[*].gender").isNotEmpty())
                .andExpect(jsonPath("$[*].language").isNotEmpty())
                .andExpect(jsonPath("$[*].backgroundColor").isNotEmpty())
                .andExpect(jsonPath("$[*].colorScheme").isNotEmpty())
                .andExpect(jsonPath("$[*].mutedDirectMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].privateProfile").isNotEmpty())
                .andExpect(jsonPath("$[*].website").isNotEmpty())
                .andExpect(jsonPath("$[*].birthday").isNotEmpty())
                .andExpect(jsonPath("$[*].registrationDate").isNotEmpty())
                .andExpect(jsonPath("$[*].tweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].mediaTweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].likeCount").isNotEmpty())
                .andExpect(jsonPath("$[*].notificationsCount").isNotEmpty())
                .andExpect(jsonPath("$[*].pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].bookmarks").isNotEmpty())
                .andExpect(jsonPath("$[*].avatar.id").isNotEmpty())
                .andExpect(jsonPath("$[*].wallpaper.id").isNotEmpty())
                .andExpect(jsonPath("$[*].profileCustomized").isNotEmpty())
                .andExpect(jsonPath("$[*].profileStarted").isNotEmpty())
                .andExpect(jsonPath("$[*].unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].userMutedList").isNotEmpty())
                .andExpect(jsonPath("$[*].userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$[*].subscribers").isNotEmpty())
                .andExpect(jsonPath("$[*].followerRequests").isNotEmpty())
                .andExpect(jsonPath("$[*].followers").isNotEmpty())
                .andExpect(jsonPath("$[*].following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/following/99 - Should user Not Found by id")
    public void getFollowing_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/following/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User (id:99) not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[400] GET /api/v1/user/following/6 - Should user blocked by other user")
    public void getFollowing_ShouldUserBlockedByOtherUser() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/following/6"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("User (id:2) is blocked")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/follow/3 - Follow user by id")
    public void processFollow() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/follow/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.email").value("test2016@test.test"))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.privateProfile").value(true))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.userBlockedList").isEmpty())
                .andExpect(jsonPath("$.subscribers").isEmpty())
                .andExpect(jsonPath("$.followers").isEmpty())
                .andExpect(jsonPath("$.following[0].id").value(2));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/follow/1 - Unfollow user by id")
    public void processUnfollow() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/follow/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("merikbest2015@gmail.com"))
                .andExpect(jsonPath("$.fullName").value("Vbhjckfd1"))
                .andExpect(jsonPath("$.username").value("Vbhjckfd1"))
                .andExpect(jsonPath("$.location").value("Kyiv"))
                .andExpect(jsonPath("$.about").value("Hello2"))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").isEmpty())
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.avatar.id").value(11L))
                .andExpect(jsonPath("$.wallpaper.id").value(22L))
                .andExpect(jsonPath("$.userBlockedList").isEmpty())
                .andExpect(jsonPath("$.subscribers").isEmpty())
                .andExpect(jsonPath("$.followers[0].id").value(2))
                .andExpect(jsonPath("$.following").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/follow/99 - Should user Not Found by id")
    public void processFollow_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/follow/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/follow/overall/1 - Get overall followers if exist")
    public void overallFollowers_exist() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/follow/overall/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].email").isNotEmpty())
                .andExpect(jsonPath("$[*].fullName").isNotEmpty())
                .andExpect(jsonPath("$[*].username").isNotEmpty())
                .andExpect(jsonPath("$[*].location").isNotEmpty())
                .andExpect(jsonPath("$[*].about").isNotEmpty())
                .andExpect(jsonPath("$[*].countryCode").isNotEmpty())
                .andExpect(jsonPath("$[*].phone").isNotEmpty())
                .andExpect(jsonPath("$[*].country").isNotEmpty())
                .andExpect(jsonPath("$[*].gender").isNotEmpty())
                .andExpect(jsonPath("$[*].language").isNotEmpty())
                .andExpect(jsonPath("$[*].backgroundColor").isNotEmpty())
                .andExpect(jsonPath("$[*].colorScheme").isNotEmpty())
                .andExpect(jsonPath("$[*].mutedDirectMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].privateProfile").isNotEmpty())
                .andExpect(jsonPath("$[*].website").isNotEmpty())
                .andExpect(jsonPath("$[*].birthday").isNotEmpty())
                .andExpect(jsonPath("$[*].registrationDate").isNotEmpty())
                .andExpect(jsonPath("$[*].tweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].mediaTweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].likeCount").isNotEmpty())
                .andExpect(jsonPath("$[*].notificationsCount").isNotEmpty())
                .andExpect(jsonPath("$[*].pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].bookmarks").isNotEmpty())
                .andExpect(jsonPath("$[*].avatar.id").isNotEmpty())
                .andExpect(jsonPath("$[*].wallpaper.id").isNotEmpty())
                .andExpect(jsonPath("$[*].profileCustomized").isNotEmpty())
                .andExpect(jsonPath("$[*].profileStarted").isNotEmpty())
                .andExpect(jsonPath("$[*].unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].userMutedList").isNotEmpty())
                .andExpect(jsonPath("$[*].userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$[*].subscribers").isNotEmpty())
                .andExpect(jsonPath("$[*].followerRequests").isNotEmpty())
                .andExpect(jsonPath("$[*].followers").isNotEmpty())
                .andExpect(jsonPath("$[*].following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/follow/overall/7 - Get overall followers if not exist")
    public void overallFollowers_NotExist() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/follow/overall/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(0)));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/follow/overall/99 - Should user Not Found by id")
    public void overallFollowers_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/follow/overall/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/follow/private/4 - Follow request from private profile")
    public void followRequestToPrivateProfile() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/follow/private/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4L))
                .andExpect(jsonPath("$.email").value("test2017@test.test"))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(true))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet").isEmpty())
                .andExpect(jsonPath("$.bookmarks").isEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(PROFILE_CUSTOMIZED))
                .andExpect(jsonPath("$.profileStarted").value(PROFILE_STARTED))
                .andExpect(jsonPath("$.unreadMessages").isEmpty())
                .andExpect(jsonPath("$.userMutedList").isEmpty())
                .andExpect(jsonPath("$.userBlockedList").isEmpty())
                .andExpect(jsonPath("$.subscribers").isEmpty())
                .andExpect(jsonPath("$.followerRequests").isNotEmpty())
                .andExpect(jsonPath("$.followers").isEmpty())
                .andExpect(jsonPath("$.following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/follow/private/5 - Unfollow request to private profile")
    public void unfollowRequestToPrivateProfile() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/follow/private/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.email").value("test2018@test.test"))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(true))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet").isEmpty())
                .andExpect(jsonPath("$.bookmarks").isEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(PROFILE_CUSTOMIZED))
                .andExpect(jsonPath("$.profileStarted").value(PROFILE_STARTED))
                .andExpect(jsonPath("$.unreadMessages").isEmpty())
                .andExpect(jsonPath("$.userMutedList").isEmpty())
                .andExpect(jsonPath("$.userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isEmpty())
                .andExpect(jsonPath("$.following").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/follow/private/99 - Should user Not Found by id")
    public void processFollowRequestToPrivateProfile_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/follow/private/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User not found")));
    }

    @Test
    @WithUserDetails("test2018@test.test")
    @DisplayName("[200] GET /api/v1/user/follow/accept/2 - Accept follow request")
    public void acceptFollowRequest() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/follow/accept/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.email").value("test2018@test.test"))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(true))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet").isEmpty())
                .andExpect(jsonPath("$.bookmarks").isEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(PROFILE_CUSTOMIZED))
                .andExpect(jsonPath("$.profileStarted").value(PROFILE_STARTED))
                .andExpect(jsonPath("$.unreadMessages").isEmpty())
                .andExpect(jsonPath("$.userMutedList").isEmpty())
                .andExpect(jsonPath("$.userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isNotEmpty())
                .andExpect(jsonPath("$.followers[0].id").value(USER_ID))
                .andExpect(jsonPath("$.following").isEmpty());
    }

    @Test
    @WithUserDetails("test2018@test.test")
    @DisplayName("[404] GET /api/v1/user/follow/accept/99 - Should user Not Found by id")
    public void acceptFollowRequest_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/follow/accept/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User not found")));
    }

    @Test
    @WithUserDetails("test2018@test.test")
    @DisplayName("[200] GET /api/v1/user/follow/decline/2 - Decline follow request")
    public void declineFollowRequest() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/follow/decline/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.email").value("test2018@test.test"))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(true))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet").isEmpty())
                .andExpect(jsonPath("$.bookmarks").isEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(PROFILE_CUSTOMIZED))
                .andExpect(jsonPath("$.profileStarted").value(PROFILE_STARTED))
                .andExpect(jsonPath("$.unreadMessages").isEmpty())
                .andExpect(jsonPath("$.userMutedList").isEmpty())
                .andExpect(jsonPath("$.userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isEmpty())
                .andExpect(jsonPath("$.following").isEmpty());
    }

    @Test
    @WithUserDetails("test2018@test.test")
    @DisplayName("[404] GET /api/v1/user/follow/decline/99 - Should user Not Found by id")
    public void declineFollowRequest_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/follow/decline/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/subscribe/1 - Subscribe to notifications")
    public void subscribeToNotifications() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/subscribe/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("merikbest2015@gmail.com"))
                .andExpect(jsonPath("$.fullName").value("Vbhjckfd1"))
                .andExpect(jsonPath("$.username").value("Vbhjckfd1"))
                .andExpect(jsonPath("$.location").value("Kyiv"))
                .andExpect(jsonPath("$.about").value("Hello2"))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(PRIVATE_PROFILE))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").isEmpty())
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(0))
                .andExpect(jsonPath("$.pinnedTweet").isEmpty())
                .andExpect(jsonPath("$.bookmarks").isEmpty())
                .andExpect(jsonPath("$.avatar.id").value(11L))
                .andExpect(jsonPath("$.wallpaper.id").value(22L))
                .andExpect(jsonPath("$.profileCustomized").value(PROFILE_CUSTOMIZED))
                .andExpect(jsonPath("$.profileStarted").value(PROFILE_STARTED))
                .andExpect(jsonPath("$.unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$.userMutedList").isEmpty())
                .andExpect(jsonPath("$.userBlockedList").isEmpty())
                .andExpect(jsonPath("$.subscribers").isNotEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isNotEmpty())
                .andExpect(jsonPath("$.following").isNotEmpty());
    }

    @Test
    @WithUserDetails("merikbest2015@gmail.com")
    @DisplayName("[200] GET /api/v1/user/subscribe/1 - Unsubscribe from notifications")
    public void unsubscribeToNotifications() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/subscribe/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(PRIVATE_PROFILE))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet.id").value(40))
                .andExpect(jsonPath("$.bookmarks").isNotEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(PROFILE_CUSTOMIZED))
                .andExpect(jsonPath("$.profileStarted").value(PROFILE_STARTED))
                .andExpect(jsonPath("$.unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$.userMutedList").isNotEmpty())
                .andExpect(jsonPath("$.userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isNotEmpty())
                .andExpect(jsonPath("$.following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/subscribe/99 - Should user Not Found by id")
    public void processSubscribeToNotifications_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/subscribe/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/pin/tweet/43 - Pin tweet to profile by id")
    public void processPinTweet() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/pin/tweet/43"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(PRIVATE_PROFILE))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet.id").value(43))
                .andExpect(jsonPath("$.bookmarks").isNotEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(true))
                .andExpect(jsonPath("$.profileStarted").value(true))
                .andExpect(jsonPath("$.unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$.userMutedList").isNotEmpty())
                .andExpect(jsonPath("$.userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isNotEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isNotEmpty())
                .andExpect(jsonPath("$.following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/pin/tweet/40 - Unpin tweet from profile by id")
    public void processUnpinTweet() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/pin/tweet/40"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(PRIVATE_PROFILE))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet").isEmpty())
                .andExpect(jsonPath("$.bookmarks").isNotEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(true))
                .andExpect(jsonPath("$.profileStarted").value(true))
                .andExpect(jsonPath("$.unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$.followers").isNotEmpty())
                .andExpect(jsonPath("$.userMutedList").isNotEmpty())
                .andExpect(jsonPath("$.userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isNotEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/pin/tweet/99 - Should tweet Not Found by id")
    public void processPinTweet_ShouldTweetNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/pin/tweet/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Tweet not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/blocked - Get blocked users")
    public void getBlockList() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/blocked"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].email").isNotEmpty())
                .andExpect(jsonPath("$[*].fullName").isNotEmpty())
                .andExpect(jsonPath("$[*].username").isNotEmpty())
                .andExpect(jsonPath("$[*].location").isNotEmpty())
                .andExpect(jsonPath("$[*].about").isNotEmpty())
                .andExpect(jsonPath("$[*].countryCode").isNotEmpty())
                .andExpect(jsonPath("$[*].phone").isNotEmpty())
                .andExpect(jsonPath("$[*].country").isNotEmpty())
                .andExpect(jsonPath("$[*].gender").isNotEmpty())
                .andExpect(jsonPath("$[*].language").isNotEmpty())
                .andExpect(jsonPath("$[*].backgroundColor").isNotEmpty())
                .andExpect(jsonPath("$[*].colorScheme").isNotEmpty())
                .andExpect(jsonPath("$[*].mutedDirectMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].privateProfile").isNotEmpty())
                .andExpect(jsonPath("$[*].website").isNotEmpty())
                .andExpect(jsonPath("$[*].birthday").isNotEmpty())
                .andExpect(jsonPath("$[*].registrationDate").isNotEmpty())
                .andExpect(jsonPath("$[*].tweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].mediaTweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].likeCount").isNotEmpty())
                .andExpect(jsonPath("$[*].notificationsCount").isNotEmpty())
                .andExpect(jsonPath("$[*].pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].bookmarks").isNotEmpty())
                .andExpect(jsonPath("$[*].avatar.id").isNotEmpty())
                .andExpect(jsonPath("$[*].wallpaper.id").isNotEmpty())
                .andExpect(jsonPath("$[*].profileCustomized").isNotEmpty())
                .andExpect(jsonPath("$[*].profileStarted").isNotEmpty())
                .andExpect(jsonPath("$[*].unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].userMutedList").isNotEmpty())
                .andExpect(jsonPath("$[*].userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$[*].subscribers").isNotEmpty())
                .andExpect(jsonPath("$[*].followerRequests").isNotEmpty())
                .andExpect(jsonPath("$[*].followers").isNotEmpty())
                .andExpect(jsonPath("$[*].following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/blocked/3 - Add user to block list by id")
    public void addToBlockList() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/blocked/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(PRIVATE_PROFILE))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$.bookmarks").isNotEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(PROFILE_CUSTOMIZED))
                .andExpect(jsonPath("$.profileStarted").value(PROFILE_STARTED))
                .andExpect(jsonPath("$.unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isNotEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isNotEmpty())
                .andExpect(jsonPath("$.following").isNotEmpty())
                .andExpect(jsonPath("$.userMutedList").isNotEmpty())
                .andExpect(jsonPath("$.userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$.userBlockedList", hasSize(2)));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/blocked/4 - Remove user from block list by id")
    public void removeFromBlockList() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/blocked/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(PRIVATE_PROFILE))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$.bookmarks").isNotEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(PROFILE_CUSTOMIZED))
                .andExpect(jsonPath("$.profileStarted").value(PROFILE_STARTED))
                .andExpect(jsonPath("$.unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isNotEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isNotEmpty())
                .andExpect(jsonPath("$.following").isNotEmpty())
                .andExpect(jsonPath("$.userMutedList").isNotEmpty())
                .andExpect(jsonPath("$.userBlockedList").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/blocked/99 - Should user Not Found by id")
    public void processBlockList_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/blocked/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/muted - Get muted list")
    public void getMutedList() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/muted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].email").isNotEmpty())
                .andExpect(jsonPath("$[*].fullName").isNotEmpty())
                .andExpect(jsonPath("$[*].username").isNotEmpty())
                .andExpect(jsonPath("$[*].location").isNotEmpty())
                .andExpect(jsonPath("$[*].about").isNotEmpty())
                .andExpect(jsonPath("$[*].countryCode").isNotEmpty())
                .andExpect(jsonPath("$[*].phone").isNotEmpty())
                .andExpect(jsonPath("$[*].country").isNotEmpty())
                .andExpect(jsonPath("$[*].gender").isNotEmpty())
                .andExpect(jsonPath("$[*].language").isNotEmpty())
                .andExpect(jsonPath("$[*].backgroundColor").isNotEmpty())
                .andExpect(jsonPath("$[*].colorScheme").isNotEmpty())
                .andExpect(jsonPath("$[*].mutedDirectMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].privateProfile").isNotEmpty())
                .andExpect(jsonPath("$[*].website").isNotEmpty())
                .andExpect(jsonPath("$[*].birthday").isNotEmpty())
                .andExpect(jsonPath("$[*].registrationDate").isNotEmpty())
                .andExpect(jsonPath("$[*].tweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].mediaTweetCount").isNotEmpty())
                .andExpect(jsonPath("$[*].likeCount").isNotEmpty())
                .andExpect(jsonPath("$[*].notificationsCount").isNotEmpty())
                .andExpect(jsonPath("$[*].pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].bookmarks").isNotEmpty())
                .andExpect(jsonPath("$[*].avatar.id").isNotEmpty())
                .andExpect(jsonPath("$[*].wallpaper.id").isNotEmpty())
                .andExpect(jsonPath("$[*].profileCustomized").isNotEmpty())
                .andExpect(jsonPath("$[*].profileStarted").isNotEmpty())
                .andExpect(jsonPath("$[*].unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$[*].userMutedList").isNotEmpty())
                .andExpect(jsonPath("$[*].userBlockedList").isNotEmpty())
                .andExpect(jsonPath("$[*].subscribers").isNotEmpty())
                .andExpect(jsonPath("$[*].followerRequests").isNotEmpty())
                .andExpect(jsonPath("$[*].followers").isNotEmpty())
                .andExpect(jsonPath("$[*].following").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/muted/3 - Mute user by id")
    public void addToMutedList() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/muted/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(PRIVATE_PROFILE))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$.bookmarks").isNotEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(PROFILE_CUSTOMIZED))
                .andExpect(jsonPath("$.profileStarted").value(PROFILE_STARTED))
                .andExpect(jsonPath("$.unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isNotEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isNotEmpty())
                .andExpect(jsonPath("$.following").isNotEmpty())
                .andExpect(jsonPath("$.userMutedList").isNotEmpty())
                .andExpect(jsonPath("$.userBlockedList").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/user/muted/1 - Unmute user by id")
    public void removeFromMutedList() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/muted/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.location").value(LOCATION))
                .andExpect(jsonPath("$.about").value(ABOUT))
                .andExpect(jsonPath("$.countryCode").value(COUNTRY_CODE))
                .andExpect(jsonPath("$.phone").value(PHONE))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.gender").value(GENDER))
                .andExpect(jsonPath("$.language").value(LANGUAGE))
                .andExpect(jsonPath("$.backgroundColor").value(BACKGROUND_COLOR))
                .andExpect(jsonPath("$.colorScheme").value(COLOR_SCHEME))
                .andExpect(jsonPath("$.mutedDirectMessages").value(MUTED_DIRECT_MESSAGES))
                .andExpect(jsonPath("$.privateProfile").value(PRIVATE_PROFILE))
                .andExpect(jsonPath("$.website").value(WEBSITE))
                .andExpect(jsonPath("$.birthday").value(BIRTHDAY))
                .andExpect(jsonPath("$.registrationDate").value(REGISTRATION_DATE))
                .andExpect(jsonPath("$.tweetCount").value(TWEET_COUNT))
                .andExpect(jsonPath("$.mediaTweetCount").value(MEDIA_TWEET_COUNT))
                .andExpect(jsonPath("$.likeCount").value(LIKE_TWEET_COUNT))
                .andExpect(jsonPath("$.notificationsCount").value(3))
                .andExpect(jsonPath("$.pinnedTweet").isNotEmpty())
                .andExpect(jsonPath("$.bookmarks").isNotEmpty())
                .andExpect(jsonPath("$.avatar.id").value(AVATAR_ID))
                .andExpect(jsonPath("$.wallpaper.id").value(WALLPAPER_ID))
                .andExpect(jsonPath("$.profileCustomized").value(true))
                .andExpect(jsonPath("$.profileStarted").value(true))
                .andExpect(jsonPath("$.unreadMessages").isNotEmpty())
                .andExpect(jsonPath("$.subscribers").isNotEmpty())
                .andExpect(jsonPath("$.followerRequests").isEmpty())
                .andExpect(jsonPath("$.followers").isNotEmpty())
                .andExpect(jsonPath("$.following").isNotEmpty())
                .andExpect(jsonPath("$.userMutedList").isEmpty())
                .andExpect(jsonPath("$.userBlockedList").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/user/muted/99 - Should user Not Found by id")
    public void addToMutedList_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(URL_USER_BASIC + "/muted/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("User not found")));
    }
}