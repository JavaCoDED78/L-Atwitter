package com.gmail.javacoded78.latwitter.mapper;

import com.gmail.javacoded78.latwitter.dto.request.ChatMessageRequest;
import com.gmail.javacoded78.latwitter.dto.request.MessageWithTweetRequest;
import com.gmail.javacoded78.latwitter.dto.response.ChatMessageResponse;
import com.gmail.javacoded78.latwitter.dto.response.ChatResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.model.Chat;
import com.gmail.javacoded78.latwitter.model.ChatMessage;
import com.gmail.javacoded78.latwitter.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatMapper {

    private final ModelMapper modelMapper;
    private final ChatService chatService;
    private final UserMapper userMapper;

    private ChatMessage convertToChatMessageEntity(ChatMessageRequest chatMessageRequest) {
        return modelMapper.map(chatMessageRequest, ChatMessage.class);
    }

    private ChatResponse convertToChatResponse(Chat chat) {
        return modelMapper.map(chat, ChatResponse.class);
    }

    private List<ChatResponse> convertListToChatResponse(List<Chat> chats) {
        return chats.stream()
                .map(this::convertToChatResponse)
                .collect(Collectors.toList());
    }

    private ChatMessageResponse convertToChatMessageResponse(ChatMessage chatMessage) {
        return modelMapper.map(chatMessage, ChatMessageResponse.class);
    }

    private List<ChatMessageResponse> convertListToChatMessageResponse(List<ChatMessage> chatMessages) {
        return chatMessages.stream()
                .map(this::convertToChatMessageResponse)
                .collect(Collectors.toList());
    }

    public List<ChatResponse> getUserChats() {
        return convertListToChatResponse(chatService.getUserChats());
    }

    public ChatResponse createChat(Long userId) {
        return convertToChatResponse(chatService.createChat(userId));
    }

    public List<ChatMessageResponse> getChatMessages(Long chatId) {
        return convertListToChatMessageResponse(chatService.getChatMessages(chatId));
    }

    public UserResponse readChatMessages(Long chatId) {
        return userMapper.convertToUserResponse(chatService.readChatMessages(chatId));
    }

    public ChatMessageResponse addMessage(ChatMessageRequest chatMessageRequest) {
        return convertToChatMessageResponse(chatService.addMessage(
                convertToChatMessageEntity(chatMessageRequest), chatMessageRequest.getChatId()));
    }

    public List<ChatMessageResponse> addMessageWithTweet(MessageWithTweetRequest request) {
        return convertListToChatMessageResponse(chatService.addMessageWithTweet(
                request.getText(), request.getTweet(), request.getUsers()));
    }

    public String leaveFromConversation(Long participantId, Long chatId) {
        return chatService.leaveFromConversation(participantId, chatId);
    }
}
