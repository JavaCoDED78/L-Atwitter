package com.gmail.javacoded78.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.javacoded78.constants.PathConstants.API_V1_WEBSOCKET;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_WEBSOCKET)
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public void send(@RequestParam("destination") String destination, @RequestBody Object request) {
        messagingTemplate.convertAndSend(destination, request);
    }
}
