package com.gmail.javacoded78.latwitter.controller;

import com.gmail.javacoded78.latwitter.dto.request.ChatMessageRequest;
import com.gmail.javacoded78.latwitter.dto.request.MessageWithTweetRequest;
import com.gmail.javacoded78.latwitter.dto.response.ChatMessageResponse;
import com.gmail.javacoded78.latwitter.dto.response.ChatResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatMapper chatMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/users")
    public ResponseEntity<List<ChatResponse>> getUserChats() {
        return ResponseEntity.ok(chatMapper.getUserChats());
    }

    @GetMapping("/create/{userId}")
    public ResponseEntity<ChatResponse> createChat(@PathVariable Long userId) {
        return ResponseEntity.ok(chatMapper.createChat(userId));
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<ChatMessageResponse>> getChatMessages(@PathVariable Long chatId) {
        return ResponseEntity.ok(chatMapper.getChatMessages(chatId));
    }

    @GetMapping("/{chatId}/read/messages")
    public ResponseEntity<UserResponse> readChatMessages(@PathVariable Long chatId) {
        return ResponseEntity.ok(chatMapper.readChatMessages(chatId));
    }

    @PostMapping("/add/message")
    public ResponseEntity<ChatMessageResponse> addMessage(@RequestBody ChatMessageRequest chatMessage) {
        ChatMessageResponse message = chatMapper.addMessage(chatMessage);
        message.getChat().getParticipants()
                .forEach(user -> messagingTemplate.convertAndSend("/topic/chat/" + user.getId(), message));
        return ResponseEntity.ok(message);
    }

    @PostMapping("/add/message/tweet")
    public ResponseEntity<List<ChatMessageResponse>> addMessageWithTweet(@RequestBody MessageWithTweetRequest request) {
        List<ChatMessageResponse> chatMessages = chatMapper.addMessageWithTweet(request);
        chatMessages.forEach(chatMessage -> chatMessage.getChat().getParticipants()
                .forEach(user -> messagingTemplate.convertAndSend("/topic/chat/" + user.getId(), chatMessage)));
        return ResponseEntity.ok(chatMessages);
    }

    @GetMapping("/participant/{participantId}/{chatId}")
    public ResponseEntity<UserResponse> getParticipant(@PathVariable Long participantId, @PathVariable Long chatId) {
        return ResponseEntity.ok(chatMapper.getParticipant(participantId, chatId));
    }

    @GetMapping("/leave/{participantId}/{chatId}")
    public ResponseEntity<String> leaveFromConversation(@PathVariable Long participantId, @PathVariable Long chatId) {
        return ResponseEntity.ok(chatMapper.leaveFromConversation(participantId, chatId));
    }
}