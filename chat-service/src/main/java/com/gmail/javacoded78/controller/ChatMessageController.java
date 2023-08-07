package com.gmail.javacoded78.controller;

import com.gmail.javacoded78.dto.request.MessageWithTweetRequest;
import com.gmail.javacoded78.dto.response.ChatMessageResponse;
import com.gmail.javacoded78.feign.WebSocketClient;
import com.gmail.javacoded78.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.ADD_MESSAGE;
import static com.gmail.javacoded78.constants.PathConstants.ADD_MESSAGE_TWEET;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_ID_MESSAGES;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_ID_READ_MESSAGES;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_CHAT;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_CHAT;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_CHAT)
public class ChatMessageController {

    private final ChatMessageMapper chatMessageMapper;
    private final WebSocketClient webSocketClient;

    @GetMapping(CHAT_ID_MESSAGES)
    public ResponseEntity<List<ChatMessageResponse>> getChatMessages(@PathVariable("chatId") Long chatId) {
        return ResponseEntity.ok(chatMessageMapper.getChatMessages(chatId));
    }

    @GetMapping(CHAT_ID_READ_MESSAGES)
    public ResponseEntity<Long> readChatMessages(@PathVariable("chatId") Long chatId) {
        return ResponseEntity.ok(chatMessageMapper.readChatMessages(chatId));
    }

    @PostMapping(ADD_MESSAGE)
    public ResponseEntity<Void> addMessage(@RequestBody ChatMessageRequest request) {
        chatMessageMapper.addMessage(request)
                .forEach((userId, message) -> webSocketClient.send(TOPIC_CHAT + userId, message));
        return ResponseEntity.ok().build();
    }

    @PostMapping(ADD_MESSAGE_TWEET)
    public ResponseEntity<Void> addMessageWithTweet(@RequestBody MessageWithTweetRequest request) {
        chatMessageMapper.addMessageWithTweet(request)
                .forEach((userId, message) -> webSocketClient.send(TOPIC_CHAT + userId, message));
        return ResponseEntity.ok().build();
    }
}
