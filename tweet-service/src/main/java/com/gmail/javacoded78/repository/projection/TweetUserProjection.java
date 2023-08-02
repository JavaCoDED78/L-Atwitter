package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.dto.response.tweet.TweetAuthorResponse;
import com.gmail.javacoded78.enums.LinkCoverSize;
import com.gmail.javacoded78.enums.ReplyType;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface TweetUserProjection {

    Long getId();
    String getText();
    LocalDateTime getDateTime();
    LocalDateTime getScheduledDate();
    String getAddressedUsername();
    Long getAddressedId();
    Long getAddressedTweetId();
    ReplyType getReplyType();
    String getLink();
    String getLinkTitle();
    String getLinkDescription();
    String getLinkCover();
    LinkCoverSize getLinkCoverSize();
    Long getAuthorId();
    List<TweetImageProjection> getImages();
    QuoteTweetProjection getQuoteTweet();
    PollProjection getPoll();
    boolean isDeleted();

    @Value("#{@tweetServiceHelper.getTweetAuthor(target.authorId)}")
    TweetAuthorResponse getUser();

    @Value("#{@retweetRepository.getRetweetsUserIds(target.id)}")
    List<Long> getRetweetsUserIds();

    @Value("#{@tweetServiceHelper.isUserLikedTweet(target.id)}")
    boolean getIsTweetLiked();

    @Value("#{@tweetServiceHelper.isUserRetweetedTweet(target.id)}")
    boolean getIsTweetRetweeted();

    @Value("#{@tweetServiceHelper.isUserBookmarkedTweet(target.id)}")
    boolean getIsTweetBookmarked();

    @Value("#{@tweetServiceHelper.isUserFollowByOtherUser(target.authorId)}")
    boolean getIsUserFollowByOtherUser();

    @Value("#{@retweetRepository.getRetweetSize(target.id)}")
    Long getRetweetsCount();

    @Value("#{@likeTweetRepository.getLikedTweetsSize(target.id)}")
    Long getLikedTweetsCount();

    @Value("#{target.replies != null ? target.replies.size() : 0}")
    Long getRepliesCount();

    interface QuoteTweetProjection {
        @Value("#{target.isDeleted ? null : target.id}")
        Long getId();

        @Value("#{target.isDeleted ? null : target.text}")
        String getText();

        @Value("#{target.isDeleted ? null : target.dateTime}")
        LocalDateTime getDateTime();

        @Value("#{target.isDeleted ? null : target.link}")
        String getLink();

        @Value("#{target.isDeleted ? null : target.linkTitle}")
        String getLinkTitle();

        @Value("#{target.isDeleted ? null : target.linkDescription}")
        String getLinkDescription();

        @Value("#{target.isDeleted ? null : target.linkCover}")
        String getLinkCover();

        @Value("#{target.isDeleted ? null : target.linkCoverSize}")
        LinkCoverSize getLinkCoverSize();

        @Value("#{target.isDeleted ? null : target.authorId}")
        Long getAuthorId();

        @Value("#{target.isDeleted ? null : @tweetServiceHelper.getTweetAuthor(target.authorId)}")
        TweetAuthorResponse getUser();

        boolean isDeleted();
    }

    interface PollProjection {
        Long getId();
        LocalDateTime getDateTime();
        List<PollChoiceProjection> getPollChoices();
    }

    interface PollChoiceProjection {
        Long getId();
        String getChoice();

        @Value("#{@pollChoiceVotedRepository.getVotedUserIds(target.id)}")
        List<VotedUserProjection> getVotedUser();
    }
}
