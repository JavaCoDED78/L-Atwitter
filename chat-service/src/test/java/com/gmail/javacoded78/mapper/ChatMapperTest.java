package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.response.ChatResponse;
import com.gmail.javacoded78.dto.response.chat.ChatUserParticipantResponse;
import com.gmail.javacoded78.repository.projection.ChatProjection;
import com.gmail.javacoded78.service.ChatService;
import com.gmail.javacoded78.service.ChatServiceTestHelper;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class ChatMapperTest {

    @InjectMocks
    private final ChatMapper chatMapper;

    @MockBean
    private final BasicMapper basicMapper;

    @MockBean
    private final ChatService chatService;

    @Test
    void getChatById() {
        ChatProjection mockChatProjection = ChatServiceTestHelper.createMockChatProjection();
        ChatResponse mockChatResponses = getMockChatResponses();
        when(chatService.getChatById(TestConstants.CHAT_ID)).thenReturn(mockChatProjection);
        when(basicMapper.convertToResponse(mockChatProjection, ChatResponse.class))
                .thenReturn(mockChatResponses);
        assertEquals(mockChatResponses, chatMapper.getChatById(TestConstants.CHAT_ID));
        verify(chatService, times(1)).getChatById(TestConstants.CHAT_ID);
        verify(basicMapper, times(1)).convertToResponse(mockChatProjection, ChatResponse.class);
    }

    @Test
    void getUserChats() {
        ChatProjection mockChatProjection1 = ChatServiceTestHelper.createMockChatProjection();
        ChatProjection mockChatProjection2 = ChatServiceTestHelper.createMockChatProjection();
        ChatResponse mockChatResponses1 = getMockChatResponses();
        ChatResponse mockChatResponses2 = getMockChatResponses();
        List<ChatProjection> mockChatProjections = List.of(mockChatProjection1, mockChatProjection2);
        List<ChatResponse> mockChatResponses = List.of(mockChatResponses1, mockChatResponses2);
        when(chatService.getUserChats()).thenReturn(mockChatProjections);
        when(basicMapper.convertToResponseList(mockChatProjections, ChatResponse.class))
                .thenReturn(mockChatResponses);
        assertEquals(mockChatResponses, chatMapper.getUserChats());
        verify(chatService, times(1)).getUserChats();
        verify(basicMapper, times(1)).convertToResponseList(mockChatProjections, ChatResponse.class);
    }

    @Test
    void createChat() {
        ChatProjection mockChatProjection = ChatServiceTestHelper.createMockChatProjection();
        ChatResponse mockChatResponses = getMockChatResponses();
        when(chatService.createChat(TestConstants.CHAT_ID)).thenReturn(mockChatProjection);
        when(basicMapper.convertToResponse(mockChatProjection, ChatResponse.class))
                .thenReturn(mockChatResponses);
        assertEquals(mockChatResponses, chatMapper.createChat(TestConstants.CHAT_ID));
        verify(chatService, times(1)).createChat(TestConstants.CHAT_ID);
        verify(basicMapper, times(1)).convertToResponse(mockChatProjection, ChatResponse.class);
    }

    private ChatResponse getMockChatResponses() {
        ChatResponse.ParticipantResponse participantResponse1 = ChatResponse.ParticipantResponse.builder()
                .id(1L)
                .user(new ChatUserParticipantResponse())
                .leftChat(false)
                .build();
        ChatResponse.ParticipantResponse participantResponse2 = ChatResponse.ParticipantResponse.builder()
                .id(2L)
                .user(new ChatUserParticipantResponse())
                .leftChat(false)
                .build();
        return ChatResponse.builder()
                .id(TestConstants.CHAT_ID)
                .creationDate(LocalDateTime.now())
                .participants(Arrays.asList(participantResponse1, participantResponse2))
                .build();
    }
}