package com.gmail.javacoded78.service.util;

import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_TWEET_TEXT_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_DELETED;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;

@Component
@RequiredArgsConstructor
public class TweetValidationHelper {

    private final TweetRepository tweetRepository;
    private final UserClient userClient;

    public List<Long> getValidUserIds() {
        List<Long> tweetAuthorIds = tweetRepository.getTweetAuthorIds();
        return userClient.getValidUserIds(new IdsRequest(tweetAuthorIds));
    }

    public Tweet checkValidTweet(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException(TWEET_NOT_FOUND, HttpStatus.NOT_FOUND));
        validateTweet(tweet.isDeleted(), tweet.getAuthorId());
        return tweet;
    }

    public void validateTweet(boolean isDeleted, Long tweetAuthorId) {
        if (isDeleted) {
            throw new ApiRequestException(TWEET_DELETED, HttpStatus.BAD_REQUEST);
        }
        checkIsValidUserProfile(tweetAuthorId);
    }

    public void validateUserProfile(Long userId) {
        if (Boolean.FALSE.equals(userClient.isUserExists(userId))) {
            throw new ApiRequestException(String.format(USER_ID_NOT_FOUND, userId), HttpStatus.NOT_FOUND);
        }
        checkIsValidUserProfile(userId);
    }

    public void checkIsValidUserProfile(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();

        if (!userId.equals(authUserId)) {
            if (Boolean.TRUE.equals(userClient.isUserHavePrivateProfile(userId))) {
                throw new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
            if (Boolean.TRUE.equals(userClient.isMyProfileBlockedByUser(userId))) {
                throw new ApiRequestException(USER_PROFILE_BLOCKED, HttpStatus.BAD_REQUEST);
            }
        }
    }

    public void checkTweetTextLength(String text) {
        if (text.isEmpty() || text.length() > 280) {
            throw new ApiRequestException(INCORRECT_TWEET_TEXT_LENGTH, HttpStatus.BAD_REQUEST);
        }
    }
}
