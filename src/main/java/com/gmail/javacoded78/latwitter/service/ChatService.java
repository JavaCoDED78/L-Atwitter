package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.ChatMessage;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.projection.chat.ChatMessageProjection;
import com.gmail.javacoded78.latwitter.repository.projection.chat.ChatProjection;

import java.util.List;
import java.util.Map;

public interface ChatService {

    List<ChatProjection> getUserChats();

    ChatProjection createChat(Long userId);

    List<ChatMessageProjection> getChatMessages(Long chatId);

    Integer readChatMessages(Long chatId);

    Map<String, Object> addMessage(ChatMessage chatMessage, Long chatId);

    List<ChatMessage> addMessageWithTweet(String text, Tweet tweet, List<User> users);

    User getParticipant(Long participantId, Long chatId);

    String leaveFromConversation(Long participantId, Long chatId);
}
