package com.gmail.javacoded78.consumer;

import com.gmail.javacoded78.event.BlockUserEvent;
import com.gmail.javacoded78.event.UpdateUserEvent;
import com.gmail.javacoded78.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.gmail.javacoded78.constants.KafkaTopicConstants.BLOCK_USER_TOPIC;
import static com.gmail.javacoded78.constants.KafkaTopicConstants.UPDATE_USER_TOPIC;

@Component
@RequiredArgsConstructor
public class UserConsumer {

    private final UserService userService;

    @KafkaListener(topics = UPDATE_USER_TOPIC, groupId = "topic")
    public void userUpdateListener(UpdateUserEvent updateUserEvent) {
        userService.handleUpdateUser(updateUserEvent);
    }

    @KafkaListener(topics = BLOCK_USER_TOPIC, groupId = "topic")
    public void userBlockListener(BlockUserEvent blockUserEvent) {
        // TODO update BlockUserEvent
//        userService.handleBlockUser(blockUserEvent);
    }
}
