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
        Boolean isUserBlockedByMyProfile = userClient.isUserBlockedByMyProfile(authUserId);
        Boolean isMyProfileBlockedByUser = userClient.isMyProfileBlockedByUser(userId);

        if (isUserBlockedByMyProfile || isMyProfileBlockedByUser) {
            throw new ApiRequestException(CHAT_PARTICIPANT_BLOCKED, HttpStatus.BAD_REQUEST);
        }
    }
}
