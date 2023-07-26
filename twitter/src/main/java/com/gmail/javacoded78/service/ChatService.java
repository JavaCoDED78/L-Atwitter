package com.gmail.javacoded78.service;

import com.gmail.javacoded78.model.ChatMessage;
import com.gmail.javacoded78.repository.projection.chat.ChatMessageProjection;
import com.gmail.javacoded78.repository.projection.chat.ChatProjection;
import com.gmail.javacoded78.repository.projection.user.UserProjection;

import java.util.List;
import java.util.Map;

public interface ChatService {

    ChatProjection getChatById(Long chatId);

    List<ChatProjection> getUserChats();

    ChatProjection createChat(Long userId);

    List<ChatMessageProjection> getChatMessages(Long chatId);

    Integer readChatMessages(Long chatId);

    Map<String, Object> addMessage(ChatMessage chatMessage, Long chatId);

    Map<String, Object> addMessageWithTweet(String text, Long tweetId, List<Long> usersIds);

    UserProjection getParticipant(Long participantId, Long chatId);

    String leaveFromConversation(Long participantId, Long chatId);
}
