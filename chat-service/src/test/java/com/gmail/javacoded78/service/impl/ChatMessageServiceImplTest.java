package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.TweetClient;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.model.Chat;
import com.gmail.javacoded78.model.ChatMessage;
import com.gmail.javacoded78.model.ChatParticipant;
import com.gmail.javacoded78.repository.ChatMessageRepository;
import com.gmail.javacoded78.repository.ChatParticipantRepository;
import com.gmail.javacoded78.repository.ChatRepository;
import com.gmail.javacoded78.repository.projection.ChatMessageProjection;
import com.gmail.javacoded78.repository.projection.ChatProjection;
import com.gmail.javacoded78.service.ChatServiceTestHelper;
import com.gmail.javacoded78.util.TestConstants;
import com.gmail.javacoded78.util.TestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_PARTICIPANT_BLOCKED;
import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_CHAT_MESSAGE_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class ChatMessageServiceImplTest {

    private final ChatMessageServiceImpl chatMessageService;

    @MockBean
    private  final ChatRepository chatRepository;

    @MockBean
    private final ChatParticipantRepository chatParticipantRepository;

    @MockBean
    private final ChatMessageRepository chatMessageRepository;

    @MockBean
    private final UserClient userClient;

    @MockBean
    private final TweetClient tweetClient;

    private ChatMessage chatMessage;
    private final Chat mockChat = ChatServiceTestHelper.createMockChat(false);
    private final ChatMessageProjection chatMessageProjection = ChatServiceTestHelper.createMockChatMessageProjectionList().get(0);
    private final Map<Long, ChatMessageProjection> chatParticipants = Map.of(
            TestConstants.USER_ID, chatMessageProjection,
            1L, chatMessageProjection);
    private final List<Long> usersIds = List.of(1L, 2L, 3L);
    private final List<Long> validUserIds = List.of(1L, 2L);

    @BeforeEach
    public void setUp() {
        chatMessage = ChatMessage.builder()
                .id(2L)
                .text("test text")
                .date(LocalDateTime.now())
                .unread(false)
                .build();
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    void getChatMessages() {
        List<ChatMessageProjection> mockChatMessages = ChatServiceTestHelper.createMockChatMessageProjectionList();
        when(chatRepository.getChatById(TestConstants.CHAT_ID, TestConstants.USER_ID, ChatProjection.class))
                .thenReturn(Optional.of(ChatServiceTestHelper.createMockChatProjection()));
        when(chatMessageRepository.getChatMessages(TestConstants.CHAT_ID)).thenReturn(mockChatMessages);
        assertEquals(mockChatMessages, chatMessageService.getChatMessages(TestConstants.CHAT_ID));
        verify(chatRepository, times(1)).getChatById(TestConstants.CHAT_ID, TestConstants.USER_ID, ChatProjection.class);
        verify(chatMessageRepository, times(1)).getChatMessages(TestConstants.CHAT_ID);
    }

    @Test
    void getChatMessages_shouldChatNotFound() {
        when(chatRepository.getChatById(TestConstants.CHAT_ID, TestConstants.USER_ID, ChatProjection.class))
                .thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatMessageService.getChatMessages(TestConstants.CHAT_ID));
        assertEquals(CHAT_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void readChatMessages() {
        when(chatRepository.getChatById(TestConstants.CHAT_ID, TestConstants.USER_ID, ChatProjection.class))
                .thenReturn(Optional.of(ChatServiceTestHelper.createMockChatProjection()));
        when(chatMessageRepository.getUnreadMessagesCount(TestConstants.USER_ID)).thenReturn(123L);
        assertEquals(123L, chatMessageService.readChatMessages(TestConstants.CHAT_ID));
        verify(chatRepository, times(1)).getChatById(TestConstants.CHAT_ID, TestConstants.USER_ID, ChatProjection.class);
        verify(chatMessageRepository, times(1)).readChatMessages(TestConstants.CHAT_ID, TestConstants.USER_ID);
        verify(chatMessageRepository, times(1)).getUnreadMessagesCount(TestConstants.USER_ID);
    }

    @Test
    void readChatMessages_shouldChatNotFound() {
        when(chatRepository.getChatById(TestConstants.CHAT_ID, TestConstants.USER_ID, ChatProjection.class))
                .thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatMessageService.readChatMessages(TestConstants.CHAT_ID));
        assertEquals(CHAT_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void addMessage() {
        when(chatRepository.getChatById(TestConstants.CHAT_ID, TestConstants.USER_ID, Chat.class))
                .thenReturn(Optional.of(mockChat));
        when(chatParticipantRepository.getChatParticipantId(TestConstants.USER_ID, TestConstants.CHAT_ID))
                .thenReturn(1L);
        when(userClient.isUserBlockedByMyProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(false);
        when(chatMessageRepository.getChatMessageById(chatMessage.getId())).thenReturn(Optional.of(chatMessageProjection));
        when(chatParticipantRepository.getChatParticipantIds(TestConstants.CHAT_ID))
                .thenReturn(Arrays.asList(TestConstants.USER_ID, 1L));
        assertEquals(chatParticipants, chatMessageService.addMessage(chatMessage, TestConstants.CHAT_ID));
        verify(chatRepository, times(1)).getChatById(TestConstants.CHAT_ID, TestConstants.USER_ID, Chat.class);
        verify(chatParticipantRepository, times(1)).getChatParticipantId(TestConstants.USER_ID, TestConstants.CHAT_ID);
        verify(userClient, times(1)).isUserBlockedByMyProfile(TestConstants.USER_ID);
        verify(userClient, times(1)).isMyProfileBlockedByUser(1L);
        verify(chatMessageRepository, times(1)).save(chatMessage);
        verify(chatMessageRepository, times(1)).getChatMessageById(chatMessage.getId());
        verify(chatParticipantRepository, times(1)).updateParticipantWhoLeftChat(1L, TestConstants.CHAT_ID);
        verify(chatParticipantRepository, times(1)).getChatParticipantIds(TestConstants.CHAT_ID);
    }

    @Test
    void addMessage_shouldReturnIncorrectChatMessageLength() {
        chatMessage.setText("");
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatMessageService.addMessage(chatMessage, TestConstants.CHAT_ID));
        assertEquals(INCORRECT_CHAT_MESSAGE_LENGTH, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void addMessage_shouldChatNotFound() {
        when(chatRepository.getChatById(TestConstants.CHAT_ID, TestConstants.USER_ID, Chat.class))
                .thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatMessageService.addMessage(chatMessage, TestConstants.CHAT_ID));
        assertEquals(CHAT_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void addMessage_shouldUserBlockedByMyProfile() {
        when(chatRepository.getChatById(TestConstants.CHAT_ID, TestConstants.USER_ID, Chat.class))
                .thenReturn(Optional.of(mockChat));
        when(chatParticipantRepository.getChatParticipantId(TestConstants.USER_ID, TestConstants.CHAT_ID))
                .thenReturn(1L);
        when(userClient.isUserBlockedByMyProfile(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatMessageService.addMessage(chatMessage, TestConstants.CHAT_ID));
        assertEquals(CHAT_PARTICIPANT_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void addMessage_shouldMyProfileBlockedByUser() {
        when(chatRepository.getChatById(TestConstants.CHAT_ID, TestConstants.USER_ID, Chat.class))
                .thenReturn(Optional.of(mockChat));
        when(chatParticipantRepository.getChatParticipantId(TestConstants.USER_ID, TestConstants.CHAT_ID))
                .thenReturn(1L);
        when(userClient.isUserBlockedByMyProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatMessageService.addMessage(chatMessage, TestConstants.CHAT_ID));
        assertEquals(CHAT_PARTICIPANT_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void addMessageWithTweet() {
        when(tweetClient.isTweetExists(TestConstants.TWEET_ID)).thenReturn(true);
        when(userClient.validateChatUsersIds(new IdsRequest(usersIds))).thenReturn(validUserIds);
        when(chatRepository.getChatByParticipants(TestConstants.USER_ID, 1L)).thenReturn(mockChat);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(false);
        when(chatRepository.getChatByParticipants(TestConstants.USER_ID, 2L)).thenReturn(mockChat);
        when(userClient.isMyProfileBlockedByUser(2L)).thenReturn(false);
        when(chatMessageRepository.getChatMessageById(any())).thenReturn(Optional.of(chatMessageProjection));
        assertEquals(chatParticipants, chatMessageService.addMessageWithTweet("test text", TestConstants.TWEET_ID, usersIds));
        verify(tweetClient, times(1)).isTweetExists(TestConstants.TWEET_ID);
        verify(userClient, times(1)).validateChatUsersIds(new IdsRequest(usersIds));
        verify(chatRepository, times(1)).getChatByParticipants(TestConstants.USER_ID, 1L);
        verify(chatRepository, times(1)).getChatByParticipants(TestConstants.USER_ID, 2L);
        verify(userClient, times(2)).isMyProfileBlockedByUser(any());
        verify(chatMessageRepository, times(2)).save(any());
        verify(chatParticipantRepository, times(2)).updateParticipantWhoLeftChat(any(), any());
        verify(chatMessageRepository, times(2)).getChatMessageById(any());
    }

    @Test
    void addMessageWithTweet_shouldCreateNewChat() {
        Chat newChat = new Chat();
        ChatParticipant authorParticipant = new ChatParticipant();
        authorParticipant.setId(11L);
        authorParticipant.setLeftChat(false);
        authorParticipant.setUserId(TestConstants.USER_ID);
        authorParticipant.setChat(newChat);
        ChatParticipant userParticipant = new ChatParticipant();
        userParticipant.setId(12L);
        userParticipant.setLeftChat(false);
        userParticipant.setUserId(1L);
        userParticipant.setChat(newChat);
        when(tweetClient.isTweetExists(TestConstants.TWEET_ID)).thenReturn(true);
        when(userClient.validateChatUsersIds(new IdsRequest(usersIds))).thenReturn(validUserIds);
        when(chatRepository.getChatByParticipants(TestConstants.USER_ID, 1L)).thenReturn(null);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(2L)).thenReturn(false);
        when(chatParticipantRepository.save(ChatParticipant.builder()
                        .userId(TestConstants.USER_ID)
                        .chat(newChat)
                .build())).thenReturn(authorParticipant);
        when(chatParticipantRepository.save(ChatParticipant.builder()
                .userId(1L)
                .chat(newChat)
                .build())).thenReturn(userParticipant);
        when(chatMessageRepository.getChatMessageById(any())).thenReturn(Optional.of(chatMessageProjection));
        assertEquals(chatParticipants, chatMessageService.addMessageWithTweet("test text", TestConstants.TWEET_ID, usersIds));
        verify(tweetClient, times(1)).isTweetExists(TestConstants.TWEET_ID);
        verify(userClient, times(1)).validateChatUsersIds(new IdsRequest(usersIds));
        verify(chatRepository, times(1)).getChatByParticipants(TestConstants.USER_ID, 1L);
        verify(chatRepository, times(1)).getChatByParticipants(TestConstants.USER_ID, 2L);
        verify(userClient, times(2)).isMyProfileBlockedByUser(any());
        verify(chatParticipantRepository, times(4)).save(any());
        verify(chatMessageRepository, times(2)).save(any());
        verify(chatMessageRepository, times(2)).getChatMessageById(any());
    }

    @Test
    void addMessageWithTweet_shouldReturnTweetNotFoundException() {
        when(tweetClient.isTweetExists(TestConstants.TWEET_ID)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatMessageService.addMessageWithTweet("test text", TestConstants.TWEET_ID, usersIds));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
