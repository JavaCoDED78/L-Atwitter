package com.gmail.javacoded78.producer;

import com.gmail.javacoded78.event.UpdateUserEvent;
import com.gmail.javacoded78.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.gmail.javacoded78.constants.KafkaTopicConstants.UPDATE_USER_TOPIC;

@Component
@RequiredArgsConstructor
public class UpdateUserProducer {

    private final KafkaTemplate<String, UpdateUserEvent> kafkaTemplate;

    public void sendUpdateUserEvent(User user) {
        kafkaTemplate.send(UPDATE_USER_TOPIC, toUpdateUserEvent(user));
    }

    private static UpdateUserEvent toUpdateUserEvent(User user) {
        return UpdateUserEvent.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .privateProfile(user.isPrivateProfile())
                .build();
    }
}
