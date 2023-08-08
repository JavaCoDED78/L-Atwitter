package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.request.TweetDeleteRequest;
import com.gmail.javacoded78.dto.request.TweetRequest;
import com.gmail.javacoded78.dto.response.NotificationReplyResponse;
import com.gmail.javacoded78.dto.response.ProfileTweetImageResponse;
import com.gmail.javacoded78.dto.response.TweetAdditionalInfoResponse;
import com.gmail.javacoded78.dto.response.TweetImageResponse;
import com.gmail.javacoded78.dto.response.TweetUserResponse;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.model.TweetImage;
import com.gmail.javacoded78.repository.projection.ProfileTweetImageProjection;
import com.gmail.javacoded78.repository.projection.TweetAdditionalInfoProjection;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.repository.projection.TweetUserProjection;
import com.gmail.javacoded78.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TweetMapper {

    private final BasicMapper basicMapper;
    private final TweetService tweetService;

    public HeaderResponse<TweetResponse> getTweets(Pageable pageable) {
        Page<TweetProjection> tweets = tweetService.getTweets(pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public TweetResponse getTweetById(Long tweetId) {
        TweetProjection tweet = tweetService.getTweetById(tweetId);
        return basicMapper.convertToResponse(tweet, TweetResponse.class);
    }

    public HeaderResponse<TweetUserResponse> getUserTweets(Long userId, Pageable pageable) {
        Page<TweetUserProjection> tweets = tweetService.getUserTweets(userId, pageable);
        return basicMapper.getHeaderResponse(tweets, TweetUserResponse.class);
    }

    public HeaderResponse<TweetResponse> getUserMediaTweets(Long userId, Pageable pageable) {
        Page<TweetProjection> tweets = tweetService.getUserMediaTweets(userId, pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public List<ProfileTweetImageResponse> getUserTweetImages(Long userId) {
        List<ProfileTweetImageProjection> tweets = tweetService.getUserTweetImages(userId);
        return basicMapper.convertToResponseList(tweets, ProfileTweetImageResponse.class);
    }

    public TweetAdditionalInfoResponse getTweetAdditionalInfoById(Long tweetId) {
        TweetAdditionalInfoProjection additionalInfo = tweetService.getTweetAdditionalInfoById(tweetId);
        return basicMapper.convertToResponse(additionalInfo, TweetAdditionalInfoResponse.class);
    }

    public List<TweetResponse> getRepliesByTweetId(Long tweetId) {
        List<TweetProjection> tweets = tweetService.getRepliesByTweetId(tweetId);
        return basicMapper.convertToResponseList(tweets, TweetResponse.class);
    }

    public HeaderResponse<TweetResponse> getQuotesByTweetId(Pageable pageable, Long tweetId) {
        Page<TweetProjection> tweets = tweetService.getQuotesByTweetId(pageable, tweetId);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public HeaderResponse<TweetResponse> getMediaTweets(Pageable pageable) {
        Page<TweetProjection> tweets = tweetService.getMediaTweets(pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public HeaderResponse<TweetResponse> getTweetsWithVideo(Pageable pageable) {
        Page<TweetProjection> tweets = tweetService.getTweetsWithVideo(pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public HeaderResponse<TweetResponse> getFollowersTweets(Pageable pageable) {
        Page<TweetProjection> tweets = tweetService.getFollowersTweets(pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public TweetImageResponse uploadTweetImage(MultipartFile file) {
        TweetImage tweetImage = tweetService.uploadTweetImage(file);
        return basicMapper.convertToResponse(tweetImage, TweetImageResponse.class);
    }

    public TweetResponse createTweet(TweetRequest tweetRequest) {
        TweetProjection tweet = tweetService.createNewTweet(basicMapper.convertToResponse(tweetRequest, Tweet.class));
        return basicMapper.convertToResponse(tweet, TweetResponse.class);
    }

    public String deleteTweet(Long tweetId) {
        return tweetService.deleteTweet(tweetId);
    }

    public HeaderResponse<TweetResponse> searchTweets(String text, Pageable pageable) {
        Page<TweetProjection> tweets = tweetService.searchTweets(text, pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public NotificationReplyResponse replyTweet(Long tweetId, TweetRequest tweetRequest) {
        TweetProjection tweet = tweetService.replyTweet(tweetId, basicMapper.convertToResponse(tweetRequest, Tweet.class));
        TweetResponse replyTweet = basicMapper.convertToResponse(tweet, TweetResponse.class);
        return new NotificationReplyResponse(tweetId, NotificationType.REPLY, replyTweet);
    }

    public TweetResponse quoteTweet(Long tweetId, TweetRequest tweetRequest) {
        TweetProjection tweet = tweetService.quoteTweet(tweetId, basicMapper.convertToResponse(tweetRequest, Tweet.class));
        return basicMapper.convertToResponse(tweet, TweetResponse.class);
    }

    public TweetResponse changeTweetReplyType(Long tweetId, ReplyType replyType) {
        TweetProjection tweet = tweetService.changeTweetReplyType(tweetId, replyType);
        return basicMapper.convertToResponse(tweet, TweetResponse.class);
    }
}
