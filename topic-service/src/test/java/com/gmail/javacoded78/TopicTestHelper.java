package com.gmail.javacoded78;

import com.gmail.javacoded78.enums.TopicCategory;
import com.gmail.javacoded78.repository.projetion.FollowedTopicProjection;
import com.gmail.javacoded78.repository.projetion.NotInterestedTopicProjection;
import com.gmail.javacoded78.repository.projetion.TopicProjection;
import lombok.experimental.UtilityClass;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@UtilityClass
public class TopicTestHelper {

    private static final ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    public static List<TopicProjection> getMockTopicProjectionList() {
        TopicProjection topic1 = factory.createProjection(
                TopicProjection.class,
                Map.of(
                        "id", 1L,
                        "topicName", "test topic 1",
                        "topicCategory", TopicCategory.TRAVEL));
        TopicProjection topic2 = factory.createProjection(
                TopicProjection.class,
                Map.of(
                        "id", 2L,
                        "topicName", "test topic 2",
                        "topicCategory", TopicCategory.FOOD));
        return Arrays.asList(topic1, topic2);
    }

    public static List<FollowedTopicProjection> getMockFollowedTopicProjectionList() {
        FollowedTopicProjection topic1 = factory.createProjection(
                FollowedTopicProjection.class,
                Map.of(
                        "id", 1L,
                        "topicName", "test topic 1",
                        "topicCategory", TopicCategory.TRAVEL,
                        "isTopicFollowed", true));
        FollowedTopicProjection topic2 = factory.createProjection(
                FollowedTopicProjection.class,
                Map.of(
                        "id", 2L,
                        "topicName", "test topic 2",
                        "topicCategory", TopicCategory.FOOD,
                        "isTopicFollowed", true));
        return Arrays.asList(topic1, topic2);
    }

    public static List<NotInterestedTopicProjection> getMockNotInterestedTopicProjectionList() {
        NotInterestedTopicProjection topic1 = factory.createProjection(
                NotInterestedTopicProjection.class,
                Map.of(
                        "id", 1L,
                        "topicName", "test topic 1",
                        "topicCategory", TopicCategory.TRAVEL,
                        "isTopicNotInterested", true));
        NotInterestedTopicProjection topic2 = factory.createProjection(
                NotInterestedTopicProjection.class,
                Map.of(
                        "id", 2L,
                        "topicName", "test topic 2",
                        "topicCategory", TopicCategory.FOOD,
                        "isTopicNotInterested", true));
        return Arrays.asList(topic1, topic2);
    }

    public static TopicProjection getMockTopicProjection() {
        return factory.createProjection(
                TopicProjection.class,
                Map.of(
                        "id", 1L,
                        "topicName", "test topic 1",
                        "topicCategory", TopicCategory.TRAVEL,
                        "isTopicFollowed", true,
                        "isTopicNotInterested", false
                ));
    }
}
