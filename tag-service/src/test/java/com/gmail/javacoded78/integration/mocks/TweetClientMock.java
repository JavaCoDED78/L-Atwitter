package com.gmail.javacoded78.integration.mocks;

import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.tweet.TweetAuthorResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.enums.LinkCoverSize;
import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.feign.TweetClient;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.gmail.javacoded78.util.TestConstants.LINK;
import static com.gmail.javacoded78.util.TestConstants.LINK_COVER;
import static com.gmail.javacoded78.util.TestConstants.LINK_DESCRIPTION;
import static com.gmail.javacoded78.util.TestConstants.LINK_TITLE;
import static com.gmail.javacoded78.util.TestConstants.TWEET_DATETIME;
import static com.gmail.javacoded78.util.TestConstants.TWEET_TEXT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TweetClientMock {

    public static void setMockListTweetResponse(TweetClient tweetClient) {
        when(tweetClient.getTweetsByIds(any(IdsRequest.class)))
                .thenReturn(
                        Arrays.asList(TweetResponse.builder()
                                .id(43L)
                                .text(TWEET_TEXT)
                                .dateTime(LocalDateTime.parse(TWEET_DATETIME))
                                .replyType(ReplyType.EVERYONE)
                                .link(LINK)
                                .linkTitle(LINK_TITLE)
                                .linkDescription(LINK_DESCRIPTION)
                                .linkCover(LINK_COVER)
                                .linkCoverSize(LinkCoverSize.LARGE)
                                .user(TweetAuthorResponse.builder()
                                        .id(2L)
                                        .build())
                                .retweetsCount(0L)
                                .likedTweetsCount(0L)
                                .repliesCount(0L)
                                .build())
                );
    }
}
