package com.gmail.javacoded78.integration.service;

import com.gmail.javacoded78.constants.PathConstants;
import com.gmail.javacoded78.dto.request.NotificationRequest;
import com.gmail.javacoded78.dto.response.chat.ChatTweetUserResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetAuthorResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetListResponse;
import com.gmail.javacoded78.enums.LinkCoverSize;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.model.GifImage;
import com.gmail.javacoded78.model.Poll;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.projection.BookmarkProjection;
import com.gmail.javacoded78.repository.projection.ChatTweetProjection;
import com.gmail.javacoded78.repository.projection.NotificationTweetProjection;
import com.gmail.javacoded78.repository.projection.ProfileTweetImageProjection;
import com.gmail.javacoded78.repository.projection.RetweetProjection;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.repository.projection.TweetUserProjection;
import com.gmail.javacoded78.util.TestConstants;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweetServiceTestHelper {

    private static final ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    public static <T> T createTweetProjection(boolean isDeleted, Class<T> type) {
        Map<String, Object> tweetMap = new HashMap<>();
        tweetMap.put("id", TestConstants.TWEET_ID);
        tweetMap.put("text", TestConstants.TWEET_TEXT);
        tweetMap.put("dateTime", LocalDateTime.now());
        tweetMap.put("scheduledDate", LocalDateTime.now());
        tweetMap.put("addressedUsername", TestConstants.USERNAME);
        tweetMap.put("addressedId", TestConstants.USER_ID);
        tweetMap.put("addressedTweetId", 123L);
        tweetMap.put("replyType", ReplyType.EVERYONE);
        tweetMap.put("link", TestConstants.LINK);
        tweetMap.put("linkTitle", TestConstants.LINK_TITLE);
        tweetMap.put("linkDescription", TestConstants.LINK_DESCRIPTION);
        tweetMap.put("linkCover", TestConstants.LINK_COVER);
        tweetMap.put("gifImage", new GifImage());
        tweetMap.put("linkCoverSize", LinkCoverSize.LARGE);
        tweetMap.put("authorId", TestConstants.USER_ID);
        tweetMap.put("listId", TestConstants.LIST_ID);
        tweetMap.put("images", new ArrayList<>());
        tweetMap.put("imageDescription", "");
        tweetMap.put("quoteTweet", new Tweet());
        tweetMap.put("poll", new Poll());
        tweetMap.put("deleted", isDeleted);
        tweetMap.put("user", new TweetAuthorResponse());
        tweetMap.put("tweetList", new TweetListResponse());
        tweetMap.put("taggedImageUsers", new ArrayList<>());
        tweetMap.put("isTweetLiked", false);
        tweetMap.put("isTweetRetweeted", false);
        tweetMap.put("isTweetBookmarked", false);
        tweetMap.put("isUserFollowByOtherUser", false);
        tweetMap.put("retweetsCount", 1);
        tweetMap.put("likedTweetsCount", 1);
        tweetMap.put("repliesCount", 0);
        tweetMap.put("quotesCount", 0);
        if (type.equals(TweetUserProjection.class)) {
            tweetMap.put("retweetsUserIds", List.of(1L, 2L));
        }
        return factory.createProjection(type, tweetMap);
    }

    public static List<TweetUserProjection> createMockTweetUserProjectionList() {
        return Arrays.asList(
                TweetServiceTestHelper.createTweetProjection(false, TweetUserProjection.class),
                TweetServiceTestHelper.createTweetProjection(false, TweetUserProjection.class));
    }

    public static List<RetweetProjection> createMockRetweetProjectionList() {
        RetweetProjection retweetProjection1 = factory.createProjection(
                RetweetProjection.class,
                Map.of(
                        "id", 1L,
                        "retweetDate", LocalDateTime.now(),
                        "tweetId", TestConstants.TWEET_ID,
                        "tweet", TweetServiceTestHelper.createTweetProjection(false, TweetUserProjection.class)
                ));
        RetweetProjection retweetProjection2 = factory.createProjection(
                RetweetProjection.class,
                Map.of(
                        "id", 2L,
                        "retweetDate", LocalDateTime.now(),
                        "tweetId", TestConstants.TWEET_ID,
                        "tweet", TweetServiceTestHelper.createTweetProjection(false, TweetUserProjection.class)
                ));
        return Arrays.asList(retweetProjection1, retweetProjection2);
    }

    public static List<ProfileTweetImageProjection> createMockProfileTweetImageProjections() {
        ProfileTweetImageProjection profileTweetImageProjection1 = factory.createProjection(
                ProfileTweetImageProjection.class,
                Map.of(
                        "tweetId", 1L,
                        "imageId", 1L,
                        "src", "test src"
                ));
        ProfileTweetImageProjection profileTweetImageProjection2 = factory.createProjection(
                ProfileTweetImageProjection.class,
                Map.of(
                        "tweetId", 2L,
                        "imageId", 2L,
                        "src", "test src"
                ));
        return Arrays.asList(profileTweetImageProjection1, profileTweetImageProjection2);
    }

    public static NotificationRequest createMockNotificationRequest(NotificationType notificationType, boolean notificationCondition) {
        return NotificationRequest.builder()
                .notificationType(notificationType)
                .notificationCondition(notificationCondition)
                .notifiedUserId(TestConstants.USER_ID)
                .userId(TestConstants.USER_ID)
                .tweetId(TestConstants.TWEET_ID)
                .build();
    }

    public static List<BookmarkProjection> createMockBookmarkProjectionList() {
        BookmarkProjection bookmarkProjection1 = factory.createProjection(
                BookmarkProjection.class,
                Map.of(
                        "id", 1L,
                        "bookmarkDate", LocalDateTime.now(),
                        "tweetId", TestConstants.TWEET_ID,
                        "tweet", TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class)
                ));
        BookmarkProjection bookmarkProjection2 = factory.createProjection(
                BookmarkProjection.class,
                Map.of(
                        "id", 2L,
                        "bookmarkDate", LocalDateTime.now(),
                        "tweetId", TestConstants.TWEET_ID,
                        "tweet", TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class)
                ));
        return Arrays.asList(bookmarkProjection1, bookmarkProjection2);
    }

    public static NotificationTweetProjection createNotificationTweetProjection() {
        return factory.createProjection(
                NotificationTweetProjection.class,
                Map.of("id", 1L,
                        "text", "test text",
                        "authorId", TestConstants.USER_ID));
    }

    public static ChatTweetProjection createChatTweetProjection() {
        return factory.createProjection(
                ChatTweetProjection.class,
                Map.of("id", 1L,
                        "text", "test text",
                        "dateTime", LocalDateTime.now(),
                        "user", new ChatTweetUserResponse(),
                        "authorId", TestConstants.USER_ID,
                        "deleted", false));
    }

    public static void mockAuthenticatedUserId() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader(PathConstants.AUTH_USER_ID_HEADER, 1L);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
    }
}
