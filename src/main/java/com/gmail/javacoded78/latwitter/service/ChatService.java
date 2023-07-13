package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Chat;
import com.gmail.javacoded78.latwitter.model.ChatMessage;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;

import java.util.List;

public interface ChatService {

    List<Chat> getUserChats();

    Chat createChat(Long userId);

    List<ChatMessage> getChatMessages(Long chatId);

    User readChatMessages(Long chatId);

    ChatMessage addMessage(ChatMessage chatMessage, Long chatId);

    List<ChatMessage> addMessageWithTweet(String text, Tweet tweet, List<User> users);
}
