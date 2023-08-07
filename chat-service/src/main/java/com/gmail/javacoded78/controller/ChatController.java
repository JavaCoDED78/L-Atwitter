package com.gmail.javacoded78.controller;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.dto.request.ChatMessageRequest;
import com.gmail.javacoded78.dto.request.MessageWithTweetRequest;
import com.gmail.javacoded78.dto.response.ChatMessageResponse;
import com.gmail.javacoded78.dto.response.ChatResponse;
import com.gmail.javacoded78.dto.response.UserChatResponse;
import com.gmail.javacoded78.feign.WebSocketClient;
import com.gmail.javacoded78.mapper.ChatMapper;
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

import static com.gmail.javacoded78.constants.PathConstants.ADD_MESSAGE;
import static com.gmail.javacoded78.constants.PathConstants.ADD_MESSAGE_TWEET;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_ID;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_ID_MESSAGES;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_ID_READ_MESSAGES;
import static com.gmail.javacoded78.constants.PathConstants.CREATE_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.LEAVE_CHAT_ID;
import static com.gmail.javacoded78.constants.PathConstants.PARTICIPANT_CHAT_ID;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_USERNAME;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_CHAT;
import static com.gmail.javacoded78.constants.PathConstants.USERS;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_CHAT;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_CHAT)
public class ChatController {

    private final ChatMapper chatMapper;

    @GetMapping(CHAT_ID)
    public ResponseEntity<ChatResponse> getChatById(@PathVariable("chatId") Long chatId) {
        return ResponseEntity.ok(chatMapper.getChatById(chatId));
    }

    @GetMapping(USERS)
    public ResponseEntity<List<ChatResponse>> getUserChats() {
        return ResponseEntity.ok(chatMapper.getUserChats());
    }

    @GetMapping(CREATE_USER_ID)
    public ResponseEntity<ChatResponse> createChat(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(chatMapper.createChat(userId));
    }
}
