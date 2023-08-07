package com.gmail.javacoded78.service;

import com.gmail.javacoded78.repository.projection.ChatProjection;

import java.util.List;

public interface ChatService {

    ChatProjection getChatById(Long chatId);

    List<ChatProjection> getUserChats();

    ChatProjection createChat(Long userId);
}
