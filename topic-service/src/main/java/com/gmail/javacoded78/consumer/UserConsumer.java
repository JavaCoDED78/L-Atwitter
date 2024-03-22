package com.gmail.javacoded78.consumer;

import com.gmail.javacoded78.event.BlockUserEvent;
import com.gmail.javacoded78.event.FollowUserEvent;
import com.gmail.javacoded78.event.UpdateUserEvent;
import com.gmail.javacoded78.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static com.gmail.javacoded78.constants.KafkaTopicConstants.BLOCK_USER_TOPIC;
import static com.gmail.javacoded78.constants.KafkaTopicConstants.FOLLOW_USER_TOPIC;
import static com.gmail.javacoded78.constants.KafkaTopicConstants.UPDATE_USER_TOPIC;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;

@Component
@RequiredArgsConstructor
public class UserConsumer {

    private final UserService userService;

    @KafkaListener(topics = UPDATE_USER_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void userUpdateListener(UpdateUserEvent updateUserEvent) {
        userService.handleUpdateUser(updateUserEvent);
    }

    @KafkaListener(topics = BLOCK_USER_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void userBlockListener(BlockUserEvent blockUserEvent, @Header(AUTH_USER_ID_HEADER) String authId) {
        userService.handleBlockUser(blockUserEvent, authId);
    }

    @KafkaListener(topics = FOLLOW_USER_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void userFollowListener(FollowUserEvent followUserEvent, @Header(AUTH_USER_ID_HEADER) String authId) {
        userService.handleFollowUser(followUserEvent, authId);
    }
}
