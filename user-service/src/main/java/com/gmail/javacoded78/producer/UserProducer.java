package com.gmail.javacoded78.producer;

import com.gmail.javacoded78.event.UserEvent;
import com.gmail.javacoded78.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.gmail.javacoded78.constants.KafkaConstants.UPDATE_USER_TOPIC;

@Component
@RequiredArgsConstructor
public class UserProducer {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public void sendUserEvent(User user) {
        kafkaTemplate.send(UPDATE_USER_TOPIC, toUserEvent(user));
    }

    private static UserEvent toUserEvent(User user) {
        return UserEvent.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .privateProfile(user.isPrivateProfile())
                .build();
    }
}
