package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.ChatTweetResponse;
import com.gmail.javacoded78.dto.ChatUserParticipantResponse;
import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.UserResponse;
import com.gmail.javacoded78.dto.lists.UserIdsRequest;
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
import com.gmail.javacoded78.repository.projection.ChatParticipantProjection;
import com.gmail.javacoded78.repository.projection.ChatProjection;
import com.gmail.javacoded78.service.ChatService;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserClient userClient;
    private final TweetClient tweetClient;

    @Override
    public ChatProjection getChatById(Long chatId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return chatRepository.getChatById(chatId, authUserId, ChatProjection.class)
                .orElseThrow(() -> new ApiRequestException("Chat not found", HttpStatus.NOT_FOUND));
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
            throw new ApiRequestException("User not found", HttpStatus.BAD_REQUEST);
        }
        isParticipantBlocked(authUserId, userId);
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

    @Override
    public List<ChatMessageProjection> getChatMessages(Long chatId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        chatRepository.getChatById(chatId, authUserId, ChatProjection.class)
                .orElseThrow(() -> new ApiRequestException("Chat not found", HttpStatus.NOT_FOUND));
        return chatMessageRepository.getChatMessages(chatId);
    }

    @Override
    @Transactional
    public Long readChatMessages(Long chatId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        chatMessageRepository.readChatMessages(chatId, authUserId);
        List<Long> chatIds = chatRepository.getChatIdsByUserId(authUserId);
        return chatMessageRepository.getUnreadMessagesCount(chatIds, authUserId);
    }

    @Override
    @Transactional
    public Map<Long, ChatMessageProjection> addMessage(ChatMessage chatMessage, Long chatId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        Chat chat = chatRepository.getChatById(chatId, authUserId, Chat.class)
                .orElseThrow(() -> new ApiRequestException("Chat not found", HttpStatus.NOT_FOUND));
        ChatParticipant chatParticipant = chat.getParticipants().stream()
                .filter(participant -> !participant.getUserId().equals(authUserId))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException("Chat participant not found", HttpStatus.NOT_FOUND));
        isParticipantBlocked(authUserId, chatParticipant.getUserId());
        chatMessage.setAuthorId(authUserId);
        chatMessage.setChat(chat);
        chatMessageRepository.save(chatMessage);
        chatParticipantRepository.updateParticipantWhoLeftChat(chatParticipant.getUserId(), chatId);
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
            throw new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND);
        }
        List<Long> validUserIds = userClient.validateChatUsersIds(new UserIdsRequest(usersIds));
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

    @Override
    public UserResponse getParticipant(Long participantId, Long chatId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        boolean isChatExists = chatRepository.isChatExists(chatId, authUserId);

        if (!isChatExists) {
            throw new ApiRequestException("Chat not found", HttpStatus.NOT_FOUND);
        }
        Long userId = chatParticipantRepository.getParticipantUserId(participantId, chatId)
                .orElseThrow(() -> new ApiRequestException("Participant not found", HttpStatus.NOT_FOUND));
        return userClient.getUserResponseById(userId);
    }

    @Override
    @Transactional
    public String leaveFromConversation(Long participantId, Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ApiRequestException("Chat not found", HttpStatus.NOT_FOUND));
        int isChatParticipantUpdated = chatParticipantRepository.leaveFromConversation(participantId, chatId);

        if (isChatParticipantUpdated != 1) {
            throw new ApiRequestException("Participant not found", HttpStatus.NOT_FOUND);
        }

        boolean isParticipantsLeftFromChat = chat.getParticipants().stream().allMatch(ChatParticipant::isLeftChat);

        if (isParticipantsLeftFromChat) {
            chatMessageRepository.deleteAll(chat.getMessages());
            chatParticipantRepository.deleteAll(chat.getParticipants());
            chatRepository.delete(chat);
            return "Chat successfully deleted";
        }
        return "Successfully left the chat";
    }

    @Override
    public HeaderResponse<UserChatResponse> searchUsersByUsername(String username, Pageable pageable) {
        return userClient.searchUsersByUsername(username, pageable);
    }

    public ChatUserParticipantResponse getChatParticipant(Long userId) {
        return userClient.getChatParticipant(userId);
    }

    private void isParticipantBlocked(Long authUserId, Long userId) {
        Boolean isUserBlockedByMyProfile = userClient.isUserBlockedByMyProfile(authUserId);
        Boolean isMyProfileBlockedByUser = userClient.isMyProfileBlockedByUser(userId);

        if (isUserBlockedByMyProfile || isMyProfileBlockedByUser) {
            throw new ApiRequestException("Participant is blocked", HttpStatus.BAD_REQUEST);
        }
    }

    public ChatTweetResponse getChatTweet(Long tweetId) {
        return tweetClient.getChatTweet(tweetId);
    }
}
