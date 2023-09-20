package com.gmail.javacoded78.integration.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.TweetRequest;
import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_TWEET_TEXT_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_DELETED;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOWER;
import static com.gmail.javacoded78.constants.PathConstants.IMAGES_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.IMAGE_TAGGED;
import static com.gmail.javacoded78.constants.PathConstants.MEDIA;
import static com.gmail.javacoded78.constants.PathConstants.MEDIA_USER_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.QUOTE_USER_ID_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.REPLY_CHANGE_USER_ID_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.REPLY_USER_ID_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_TEXT;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID_INFO;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID_QUOTES;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID_REPLIES;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_TWEETS;
import static com.gmail.javacoded78.constants.PathConstants.USER_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.VIDEO;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class TweetControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Test
    @DisplayName("[200] GET /ui/v1/tweets - Get tweets")
    void getTweets() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]").value(hasSize(8)),
                        jsonPath("$[0].id").value(48L),
                        jsonPath("$[0].text").value("hello world3"),
                        jsonPath("$[0].dateTime").value("2023-09-20T20:40:51"),
                        jsonPath("$[0].scheduledDate").isEmpty(),
                        jsonPath("$[0].addressedUsername").isEmpty(),
                        jsonPath("$[0].addressedId").isEmpty(),
                        jsonPath("$[0].addressedTweetId").isEmpty(),
                        jsonPath("$[0].replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$[0].link").isEmpty(),
                        jsonPath("$[0].linkTitle").isEmpty(),
                        jsonPath("$[0].linkDescription").isEmpty(),
                        jsonPath("$[0].linkCover").isEmpty(),
                        jsonPath("$[0].linkCoverSize").isEmpty(),
                        jsonPath("$[0].quoteTweet").isEmpty(),
                        jsonPath("$[0].user.id").value(2L),
                        jsonPath("$[0].images").isEmpty(),
                        jsonPath("$[0].retweetsCount").value(0L),
                        jsonPath("$[0].likedTweetsCount").value(0L),
                        jsonPath("$[0].repliesCount").value(0L),
                        jsonPath("$[0].isTweetLiked").value(false),
                        jsonPath("$[0].isTweetRetweeted").value(false),
                        jsonPath("$[0].isUserFollowByOtherUser").value(false),
                        jsonPath("$[0].isTweetDeleted").value(false),
                        jsonPath("$[0].isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/43 - Get tweet by id")
    void getTweetById() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID, 43)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(43L),
                        jsonPath("$.text").value(TestConstants.TWEET_TEXT),
                        jsonPath("$.dateTime").value(TestConstants.TWEET_DATETIME),
                        jsonPath("$.scheduledDate").isEmpty(),
                        jsonPath("$.addressedUsername").isEmpty(),
                        jsonPath("$.addressedId").isEmpty(),
                        jsonPath("$.addressedTweetId").isEmpty(),
                        jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$.link").value(TestConstants.LINK),
                        jsonPath("$.linkTitle").value(TestConstants.LINK_TITLE),
                        jsonPath("$.linkDescription").value(TestConstants.LINK_DESCRIPTION),
                        jsonPath("$.linkCover").value(TestConstants.LINK_COVER),
                        jsonPath("$.linkCoverSize").value("LARGE"),
                        jsonPath("$.user.id").value(2L),
                        jsonPath("$.images").isEmpty(),
                        jsonPath("$.quoteTweet").isEmpty(),
                        jsonPath("$.poll").isEmpty(),
                        jsonPath("$.retweetsCount").value(0L),
                        jsonPath("$.likedTweetsCount").value(0L),
                        jsonPath("$.repliesCount").value(0L),
                        jsonPath("$.isTweetLiked").value(false),
                        jsonPath("$.isTweetRetweeted").value(false),
                        jsonPath("$.isUserFollowByOtherUser").value(false),
                        jsonPath("$.isTweetDeleted").value(false),
                        jsonPath("$.isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/99 - Should Not Found tweet by id")
    void getTweetById_ShouldNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/49 - Should tweet deleted")
    void getTweetById_ShouldTweetDeleted() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID, 49)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(TWEET_DELETED)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/50 - Should user have private profile")
    void getTweetById_ShouldUserHavePrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID, 50)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/51 - Should User blocked")
    void getTweetById_ShouldUserBlocked() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID, 51)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/user/2 - Get user tweets by id")
    void getUserTweets() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + USER_USER_ID, TestConstants.USER_ID)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$[*]", hasSize(8)),
                        jsonPath("$[*].id").isNotEmpty(),
                        jsonPath("$[*].text").isNotEmpty(),
                        jsonPath("$[*].dateTime").isNotEmpty(),
                        jsonPath("$[*].addressedUsername").isNotEmpty(),
                        jsonPath("$[*].addressedId").isNotEmpty(),
                        jsonPath("$[*].addressedTweetId").isNotEmpty(),
                        jsonPath("$[*].replyType").isNotEmpty(),
                        jsonPath("$[*].link").isNotEmpty(),
                        jsonPath("$[*].linkTitle").isNotEmpty(),
                        jsonPath("$[*].linkDescription").isNotEmpty(),
                        jsonPath("$[*].linkCover").isNotEmpty(),
                        jsonPath("$[*].linkCoverSize").isNotEmpty(),
                        jsonPath("$[*].quoteTweet").isNotEmpty(),
                        jsonPath("$[*].user").isNotEmpty(),
                        jsonPath("$[*].poll").isNotEmpty(),
                        jsonPath("$[*].images").isNotEmpty(),
                        jsonPath("$[*].retweetsCount").isNotEmpty(),
                        jsonPath("$[*].likedTweetsCount").isNotEmpty(),
                        jsonPath("$[*].repliesCount").isNotEmpty(),
                        jsonPath("$[*].isTweetLiked").isNotEmpty(),
                        jsonPath("$[*].isTweetRetweeted").isNotEmpty(),
                        jsonPath("$[*].isUserFollowByOtherUser").isNotEmpty(),
                        jsonPath("$[*].isTweetDeleted").isNotEmpty(),
                        jsonPath("$[*].isTweetBookmarked").isNotEmpty()
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/user/99 - Should user Not Found by id")
    void getUserTweets_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + USER_USER_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/user/3 - Should user have private profile")
    void getUserTweets_ShouldUserHavePrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + USER_USER_ID, 3)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/user/6 - Should User blocked")
    void getUserTweets_ShouldUserBlocked() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + USER_USER_ID, 6)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/media/user/2 - Get user media tweets by id")
    void getUserMediaTweets() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + MEDIA_USER_USER_ID, TestConstants.USER_ID)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(42L))
                .andExpect(jsonPath("$[0].text").value(TestConstants.YOUTUBE_LINK))
                .andExpect(jsonPath("$[0].dateTime").value("2023-09-20T20:33:36"))
                .andExpect(jsonPath("$[0].scheduledDate").isEmpty())
                .andExpect(jsonPath("$[0].addressedUsername").isEmpty())
                .andExpect(jsonPath("$[0].addressedId").isEmpty())
                .andExpect(jsonPath("$[0].addressedTweetId").isEmpty())
                .andExpect(jsonPath("$[0].replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$[0].link").value(TestConstants.YOUTUBE_LINK))
                .andExpect(jsonPath("$[0].linkTitle").value(TestConstants.YOUTUBE_LINK_TITLE))
                .andExpect(jsonPath("$[0].linkDescription").isEmpty())
                .andExpect(jsonPath("$[0].linkCover").value(TestConstants.YOUTUBE_LINK_COVER))
                .andExpect(jsonPath("$[0].linkCoverSize").isEmpty())
                .andExpect(jsonPath("$[0].quoteTweet").isEmpty())
                .andExpect(jsonPath("$[0].user.id").value(2L))
                .andExpect(jsonPath("$[0].poll").isEmpty())
                .andExpect(jsonPath("$[*].images").isNotEmpty())
                .andExpect(jsonPath("$[0].retweetsCount").value(0L))
                .andExpect(jsonPath("$[0].likedTweetsCount").value(0L))
                .andExpect(jsonPath("$[0].repliesCount").value(0L))
                .andExpect(jsonPath("$[0].isTweetLiked").value(false))
                .andExpect(jsonPath("$[0].isTweetRetweeted").value(false))
                .andExpect(jsonPath("$[0].isUserFollowByOtherUser").value(false))
                .andExpect(jsonPath("$[0].isTweetDeleted").value(false))
                .andExpect(jsonPath("$[0].isTweetBookmarked").value(false));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/media/user/99 - Should user Not Found by id")
    void getUserMediaTweets_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + MEDIA_USER_USER_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/media/user/3 - Should user have private profile")
    void getUserMediaTweets_ShouldUserHavePrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + MEDIA_USER_USER_ID, 3)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/media/user/6 - Should User blocked")
    void getUserMediaTweets_ShouldUserBlocked() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + MEDIA_USER_USER_ID, 6)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/images/1 - Get user tweets with images")
    void getUserTweetImages() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + IMAGES_USER_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].tweetId").value(45L))
                .andExpect(jsonPath("$[0].imageId").value(1L))
                .andExpect(jsonPath("$[0].src").isNotEmpty());
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/images/99 - Should user Not Found by id")
    void getUserTweetImages_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + IMAGES_USER_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/images/3 - Should user have private profile")
    void getUserTweetImages_ShouldUserHavePrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + IMAGES_USER_ID, 3)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/images/6 - Should User blocked")
    void getUserTweetImages_ShouldUserBlocked() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + IMAGES_USER_ID, 6)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/43/info - Get tweet additional info by id")
    void getTweetAdditionalInfoById() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_INFO, 43)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.text").value(TestConstants.TWEET_TEXT),
                        jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$.user.id").value(2L),
                        jsonPath("$.user.fullName").value(TestConstants.FULL_NAME),
                        jsonPath("$.user.username").value(TestConstants.FULL_NAME),
                        jsonPath("$.user.isFollower").value(false),
                        jsonPath("$.user.isMyProfileBlocked").value(false),
                        jsonPath("$.user.isUserBlocked").value(false),
                        jsonPath("$.user.isUserMuted").value(false)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/99/info - Should Not Found tweet by id")
    void getTweetAdditionalInfoById_ShouldNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_INFO, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/49/info - Should tweet deleted")
    void getTweetAdditionalInfoById_ShouldTweetDeleted() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_INFO, 49)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(TWEET_DELETED)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/50/info - Should user have private profile")
    void getTweetAdditionalInfoById_ShouldUserHavePrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_INFO, 50)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/51/info - Should User blocked")
    void getTweetAdditionalInfoById_ShouldUserBlocked() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_INFO, 51)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/40/replies - Get Replies By Tweet Id")
    void getRepliesByTweetId() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_REPLIES, 40)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(41L),
                        jsonPath("$[0].text").value("test reply"),
                        jsonPath("$[0].dateTime").value("2023-09-20T20:31:55"),
                        jsonPath("$[0].scheduledDate").isEmpty(),
                        jsonPath("$[0].addressedUsername").value("Androsor"),
                        jsonPath("$[0].addressedId").value(2L),
                        jsonPath("$[0].addressedTweetId").value(40L),
                        jsonPath("$[0].replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$[0].link").isEmpty(),
                        jsonPath("$[0].linkTitle").isEmpty(),
                        jsonPath("$[0].linkDescription").isEmpty(),
                        jsonPath("$[0].linkCover").isEmpty(),
                        jsonPath("$[0].linkCoverSize").isEmpty(),
                        jsonPath("$[0].quoteTweet").isEmpty(),
                        jsonPath("$[0].user.id").value(1L),
                        jsonPath("$[0].poll").isEmpty(),
                        jsonPath("$[0].images").isEmpty(),
                        jsonPath("$[0].retweetsCount").value(0L),
                        jsonPath("$[0].likedTweetsCount").value(0L),
                        jsonPath("$[0].repliesCount").value(0L),
                        jsonPath("$[0].isTweetLiked").value(false),
                        jsonPath("$[0].isTweetRetweeted").value(false),
                        jsonPath("$[0].isUserFollowByOtherUser").value(true),
                        jsonPath("$[0].isTweetDeleted").value(false),
                        jsonPath("$[0].isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/99/replies - Should Not Found tweet by id")
    void getRepliesByTweetId_ShouldNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_REPLIES, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/49/replies - Should tweet deleted")
    void getRepliesByTweetId_ShouldTweetDeleted() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_REPLIES, 49)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(TWEET_DELETED)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/50/replies - Should user have private profile")
    void getRepliesByTweetId_ShouldUserHavePrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_REPLIES, 50)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/49/replies - Should User blocked")
    void getRepliesByTweetId_ShouldUserBlocked() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_REPLIES, 51)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/40/quotes - Get Quotes By Tweet Id")
    void getQuotesByTweetId() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_QUOTES, 40)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(45L),
                        jsonPath("$[0].text").value("media tweet test"),
                        jsonPath("$[0].dateTime").value("2023-09-20T20:38:51"),
                        jsonPath("$[0].scheduledDate").isEmpty(),
                        jsonPath("$[0].addressedUsername").isEmpty(),
                        jsonPath("$[0].addressedId").isEmpty(),
                        jsonPath("$[0].addressedTweetId").isEmpty(),
                        jsonPath("$[0].replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$[0].link").isEmpty(),
                        jsonPath("$[0].linkTitle").isEmpty(),
                        jsonPath("$[0].linkDescription").isEmpty(),
                        jsonPath("$[0].linkCover").isEmpty(),
                        jsonPath("$[0].linkCoverSize").isEmpty(),
                        jsonPath("$[0].quoteTweet.id").value(40L),
                        jsonPath("$[0].user.id").value(1L),
                        jsonPath("$[0].poll").isEmpty(),
                        jsonPath("$[*].images", hasSize(1)),
                        jsonPath("$[0].retweetsCount").value(1L),
                        jsonPath("$[0].likedTweetsCount").value(1L),
                        jsonPath("$[0].repliesCount").value(0L),
                        jsonPath("$[0].isTweetLiked").value(true),
                        jsonPath("$[0].isTweetRetweeted").value(true),
                        jsonPath("$[0].isUserFollowByOtherUser").value(true),
                        jsonPath("$[0].isTweetDeleted").value(false),
                        jsonPath("$[0].isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/99/quotes - Should Not Found tweet by id")
    void getQuotesByTweetId_ShouldNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_QUOTES, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/49/quotes - Should tweet deleted")
    void getQuotesByTweetId_ShouldTweetDeleted() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_QUOTES, 49)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(TWEET_DELETED)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/50/quotes - Should user have private profile")
    void getQuotesByTweetId_ShouldUserHavePrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_QUOTES, 50)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/51/quotes - Should User blocked")
    void getQuotesByTweetId_ShouldUserBlocked() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_QUOTES, 51)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/media - Get media tweets")
    void getMediaTweets() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + MEDIA)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(45L),
                        jsonPath("$[0].text").value("media tweet test"),
                        jsonPath("$[0].dateTime").value("2023-09-20T20:38:51"),
                        jsonPath("$[0].scheduledDate").isEmpty(),
                        jsonPath("$[0].addressedUsername").isEmpty(),
                        jsonPath("$[0].addressedId").isEmpty(),
                        jsonPath("$[0].addressedTweetId").isEmpty(),
                        jsonPath("$[0].replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$[0].link").isEmpty(),
                        jsonPath("$[0].linkTitle").isEmpty(),
                        jsonPath("$[0].linkDescription").isEmpty(),
                        jsonPath("$[0].linkCover").isEmpty(),
                        jsonPath("$[0].linkCoverSize").isEmpty(),
                        jsonPath("$[0].quoteTweet.id").value(40L),
                        jsonPath("$[0].user.id").value(1L),
                        jsonPath("$[0].poll").isEmpty(),
                        jsonPath("$[*].images", hasSize(1)),
                        jsonPath("$[0].retweetsCount").value(1L),
                        jsonPath("$[0].likedTweetsCount").value(1L),
                        jsonPath("$[0].repliesCount").value(0L),
                        jsonPath("$[0].isTweetLiked").value(true),
                        jsonPath("$[0].isTweetRetweeted").value(true),
                        jsonPath("$[0].isUserFollowByOtherUser").value(true),
                        jsonPath("$[0].isTweetDeleted").value(false),
                        jsonPath("$[0].isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/video - Get tweets with video")
    void getTweetsWithVideo() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + VIDEO)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(42L),
                        jsonPath("$[0].text").value(TestConstants.YOUTUBE_LINK),
                        jsonPath("$[0].dateTime").value("2023-09-20T20:33:36"),
                        jsonPath("$[0].scheduledDate").isEmpty(),
                        jsonPath("$[0].addressedUsername").isEmpty(),
                        jsonPath("$[0].addressedId").isEmpty(),
                        jsonPath("$[0].addressedTweetId").isEmpty(),
                        jsonPath("$[0].replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$[0].link").value(TestConstants.YOUTUBE_LINK),
                        jsonPath("$[0].linkTitle").value(TestConstants.YOUTUBE_LINK_TITLE),
                        jsonPath("$[0].linkDescription").isEmpty(),
                        jsonPath("$[0].linkCover").value(TestConstants.YOUTUBE_LINK_COVER),
                        jsonPath("$[0].linkCoverSize").isEmpty(),
                        jsonPath("$[0].quoteTweet").isEmpty(),
                        jsonPath("$[0].user.id").value(2L),
                        jsonPath("$[0].poll").isEmpty(),
                        jsonPath("$[0].images").isEmpty(),
                        jsonPath("$[0].retweetsCount").value(0L),
                        jsonPath("$[0].likedTweetsCount").value(0L),
                        jsonPath("$[0].repliesCount").value(0L),
                        jsonPath("$[0].isTweetLiked").value(false),
                        jsonPath("$[0].isTweetRetweeted").value(false),
                        jsonPath("$[0].isUserFollowByOtherUser").value(false),
                        jsonPath("$[0].isTweetDeleted").value(false),
                        jsonPath("$[0].isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/follower - Get followers tweets")
    void getFollowersTweets() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + FOLLOWER)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(9)),
                        jsonPath("$[6].id").value(42L),
                        jsonPath("$[6].text").value(TestConstants.YOUTUBE_LINK),
                        jsonPath("$[6].dateTime").value("2023-09-20T20:33:36"),
                        jsonPath("$[6].scheduledDate").isEmpty(),
                        jsonPath("$[6].addressedUsername").isEmpty(),
                        jsonPath("$[6].addressedId").isEmpty(),
                        jsonPath("$[6].addressedTweetId").isEmpty(),
                        jsonPath("$[6].replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$[6].link").value(TestConstants.YOUTUBE_LINK),
                        jsonPath("$[6].linkTitle").value(TestConstants.YOUTUBE_LINK_TITLE),
                        jsonPath("$[6].linkDescription").isEmpty(),
                        jsonPath("$[6].linkCover").value(TestConstants.YOUTUBE_LINK_COVER),
                        jsonPath("$[6].linkCoverSize").isEmpty(),
                        jsonPath("$[6].quoteTweet").isEmpty(),
                        jsonPath("$[6].user.id").value(2L),
                        jsonPath("$[6].poll").isEmpty(),
                        jsonPath("$[6].images").isEmpty(),
                        jsonPath("$[6].retweetsCount").value(0L),
                        jsonPath("$[6].likedTweetsCount").value(0L),
                        jsonPath("$[6].repliesCount").value(0L),
                        jsonPath("$[6].isTweetLiked").value(false),
                        jsonPath("$[6].isTweetRetweeted").value(false),
                        jsonPath("$[6].isUserFollowByOtherUser").value(false),
                        jsonPath("$[6].isTweetDeleted").value(false),
                        jsonPath("$[6].isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/image/tagged/45 - Get tagged image users")
    void getTaggedImageUsers() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + IMAGE_TAGGED, 45)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(2)),
                        jsonPath("$[0].id").value(1L),
                        jsonPath("$[0].fullName").value(TestConstants.USERNAME2),
                        jsonPath("$[0].username").value(TestConstants.USERNAME2),
                        jsonPath("$[0].about").value(TestConstants.ABOUT2),
                        jsonPath("$[0].isPrivateProfile").value(false),
                        jsonPath("$[0].isMutedDirectMessages").value(true),
                        jsonPath("$[0].isUserBlocked").value(false),
                        jsonPath("$[0].isMyProfileBlocked").value(false),
                        jsonPath("$[0].isWaitingForApprove").value(false),
                        jsonPath("$[0].isFollower").value(true)
                );
    }

    @Test
    @DisplayName("[400] POST /ui/v1/tweets - Should tweet text length more than 280 symbols")
    void createTweet_ShouldTweetTextLengthMoreThan280Symbols() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText(TestConstants.LINK_DESCRIPTION + TestConstants.LINK_DESCRIPTION);
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(UI_V1_TWEETS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_TWEET_TEXT_LENGTH)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/tweets - Should tweet text length length is 0")
    void createTweet_ShouldTweetTextLengthLengthIs0() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText(TestConstants.LINK_DESCRIPTION + TestConstants.LINK_DESCRIPTION);
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(UI_V1_TWEETS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_TWEET_TEXT_LENGTH)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/tweets - Create tweet with hashtag")
    void createTweetWithHashtag() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test tweet #test123");
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(UI_V1_TWEETS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.text").value("test tweet #test123"),
                        jsonPath("$.dateTime").isNotEmpty(),
                        jsonPath("$.scheduledDate").isEmpty(),
                        jsonPath("$.addressedUsername").isEmpty(),
                        jsonPath("$.addressedId").isEmpty(),
                        jsonPath("$.addressedTweetId").isEmpty(),
                        jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$.link").isEmpty(),
                        jsonPath("$.linkTitle").isEmpty(),
                        jsonPath("$.linkDescription").isEmpty(),
                        jsonPath("$.linkCover").isEmpty(),
                        jsonPath("$.linkCoverSize").isEmpty(),
                        jsonPath("$.quoteTweet").isEmpty(),
                        jsonPath("$.user.id").value(2L),
                        jsonPath("$.poll").isEmpty(),
                        jsonPath("$.images").isEmpty(),
                        jsonPath("$.retweetsCount").value(0L),
                        jsonPath("$.likedTweetsCount").value(0L),
                        jsonPath("$.repliesCount").value(0L),
                        jsonPath("$.isTweetLiked").value(false),
                        jsonPath("$.isTweetRetweeted").value(false),
                        jsonPath("$.isUserFollowByOtherUser").value(false),
                        jsonPath("$.isTweetDeleted").value(false),
                        jsonPath("$.isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[200] POST /ui/v1/tweets - Create tweet with link")
    void createTweetWithLink() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText(TestConstants.TWEET_TEXT);
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(UI_V1_TWEETS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.text").value(TestConstants.TWEET_TEXT),
                        jsonPath("$.dateTime").isNotEmpty(),
                        jsonPath("$.scheduledDate").isEmpty(),
                        jsonPath("$.addressedUsername").isEmpty(),
                        jsonPath("$.addressedId").isEmpty(),
                        jsonPath("$.addressedTweetId").isEmpty(),
                        jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$.link").isNotEmpty(),
                        jsonPath("$.linkTitle").isNotEmpty(),
                        jsonPath("$.linkDescription").isNotEmpty(),
                        jsonPath("$.linkCover").isNotEmpty(),
                        jsonPath("$.linkCoverSize").isNotEmpty(),
                        jsonPath("$.quoteTweet").isEmpty(),
                        jsonPath("$.user.id").value(2L),
                        jsonPath("$.poll").isEmpty(),
                        jsonPath("$.images").isEmpty(),
                        jsonPath("$.retweetsCount").value(0L),
                        jsonPath("$.likedTweetsCount").value(0L),
                        jsonPath("$.repliesCount").value(0L),
                        jsonPath("$.isTweetLiked").value(false),
                        jsonPath("$.isTweetRetweeted").value(false),
                        jsonPath("$.isUserFollowByOtherUser").value(false),
                        jsonPath("$.isTweetDeleted").value(false),
                        jsonPath("$.isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[200] POST /ui/v1/tweets - Create tweet with YouTube link")
    void createTweetWithYouTubeLink() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText(TestConstants.TEXT_WITH_YOUTUBE_LINK);
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(UI_V1_TWEETS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.text").value(TestConstants.TEXT_WITH_YOUTUBE_LINK),
                        jsonPath("$.dateTime").isNotEmpty(),
                        jsonPath("$.scheduledDate").isEmpty(),
                        jsonPath("$.addressedUsername").isEmpty(),
                        jsonPath("$.addressedId").isEmpty(),
                        jsonPath("$.addressedTweetId").isEmpty(),
                        jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$.link").value(TestConstants.YOUTUBE_LINK),
                        jsonPath("$.linkTitle").value(TestConstants.YOUTUBE_LINK_TITLE),
                        jsonPath("$.linkDescription").isEmpty(),
                        jsonPath("$.linkCover").value(TestConstants.YOUTUBE_LINK_COVER),
                        jsonPath("$.linkCoverSize").isEmpty(),
                        jsonPath("$.quoteTweet").isEmpty(),
                        jsonPath("$.user.id").value(2L),
                        jsonPath("$.poll").isEmpty(),
                        jsonPath("$.images").isEmpty(),
                        jsonPath("$.retweetsCount").value(0L),
                        jsonPath("$.likedTweetsCount").value(0L),
                        jsonPath("$.repliesCount").value(0L),
                        jsonPath("$.isTweetLiked").value(false),
                        jsonPath("$.isTweetRetweeted").value(false),
                        jsonPath("$.isUserFollowByOtherUser").value(false),
                        jsonPath("$.isTweetDeleted").value(false),
                        jsonPath("$.isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[200] POST /ui/v1/tweets - Create tweet with list id")
    void createTweetWithListId() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test tweet with list id");
        tweetRequest.setListId(4L);
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(UI_V1_TWEETS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.text").value("test tweet with list id"),
                        jsonPath("$.dateTime").isNotEmpty(),
                        jsonPath("$.scheduledDate").isEmpty(),
                        jsonPath("$.addressedUsername").isEmpty(),
                        jsonPath("$.addressedId").isEmpty(),
                        jsonPath("$.addressedTweetId").isEmpty(),
                        jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$.link").isEmpty(),
                        jsonPath("$.linkTitle").isEmpty(),
                        jsonPath("$.linkDescription").isEmpty(),
                        jsonPath("$.linkCover").isEmpty(),
                        jsonPath("$.linkCoverSize").isEmpty(),
                        jsonPath("$.quoteTweet").isEmpty(),
                        jsonPath("$.user.id").value(2L),
                        jsonPath("$.tweetList.id").value(4L),
                        jsonPath("$.tweetList.name").value("test list name"),
                        jsonPath("$.poll").isEmpty(),
                        jsonPath("$.images").isEmpty(),
                        jsonPath("$.retweetsCount").value(0L),
                        jsonPath("$.likedTweetsCount").value(0L),
                        jsonPath("$.repliesCount").value(0L),
                        jsonPath("$.isTweetLiked").value(false),
                        jsonPath("$.isTweetRetweeted").value(false),
                        jsonPath("$.isUserFollowByOtherUser").value(false),
                        jsonPath("$.isTweetDeleted").value(false),
                        jsonPath("$.isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[200] DELETE /ui/v1/tweets/40 - Delete Tweet")
    void deleteTweet() throws Exception {
        mockMvc.perform(delete(UI_V1_TWEETS + TWEET_ID, 40)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Your Tweet was deleted")));
    }

    @Test
    @DisplayName("[404] DELETE /ui/v1/tweets/99 - Should Tweet Not Found by id")
    void deleteTweet_ShouldTweetNotFoundById() throws Exception {
        mockMvc.perform(delete(UI_V1_TWEETS + TWEET_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/search/test - Search tweets by text")
    void searchTweets() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + SEARCH_TEXT, "test")
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(9)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/tweets/reply/2/43 - Reply tweet by id")
    void replyTweet() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test reply");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        mockMvc.perform(post(UI_V1_TWEETS + REPLY_USER_ID_TWEET_ID, 2, 43)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tweetId").value(43))
                .andExpect(jsonPath("$.notificationType").value("REPLY"))
                .andExpect(jsonPath("$.tweet.id").isNotEmpty())
                .andExpect(jsonPath("$.tweet.text").value("test reply"))
                .andExpect(jsonPath("$.tweet.addressedTweetId").value(43))
                .andExpect(jsonPath("$.tweet.user.id").value(2));
    }

    @Test
    @DisplayName("[404] POST /ui/v1/tweets/reply/2/99 - Should tweet Not Found by id")
    void replyTweet_ShouldTweetNotFound() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test reply");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        mockMvc.perform(post(UI_V1_TWEETS + REPLY_USER_ID_TWEET_ID, 2, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/reply/2/49 - Should tweet deleted")
    void replyTweet_ShouldTweetDeleted() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test reply");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        mockMvc.perform(post(UI_V1_TWEETS + REPLY_USER_ID_TWEET_ID, 2, 49)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(TWEET_DELETED)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/reply/2/50 - Should user have private profile")
    void replyTweet_ShouldUserHavePrivateProfile() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test reply");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        mockMvc.perform(post(UI_V1_TWEETS + REPLY_USER_ID_TWEET_ID, 2, 50)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/reply/2/51 - Should User blocked")
    void replyTweet_ShouldUserBlocked() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test reply");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        mockMvc.perform(post(UI_V1_TWEETS + REPLY_USER_ID_TWEET_ID, 2, 51)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/tweets/quote/2/43 - Quote tweet by id")
    void quoteTweet() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test quote");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        mockMvc.perform(post(UI_V1_TWEETS + QUOTE_USER_ID_TWEET_ID, 2, 43)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.text").value("test quote"),
                        jsonPath("$.dateTime").isNotEmpty(),
                        jsonPath("$.scheduledDate").isEmpty(),
                        jsonPath("$.addressedUsername").isEmpty(),
                        jsonPath("$.addressedId").isEmpty(),
                        jsonPath("$.addressedTweetId").isEmpty(),
                        jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$.link").isEmpty(),
                        jsonPath("$.linkTitle").isEmpty(),
                        jsonPath("$.linkDescription").isEmpty(),
                        jsonPath("$.linkCover").isEmpty(),
                        jsonPath("$.linkCoverSize").isEmpty(),
                        jsonPath("$.user.id").value(2L),
                        jsonPath("$.quoteTweet").isNotEmpty(),
                        jsonPath("$.quoteTweet.id").value(43),
                        jsonPath("$.quoteTweet.text").value(TestConstants.TWEET_TEXT),
                        jsonPath("$.quoteTweet.dateTime").value(TestConstants.TWEET_DATETIME),
                        jsonPath("$.poll").isEmpty(),
                        jsonPath("$.images").isEmpty(),
                        jsonPath("$.retweetsCount").value(0L),
                        jsonPath("$.likedTweetsCount").value(0L),
                        jsonPath("$.repliesCount").value(0L),
                        jsonPath("$.isTweetLiked").value(false),
                        jsonPath("$.isTweetRetweeted").value(false),
                        jsonPath("$.isUserFollowByOtherUser").value(false),
                        jsonPath("$.isTweetDeleted").value(false),
                        jsonPath("$.isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[404] POST /ui/v1/tweets/quote/2/99 - Should Tweet Not Found by id")
    void quoteTweet_ShouldTweetNotFound() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test quote");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        mockMvc.perform(post(UI_V1_TWEETS + QUOTE_USER_ID_TWEET_ID, 2, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/quote/2/49 - Should tweet deleted")
    void quoteTweet_ShouldTweetDeleted() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test reply");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        mockMvc.perform(post(UI_V1_TWEETS + QUOTE_USER_ID_TWEET_ID, 2, 49)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(TWEET_DELETED)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/quote/2/50 - Should user have private profile")
    void quoteTweet_ShouldUserHavePrivateProfile() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test reply");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        mockMvc.perform(post(UI_V1_TWEETS + QUOTE_USER_ID_TWEET_ID, 2, 50)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/quote/2/51 - Should User blocked")
    void quoteTweet_ShouldUserBlocked() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test reply");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        mockMvc.perform(post(UI_V1_TWEETS + QUOTE_USER_ID_TWEET_ID, 2, 51)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/reply/change/2/43 - Change Tweet reply type by id")
    void changeTweetReplyType() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + REPLY_CHANGE_USER_ID_TWEET_ID, 2, 43)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .param("replyType", String.valueOf(ReplyType.FOLLOW)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(43L),
                        jsonPath("$.text").value(TestConstants.TWEET_TEXT),
                        jsonPath("$.dateTime").value(TestConstants.TWEET_DATETIME),
                        jsonPath("$.scheduledDate").isEmpty(),
                        jsonPath("$.addressedUsername").isEmpty(),
                        jsonPath("$.addressedId").isEmpty(),
                        jsonPath("$.addressedTweetId").isEmpty(),
                        jsonPath("$.replyType").value(ReplyType.FOLLOW.toString()),
                        jsonPath("$.link").value(TestConstants.LINK),
                        jsonPath("$.linkTitle").value(TestConstants.LINK_TITLE),
                        jsonPath("$.linkDescription").value(TestConstants.LINK_DESCRIPTION),
                        jsonPath("$.linkCover").value(TestConstants.LINK_COVER),
                        jsonPath("$.linkCoverSize").value("LARGE"),
                        jsonPath("$.user.id").value(2L),
                        jsonPath("$.images").isEmpty(),
                        jsonPath("$.quoteTweet").isEmpty(),
                        jsonPath("$.poll").isEmpty(),
                        jsonPath("$.retweetsCount").value(0L),
                        jsonPath("$.likedTweetsCount").value(0L),
                        jsonPath("$.repliesCount").value(0L),
                        jsonPath("$.isTweetLiked").value(false),
                        jsonPath("$.isTweetRetweeted").value(false),
                        jsonPath("$.isUserFollowByOtherUser").value(false),
                        jsonPath("$.isTweetDeleted").value(false),
                        jsonPath("$.isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/reply/change/2/99 - Should Tweet Not Found by id")
    void changeTweetReplyType_ShouldTweetNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + REPLY_CHANGE_USER_ID_TWEET_ID, 2, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .param("replyType", String.valueOf(ReplyType.FOLLOW)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/reply/change/2/41 - Should Tweet Not Found by user")
    void changeTweetReplyType_ShouldTweetNotFoundByUser() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + REPLY_CHANGE_USER_ID_TWEET_ID, 2, 41)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .param("replyType", String.valueOf(ReplyType.FOLLOW)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }
}
