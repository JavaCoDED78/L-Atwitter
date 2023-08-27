package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.enums.TopicCategory;
import com.gmail.javacoded78.model.Topic;
import com.gmail.javacoded78.repository.projetion.NotInterestedTopicProjection;
import com.gmail.javacoded78.repository.projetion.TopicProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("""
            SELECT t FROM Topic t
            WHERE t.id IN :topicsIds
            ORDER BY t.id DESC
            """)
    List<TopicProjection> getTopicsByIds(@Param("topicsIds") List<Long> topicsIds);

    @Query("""
            SELECT t.id as id, t.topicName as topicName, t.topicCategory as topicCategory FROM Topic t
            WHERE t.id IN (
                    SELECT f.topicId FROM TopicFollowers f
                    WHERE f.userId = :userId)
            ORDER BY t.id DESC
            """)
    <T> List<T> getTopicsByTopicFollowerId(@Param("userId") Long userId, Class<T> type);

    @Query("""
            SELECT t.id as id, t.topicName as topicName, t.topicCategory as topicCategory FROM Topic t
            WHERE t.id IN (
                    SELECT ni.topicId FROM TopicNotInterested ni
                    WHERE ni.userId = :userId)
            ORDER BY t.id DESC
            """)
    List<NotInterestedTopicProjection> getTopicsByNotInterestedUserId(@Param("userId") Long userId);

    @Query("""
            SELECT t FROM Topic t
            WHERE t.topicCategory = :topicCategory
            ORDER BY t.id DESC
            """)
    List<TopicProjection> getTopicsByCategory(@Param("topicCategory") TopicCategory topicCategory);

    @Query("""
            SELECT CASE WHEN count(t) > 0
                THEN true
                ELSE false END
            FROM Topic t
            WHERE t.id = :topicId
            """)
    boolean isTopicExist(@Param("topicId") Long topicId);
}

