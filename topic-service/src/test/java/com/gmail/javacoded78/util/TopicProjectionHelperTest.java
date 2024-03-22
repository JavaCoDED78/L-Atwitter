package com.gmail.javacoded78.util;

import com.gmail.javacoded78.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TopicProjectionHelperTest {

    @Autowired
    private TopicProjectionHelper topicProjectionHelper;

    @MockBean
    private TopicRepository topicRepository;

    @BeforeEach
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    public void isTopicFollowed() {
        when(topicRepository.isTopicFollowed(USER_ID, 3L)).thenReturn(true);
        assertTrue(topicProjectionHelper.isTopicFollowed(3L));
        verify(topicRepository, times(1)).isTopicFollowed(USER_ID, 3L);
    }

    @Test
    public void isTopicNotInterested() {
        when(topicRepository.isTopicNotInterested(USER_ID, 3L)).thenReturn(true);
        assertTrue(topicProjectionHelper.isTopicNotInterested(3L));
        verify(topicRepository, times(1)).isTopicNotInterested(USER_ID, 3L);
    }
}