package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.UserChatResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.model.Chat;
import com.gmail.javacoded78.repository.ChatMessageRepository;
import com.gmail.javacoded78.repository.ChatParticipantRepository;
import com.gmail.javacoded78.repository.ChatRepository;
import com.gmail.javacoded78.service.ChatServiceTestHelper;
import com.gmail.javacoded78.util.TestConstants;
import com.gmail.javacoded78.util.TestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_PARTICIPANT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class ChatParticipantServiceImplTest {

    private final ChatParticipantServiceImpl chatParticipantService;

    @MockBean
    private final ChatRepository chatRepository;

    @MockBean
    private final ChatParticipantRepository chatParticipantRepository;

    @MockBean
    private final ChatMessageRepository chatMessageRepository;

    @MockBean
    private final UserClient userClient;

    @BeforeEach
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    void getParticipant() {
        when(chatRepository.isChatExists(TestConstants.CHAT_ID, TestConstants.USER_ID)).thenReturn(true);
        when(chatParticipantRepository.getParticipantUserId(1L, TestConstants.CHAT_ID)).thenReturn(Optional.of(1L));
        when(userClient.getUserResponseById(1L)).thenReturn(new UserResponse());
        assertEquals(new UserResponse(), chatParticipantService.getParticipant(1L, TestConstants.CHAT_ID));
        verify(chatRepository, times(1)).isChatExists(TestConstants.CHAT_ID, TestConstants.USER_ID);
        verify(chatParticipantRepository, times(1)).getParticipantUserId(1L, TestConstants.CHAT_ID);
        verify(userClient, times(1)).getUserResponseById(1L);
    }

    @Test
    void getParticipant_ShouldReturnChatNotFound() {
        when(chatRepository.isChatExists(TestConstants.CHAT_ID, TestConstants.USER_ID)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatParticipantService.getParticipant(1L, TestConstants.CHAT_ID));
        assertEquals(CHAT_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getParticipant_ShouldChatParticipantNotFound() {
        when(chatRepository.isChatExists(TestConstants.CHAT_ID, TestConstants.USER_ID)).thenReturn(true);
        when(chatParticipantRepository.getParticipantUserId(1L, TestConstants.CHAT_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatParticipantService.getParticipant(1L, TestConstants.CHAT_ID));
        assertEquals(CHAT_PARTICIPANT_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void leaveFromConversation() {
        when(chatRepository.findById(TestConstants.CHAT_ID)).thenReturn(Optional.of(ChatServiceTestHelper.createMockChat(false)));
        when(chatParticipantRepository.leaveFromConversation(1L, TestConstants.CHAT_ID)).thenReturn(1);
        assertEquals("Successfully left the chat", chatParticipantService.leaveFromConversation(1L, TestConstants.CHAT_ID));
        verify(chatRepository, times(1)).findById(TestConstants.CHAT_ID);
        verify(chatParticipantRepository, times(1)).leaveFromConversation(1L, TestConstants.CHAT_ID);
    }

    @Test
    void leaveFromConversation_ShouldDeleteChat() {
        Chat mockChat = ChatServiceTestHelper.createMockChat(true);
        when(chatRepository.findById(TestConstants.CHAT_ID)).thenReturn(Optional.of(mockChat));
        when(chatParticipantRepository.leaveFromConversation(1L, TestConstants.CHAT_ID)).thenReturn(1);
        assertEquals("Chat successfully deleted", chatParticipantService.leaveFromConversation(1L, TestConstants.CHAT_ID));
        verify(chatRepository, times(1)).findById(TestConstants.CHAT_ID);
        verify(chatParticipantRepository, times(1)).leaveFromConversation(1L, TestConstants.CHAT_ID);
        verify(chatMessageRepository, times(1)).deleteAll(mockChat.getMessages());
        verify(chatParticipantRepository, times(1)).deleteAll(mockChat.getParticipants());
        verify(chatRepository, times(1)).delete(mockChat);
    }

    @Test
    void leaveFromConversation_ShouldChatNotFound() {
        when(chatRepository.findById(TestConstants.CHAT_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatParticipantService.leaveFromConversation(1L, TestConstants.CHAT_ID));
        assertEquals(CHAT_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void leaveFromConversation_ShouldChatParticipantNotFound() {
        when(chatRepository.findById(TestConstants.CHAT_ID)).thenReturn(Optional.of(ChatServiceTestHelper.createMockChat(false)));
        when(chatParticipantRepository.leaveFromConversation(1L, TestConstants.CHAT_ID)).thenReturn(0);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatParticipantService.leaveFromConversation(1L, TestConstants.CHAT_ID));
        assertEquals(CHAT_PARTICIPANT_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void searchUsersByUsername() {
        PageRequest pageable = PageRequest.of(0, 20);
        HeaderResponse<UserChatResponse> headerResponse = new HeaderResponse<>(
                List.of(new UserChatResponse(), new UserChatResponse()), new HttpHeaders());
        when(userClient.searchUsersByUsername("test username", pageable)).thenReturn(headerResponse);
        when(chatRepository.getChatByParticipants(TestConstants.USER_ID, 1L)).thenReturn(new Chat());
        when(chatRepository.getChatByParticipants(TestConstants.USER_ID, 2L)).thenReturn(new Chat());
        assertEquals(headerResponse, chatParticipantService.searchUsersByUsername("test username", pageable));
        verify(userClient, times(1)).searchUsersByUsername("test username", pageable);
    }
}