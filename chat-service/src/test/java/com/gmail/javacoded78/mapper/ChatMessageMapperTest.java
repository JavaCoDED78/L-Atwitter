package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.request.ChatMessageRequest;
import com.gmail.javacoded78.dto.request.MessageWithTweetRequest;
import com.gmail.javacoded78.dto.response.ChatMessageResponse;
import com.gmail.javacoded78.dto.response.chat.ChatTweetResponse;
import com.gmail.javacoded78.model.ChatMessage;
import com.gmail.javacoded78.repository.projection.ChatMessageProjection;
import com.gmail.javacoded78.service.ChatMessageService;
import com.gmail.javacoded78.service.ChatServiceTestHelper;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class ChatMessageMapperTest {

    @InjectMocks
    private final ChatMessageMapper chatMessageMapper;

    @MockBean
    private final BasicMapper basicMapper;

    @MockBean
    private final ChatMessageService chatMessageService;

    private final Map<Long, ChatMessageProjection> chatMessageProjection = getChatMessageProjection();
    private final Map<Long, ChatMessageResponse> chatMessageResponse = getChatMessageResponse();

    @Test
    void getChatMessages() {
        List<ChatMessageProjection> mockChatMessageProjectionList = ChatServiceTestHelper.createMockChatMessageProjectionList();
        List<ChatMessageResponse> mockChatMessageResponseList = getMockChatMessageResponseList();
        when(chatMessageService.getChatMessages(TestConstants.CHAT_ID)).thenReturn(mockChatMessageProjectionList);
        when(basicMapper.convertToResponseList(mockChatMessageProjectionList, ChatMessageResponse.class))
                .thenReturn(mockChatMessageResponseList);
        assertEquals(mockChatMessageResponseList, chatMessageMapper.getChatMessages(TestConstants.CHAT_ID));
        verify(chatMessageService, times(1)).getChatMessages(TestConstants.CHAT_ID);
        verify(basicMapper, times(1)).convertToResponseList(mockChatMessageProjectionList, ChatMessageResponse.class);
    }

    @Test
    void readChatMessages() {
        when(chatMessageService.readChatMessages(TestConstants.CHAT_ID)).thenReturn(0L);
        assertEquals(0L, chatMessageMapper.readChatMessages(TestConstants.CHAT_ID));
        verify(chatMessageService, times(1)).readChatMessages(TestConstants.CHAT_ID);
    }

    @Test
    void addMessage() {
        ChatMessageRequest chatMessageRequest = new ChatMessageRequest(TestConstants.CHAT_ID, "text");
        when(chatMessageService.addMessage(new ChatMessage(), TestConstants.CHAT_ID)).thenReturn(chatMessageProjection);
        when(basicMapper.convertToResponse(chatMessageRequest, ChatMessage.class)).thenReturn(new ChatMessage());
        when(basicMapper.convertToResponse(chatMessageProjection.get(1L), ChatMessageResponse.class))
                .thenReturn(chatMessageResponse.get(1L));
        when(basicMapper.convertToResponse(chatMessageProjection.get(2L), ChatMessageResponse.class))
                .thenReturn(chatMessageResponse.get(2L));
        assertEquals(chatMessageResponse, chatMessageMapper.addMessage(chatMessageRequest));
        verify(chatMessageService, times(1)).addMessage(new ChatMessage(), TestConstants.CHAT_ID);
        verify(basicMapper, times(1)).convertToResponse(chatMessageRequest, ChatMessage.class);
        verify(basicMapper, times(1)).convertToResponse(chatMessageProjection.get(1L), ChatMessageResponse.class);
        verify(basicMapper, times(1)).convertToResponse(chatMessageProjection.get(2L), ChatMessageResponse.class);
    }

    @Test
    void addMessageWithTweet() {
        MessageWithTweetRequest request = new MessageWithTweetRequest("test text", TestConstants.TWEET_ID, List.of(1L, 2L));
        when(chatMessageService.addMessageWithTweet(request.getText(), request.getTweetId(), request.getUsersIds()))
                .thenReturn(chatMessageProjection);
        when(basicMapper.convertToResponse(chatMessageProjection.get(1L), ChatMessageResponse.class))
                .thenReturn(chatMessageResponse.get(1L));
        when(basicMapper.convertToResponse(chatMessageProjection.get(2L), ChatMessageResponse.class))
                .thenReturn(chatMessageResponse.get(2L));
        assertEquals(chatMessageResponse, chatMessageMapper.addMessageWithTweet(request));
        verify(chatMessageService, times(1)).addMessageWithTweet(request.getText(), request.getTweetId(), request.getUsersIds());
        verify(basicMapper, times(1)).convertToResponse(chatMessageProjection.get(1L), ChatMessageResponse.class);
        verify(basicMapper, times(1)).convertToResponse(chatMessageProjection.get(2L), ChatMessageResponse.class);
    }

    private List<ChatMessageResponse> getMockChatMessageResponseList() {
        ChatMessageResponse messageResponse1 = ChatMessageResponse.builder()
                .id(1L)
                .text(TestConstants.CHAT_MESSAGE_TEXT)
                .date(LocalDateTime.now())
                .authorId(TestConstants.CHAT_MESSAGE_AUTHOR_ID)
                .tweet(new ChatTweetResponse())
                .chat(new ChatMessageResponse.ChatResponse())
                .build();
        ChatMessageResponse messageResponse2 = ChatMessageResponse.builder()
                .id(2L)
                .text(TestConstants.CHAT_MESSAGE_TEXT)
                .date(LocalDateTime.now())
                .authorId(TestConstants.CHAT_MESSAGE_AUTHOR_ID)
                .tweet(new ChatTweetResponse())
                .chat(new ChatMessageResponse.ChatResponse())
                .build();
        return List.of(messageResponse1, messageResponse2);
    }

    private Map<Long, ChatMessageProjection> getChatMessageProjection() {
        return Map.of(
                1L, ChatServiceTestHelper.createMockChatMessageProjectionList().get(0),
                2L, ChatServiceTestHelper.createMockChatMessageProjectionList().get(1)
        );
    }

    private Map<Long, ChatMessageResponse> getChatMessageResponse() {
        return Map.of(
                1L, getMockChatMessageResponseList().get(0),
                2L, getMockChatMessageResponseList().get(1)
        );
    }
}