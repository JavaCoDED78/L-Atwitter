package com.gmail.javacoded78.latwitter.controller;

import com.gmail.javacoded78.latwitter.dto.request.ChatMessageRequest;
import com.gmail.javacoded78.latwitter.dto.request.MessageWithTweetRequest;
import com.gmail.javacoded78.latwitter.dto.response.HeaderResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserChatResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.dto.response.chats.ChatMessageResponse;
import com.gmail.javacoded78.latwitter.dto.response.chats.ChatResponse;
import com.gmail.javacoded78.latwitter.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatMapper chatMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatResponse> getChatById(@PathVariable Long chatId) {
        return ResponseEntity.ok(chatMapper.getChatById(chatId));
    }

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
    public ResponseEntity<Integer> readChatMessages(@PathVariable Long chatId) {
        return ResponseEntity.ok(chatMapper.readChatMessages(chatId));
    }

    @PostMapping("/add/message")
    public ResponseEntity<ChatMessageResponse> addMessage(@RequestBody ChatMessageRequest chatMessage) {
        ChatMessageResponse message = chatMapper.addMessage(chatMessage);
        message.getChatParticipantsIds()
                .forEach(userId -> messagingTemplate.convertAndSend("/topic/chat/" + userId, message));
        return ResponseEntity.ok(message);
    }

    @PostMapping("/add/message/tweet")
    public ResponseEntity<ChatMessageResponse> addMessageWithTweet(@RequestBody MessageWithTweetRequest request) {
        ChatMessageResponse message = chatMapper.addMessageWithTweet(request);
        message.getChatParticipantsIds()
                .forEach(userId -> messagingTemplate.convertAndSend("/topic/chat/" + userId, message));
        return ResponseEntity.ok(message);
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<List<UserChatResponse>> searchParticipantsByUsername(@PathVariable String username, @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserChatResponse> response = chatMapper.searchParticipantsByUsername(username, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
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