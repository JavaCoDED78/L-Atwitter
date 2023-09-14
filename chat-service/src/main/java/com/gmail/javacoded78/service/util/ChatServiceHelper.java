package com.gmail.javacoded78.service.util;

import com.gmail.javacoded78.dto.response.chat.ChatTweetResponse;
import com.gmail.javacoded78.dto.response.chat.ChatUserParticipantResponse;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.TweetClient;
import com.gmail.javacoded78.feign.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_PARTICIPANT_BLOCKED;
import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_CHAT_MESSAGE_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class ChatServiceHelper {

    private final UserClient userClient;
    private final TweetClient tweetClient;

    public ChatUserParticipantResponse getChatParticipant(Long userId) {
        return userClient.getChatParticipant(userId);
    }

    public ChatTweetResponse getChatTweet(Long tweetId) {
        return tweetClient.getChatTweet(tweetId);
    }

    public void isParticipantBlocked(Long authUserId, Long userId) {
        if (userClient.isUserBlockedByMyProfile(authUserId) || userClient.isMyProfileBlockedByUser(userId)) {
            throw new ApiRequestException(CHAT_PARTICIPANT_BLOCKED, HttpStatus.BAD_REQUEST);
        }
    }

    public void checkChatMessageLength(String text) {
        if (text.isEmpty()) {
            throw new ApiRequestException(INCORRECT_CHAT_MESSAGE_LENGTH, HttpStatus.BAD_REQUEST);
        }
    }

    public void isTweetExists(Long tweetId) {
        if (Boolean.FALSE.equals(tweetClient.isTweetExists(tweetId))) {
            throw new ApiRequestException(TWEET_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public void isUserExists(Long userId) {
        if (Boolean.FALSE.equals(userClient.isUserExists(userId))) {
            throw new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
}
