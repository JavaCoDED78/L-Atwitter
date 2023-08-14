package com.gmail.javacoded78.service.util;

import com.gmail.javacoded78.dto.response.chat.ChatTweetResponse;
import com.gmail.javacoded78.dto.response.chat.ChatUserParticipantResponse;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.TweetClient;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_PARTICIPANT_BLOCKED;
import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_CHAT_MESSAGE_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ChatServiceHelperTest {

    @Autowired
    private ChatServiceHelper chatServiceHelper;

    @MockBean
    private UserClient userClient;

    @MockBean
    private TweetClient tweetClient;

    @Test
    public void getChatParticipant() {
        when(userClient.getChatParticipant(TestConstants.USER_ID)).thenReturn(new ChatUserParticipantResponse());
        assertEquals(new ChatUserParticipantResponse(), chatServiceHelper.getChatParticipant(TestConstants.USER_ID));
        verify(userClient, times(1)).getChatParticipant(TestConstants.USER_ID);
    }

    @Test
    public void getChatTweet() {
        when(tweetClient.getChatTweet(TestConstants.TWEET_ID)).thenReturn(new ChatTweetResponse());
        assertEquals(new ChatTweetResponse(), chatServiceHelper.getChatTweet(TestConstants.TWEET_ID));
        verify(tweetClient, times(1)).getChatTweet(TestConstants.TWEET_ID);
    }

    @Test
    public void isParticipantBlocked() {
        when(userClient.isUserBlockedByMyProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatServiceHelper.isParticipantBlocked(TestConstants.CHAT_ID, 1L));
        assertEquals(CHAT_PARTICIPANT_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void checkChatMessageLength() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatServiceHelper.checkChatMessageLength(""));
        assertEquals(INCORRECT_CHAT_MESSAGE_LENGTH, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void isTweetExists() {
        when(tweetClient.isTweetExists(TestConstants.TWEET_ID)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatServiceHelper.isTweetExists(TestConstants.TWEET_ID));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void isUserExists() {
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatServiceHelper.isUserExists(TestConstants.USER_ID));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}