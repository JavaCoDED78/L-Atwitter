package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.response.ChatResponse;
import com.gmail.javacoded78.dto.response.chat.ChatUserParticipantResponse;
import com.gmail.javacoded78.repository.projection.ChatProjection;
import com.gmail.javacoded78.service.ChatService;
import com.gmail.javacoded78.service.ChatServiceTestHelper;
import com.gmail.javacoded78.util.TestConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ChatMapperTest {

    @Autowired
    private ChatMapper chatMapper;

    @MockBean
    private BasicMapper basicMapper;

    @MockBean
    private ChatService chatService;

    @Test
    public void getChatById() {
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
    public void getUserChats() {
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
    public void createChat() {
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
        ChatResponse.ParticipantResponse participantResponse1 = new ChatResponse.ParticipantResponse();
        participantResponse1.setId(1L);
        participantResponse1.setUser(new ChatUserParticipantResponse());
        participantResponse1.setLeftChat(false);
        ChatResponse.ParticipantResponse participantResponse2 = new ChatResponse.ParticipantResponse();
        participantResponse2.setId(2L);
        participantResponse2.setUser(new ChatUserParticipantResponse());
        participantResponse2.setLeftChat(false);
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setId(TestConstants.CHAT_ID);
        chatResponse.setCreationDate(LocalDateTime.now());
        chatResponse.setParticipants(Arrays.asList(participantResponse1, participantResponse2));
        return chatResponse;
    }
}