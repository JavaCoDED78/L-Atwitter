package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.model.Chat;
import com.gmail.javacoded78.model.ChatParticipant;
import com.gmail.javacoded78.repository.ChatParticipantRepository;
import com.gmail.javacoded78.repository.ChatRepository;
import com.gmail.javacoded78.repository.projection.ChatProjection;
import com.gmail.javacoded78.service.ChatService;
import com.gmail.javacoded78.service.util.ChatServiceHelper;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatServiceHelper chatServiceHelper;

    @Override
    public ChatProjection getChatById(Long chatId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return chatRepository.getChatById(chatId, authUserId, ChatProjection.class)
                .orElseThrow(() -> new ApiRequestException(CHAT_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<ChatProjection> getUserChats() {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return chatRepository.getChatsByUserId(authUserId);
    }

    @Override
    @Transactional
    public ChatProjection createChat(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        chatServiceHelper.isUserExists(userId);
        chatServiceHelper.isParticipantBlocked(authUserId, userId);
        Chat chat = chatRepository.getChatByParticipants(authUserId, userId);

        if (chat == null) {
            Chat newChat = new Chat();
            chatRepository.save(newChat);
            ChatParticipant authUserParticipant = chatParticipantRepository.save(ChatParticipant.builder()
                    .userId(authUserId)
                    .chat(newChat)
                    .build());
            ChatParticipant userParticipant = chatParticipantRepository.save(ChatParticipant.builder()
                    .userId(userId)
                    .chat(newChat)
                    .build());
            newChat.setParticipants(Arrays.asList(authUserParticipant, userParticipant));
            return chatRepository.getChatById(newChat.getId());
        }
        return chatRepository.getChatById(chat.getId());
    }
}
