package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.dto.request.ChatMessageRequest;
import com.gmail.javacoded78.dto.request.MessageWithTweetRequest;
import com.gmail.javacoded78.dto.response.ChatMessageResponse;
import com.gmail.javacoded78.dto.response.ChatResponse;
import com.gmail.javacoded78.dto.response.UserChatResponse;
import com.gmail.javacoded78.model.ChatMessage;
import com.gmail.javacoded78.repository.projection.ChatMessageProjection;
import com.gmail.javacoded78.repository.projection.ChatProjection;
import com.gmail.javacoded78.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ChatMapper {

    private final BasicMapper basicMapper;
    private final ChatService chatService;

    public ChatResponse getChatById(Long chatId) {
        ChatProjection chat = chatService.getChatById(chatId);
        return basicMapper.convertToResponse(chat, ChatResponse.class);
    }

    public List<ChatResponse> getUserChats() {
        List<ChatProjection> chats = chatService.getUserChats();
        return basicMapper.convertToResponseList(chats, ChatResponse.class);
    }

    public ChatResponse createChat(Long userId) {
        ChatProjection chat = chatService.createChat(userId);
        return basicMapper.convertToResponse(chat, ChatResponse.class);
    }
}
