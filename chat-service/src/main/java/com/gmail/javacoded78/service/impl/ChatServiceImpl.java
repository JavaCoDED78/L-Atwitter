package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.response.chat.ChatTweetResponse;
import com.gmail.javacoded78.dto.response.chat.ChatUserParticipantResponse;
import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.dto.response.UserChatResponse;
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
import com.gmail.javacoded78.service.ChatService;
import com.gmail.javacoded78.service.util.ChatServiceHelper;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatServiceHelper chatServiceHelper;
    private final UserClient userClient;

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
        Boolean isUserExists = userClient.isUserExists(userId);

        if (!isUserExists) {
            throw new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        chatServiceHelper.isParticipantBlocked(authUserId, userId);
        Chat chat = chatRepository.getChatByParticipants(authUserId, userId);

        if (chat == null) {
            Chat newChat = new Chat();
            chatRepository.save(newChat);
            ChatParticipant authUserParticipant = chatParticipantRepository.save(new ChatParticipant(authUserId, newChat));
            ChatParticipant userParticipant = chatParticipantRepository.save(new ChatParticipant(userId, newChat));
            newChat.setParticipants(Arrays.asList(authUserParticipant, userParticipant));
            return chatRepository.getChatById(newChat.getId());
        }
        return chatRepository.getChatById(chat.getId());
    }
}
