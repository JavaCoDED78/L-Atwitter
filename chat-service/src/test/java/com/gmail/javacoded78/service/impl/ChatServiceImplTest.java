package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.model.Chat;
import com.gmail.javacoded78.model.ChatParticipant;
import com.gmail.javacoded78.repository.ChatParticipantRepository;
import com.gmail.javacoded78.repository.ChatRepository;
import com.gmail.javacoded78.repository.projection.ChatProjection;
import com.gmail.javacoded78.service.ChatServiceTestHelper;
import com.gmail.javacoded78.util.TestConstants;
import com.gmail.javacoded78.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_PARTICIPANT_BLOCKED;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ChatServiceImplTest {

    @Autowired
    private ChatServiceImpl chatService;

    @MockBean
    private ChatRepository chatRepository;

    @MockBean
    private ChatParticipantRepository chatParticipantRepository;

    @MockBean
    private UserClient userClient;

    @Before
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    public void getChatById() {
        ChatProjection mockChatProjection = ChatServiceTestHelper.createMockChatProjection();
        when(chatRepository.getChatById(TestConstants.CHAT_ID, TestConstants.USER_ID, ChatProjection.class))
                .thenReturn(Optional.of(mockChatProjection));
        assertEquals(mockChatProjection, chatService.getChatById(TestConstants.CHAT_ID));
        verify(chatRepository, times(1)).getChatById(TestConstants.CHAT_ID, TestConstants.USER_ID, ChatProjection.class);
    }

    @Test
    public void getChatById_ShouldChatNotFound() {
        when(chatRepository.getChatById(TestConstants.CHAT_ID, TestConstants.USER_ID, ChatProjection.class))
                .thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatService.getChatById(TestConstants.CHAT_ID));
        assertEquals(CHAT_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void getUserChats() {
        ChatProjection mockChatProjection1 = ChatServiceTestHelper.createMockChatProjection();
        ChatProjection mockChatProjection2 = ChatServiceTestHelper.createMockChatProjection();
        List<ChatProjection> mockChatProjections = List.of(mockChatProjection1, mockChatProjection2);
        when(chatRepository.getChatsByUserId(TestConstants.USER_ID)).thenReturn(mockChatProjections);
        assertEquals(mockChatProjections, chatService.getUserChats());
        verify(chatRepository, times(1)).getChatsByUserId(TestConstants.USER_ID);
    }

    @Test
    public void createChat() {
        Chat newChat = new Chat();
        newChat.setId(TestConstants.CHAT_ID);
        when(userClient.isUserExists(1L)).thenReturn(true);
        when(userClient.isUserBlockedByMyProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(false);
        when(chatRepository.getChatByParticipants(TestConstants.USER_ID, 1L)).thenReturn(null);
        when(chatParticipantRepository.save(new ChatParticipant(TestConstants.USER_ID, newChat)))
                .thenReturn(ChatServiceTestHelper.createMockChatParticipant(1L, TestConstants.USER_ID, newChat));
        when(chatParticipantRepository.save(new ChatParticipant(1L, newChat)))
                .thenReturn(ChatServiceTestHelper.createMockChatParticipant(1L, 1L, newChat));
        when(chatRepository.getChatById(newChat.getId())).thenReturn(ChatServiceTestHelper.createMockChatProjection());
        assertNull(chatService.createChat(1L));
        verify(userClient, times(1)).isUserExists(1L);
        verify(userClient, times(1)).isUserBlockedByMyProfile(TestConstants.USER_ID);
        verify(userClient, times(1)).isMyProfileBlockedByUser(1L);
        verify(chatRepository, times(1)).getChatByParticipants(TestConstants.USER_ID, 1L);
        verify(chatRepository, times(1)).save(any());
    }

    @Test
    public void createChat_ShouldReturnChatProjection() {
        Chat newChat = new Chat();
        newChat.setId(TestConstants.CHAT_ID);
        ChatProjection mockChatProjection = ChatServiceTestHelper.createMockChatProjection();
        when(userClient.isUserExists(1L)).thenReturn(true);
        when(userClient.isUserBlockedByMyProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(false);
        when(chatRepository.getChatByParticipants(TestConstants.USER_ID, 1L))
                .thenReturn(ChatServiceTestHelper.createMockChat(false));
        when(chatRepository.getChatById(TestConstants.CHAT_ID)).thenReturn(mockChatProjection);
        assertEquals(mockChatProjection, chatService.createChat(1L));
        verify(userClient, times(1)).isUserExists(1L);
        verify(userClient, times(1)).isUserBlockedByMyProfile(TestConstants.USER_ID);
        verify(userClient, times(1)).isMyProfileBlockedByUser(1L);
        verify(chatRepository, times(1)).getChatByParticipants(TestConstants.USER_ID, 1L);
        verify(chatRepository, times(1)).getChatById(TestConstants.CHAT_ID);
    }

    @Test
    public void createChat_ShouldUserNotFound() {
        when(userClient.isUserExists(1L)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatService.createChat(1L));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void createChat_ShouldUserBlockedByMyProfile() {
        when(userClient.isUserExists(1L)).thenReturn(true);
        when(userClient.isUserBlockedByMyProfile(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> chatService.createChat(1L));
        assertEquals(CHAT_PARTICIPANT_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}