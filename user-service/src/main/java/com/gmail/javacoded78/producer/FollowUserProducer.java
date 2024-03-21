package com.gmail.javacoded78.producer;

import com.gmail.javacoded78.event.FollowUserEvent;
import com.gmail.javacoded78.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.gmail.javacoded78.constants.KafkaTopicConstants.FOLLOW_USER_TOPIC;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;

@Component
@RequiredArgsConstructor
public class FollowUserProducer {

    private final KafkaTemplate<String, FollowUserEvent> kafkaTemplate;

    public void sendFollowUserEvent(User user, Long authUserId, boolean hasUserFollowed) {
        ProducerRecord<String, FollowUserEvent> producerRecord = new ProducerRecord<>(FOLLOW_USER_TOPIC, toFollowUserEvent(user, hasUserFollowed));
        producerRecord.headers().add(AUTH_USER_ID_HEADER, authUserId.toString().getBytes());
        kafkaTemplate.send(producerRecord);
    }

    private static FollowUserEvent toFollowUserEvent(User user, boolean hasUserFollowed) {
        return FollowUserEvent.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .privateProfile(user.isPrivateProfile())
                .userFollow(hasUserFollowed)
                .build();
    }
}
