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
import com.gmail.javacoded78.service.ChatMessageService;
import com.gmail.javacoded78.service.util.ChatServiceHelper;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_PARTICIPANT_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatRepository chatRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatServiceHelper chatServiceHelper;
    private final UserClient userClient;
    private final TweetClient tweetClient;

    @Override
    public List<ChatMessageProjection> getChatMessages(Long chatId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        chatRepository.getChatById(chatId, authUserId, ChatProjection.class)
                .orElseThrow(() -> new ApiRequestException(CHAT_NOT_FOUND, HttpStatus.NOT_FOUND));
        return chatMessageRepository.getChatMessages(chatId);
    }

    @Override
    @Transactional
    public Long readChatMessages(Long chatId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        chatRepository.getChatById(chatId, authUserId, ChatProjection.class)
                .orElseThrow(() -> new ApiRequestException(CHAT_NOT_FOUND, HttpStatus.NOT_FOUND));
        chatMessageRepository.readChatMessages(chatId, authUserId);
        return chatMessageRepository.getUnreadMessagesCount(authUserId);
    }

    @Override
    @Transactional
    public Map<Long, ChatMessageProjection> addMessage(ChatMessage chatMessage, Long chatId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        Chat chat = chatRepository.getChatById(chatId, authUserId, Chat.class)
                .orElseThrow(() -> new ApiRequestException(CHAT_NOT_FOUND, HttpStatus.NOT_FOUND));
        Long chatParticipantId = chatParticipantRepository.getChatParticipantId(authUserId, chatId);
        chatServiceHelper.isParticipantBlocked(authUserId, chatParticipantId);
        chatMessage.setAuthorId(authUserId);
        chatMessage.setChat(chat);
        chatMessageRepository.save(chatMessage);
        chatParticipantRepository.updateParticipantWhoLeftChat(chatParticipantId, chatId);
        chat.getMessages().add(chatMessage);
        ChatMessageProjection message = chatMessageRepository.getChatMessageById(chatMessage.getId()).get();
        Map<Long, ChatMessageProjection> chatParticipants = new HashMap<>();
        chatParticipantRepository.getChatParticipantIds(chatId)
                .forEach(userId -> chatParticipants.put(userId, message));
        return chatParticipants;
    }

    @Override
    @Transactional
    public Map<Long, ChatMessageProjection> addMessageWithTweet(String text, Long tweetId, List<Long> usersIds) {
        if (!tweetClient.isTweetExists(tweetId)) {
            throw new ApiRequestException(TWEET_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        List<Long> validUserIds = userClient.validateChatUsersIds(new IdsRequest(usersIds));
        Map<Long, ChatMessageProjection> chatParticipants = new HashMap<>();
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        validUserIds.forEach(userId -> {
            ChatMessage chatMessage = new ChatMessage(text, tweetId, authUserId);
            Chat chat = chatRepository.getChatByParticipants(authUserId, userId);
            Boolean isUserBlockedByMyProfile = userClient.isMyProfileBlockedByUser(userId);

            if (chat == null && !isUserBlockedByMyProfile) {
                Chat newChat = new Chat();
                chatRepository.save(newChat);
                ChatParticipant authorParticipant = chatParticipantRepository.save(new ChatParticipant(authUserId, newChat));
                ChatParticipant userParticipant = chatParticipantRepository.save(new ChatParticipant(userId, newChat));
                newChat.setParticipants(List.of(authorParticipant, userParticipant));
                chatMessage.setChat(newChat);
                chatMessageRepository.save(chatMessage);
            } else if (!isUserBlockedByMyProfile) {
                chatMessage.setChat(chat);
                chatMessageRepository.save(chatMessage);
                chatParticipantRepository.updateParticipantWhoLeftChat(userId, chat.getId());
                chat.getMessages().add(chatMessage);
            }
            ChatMessageProjection message = chatMessageRepository.getChatMessageById(chatMessage.getId()).get();
            chatParticipants.put(userId, message);
        });
        return chatParticipants;
    }
}
