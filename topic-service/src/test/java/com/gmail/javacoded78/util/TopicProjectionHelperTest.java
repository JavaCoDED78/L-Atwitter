package com.gmail.javacoded78.util;

import com.gmail.javacoded78.repository.TopicFollowersRepository;
import com.gmail.javacoded78.repository.TopicNotInterestedRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TopicProjectionHelperTest {

    @Autowired
    private TopicProjectionHelper topicProjectionHelper;

    @MockBean
    private TopicFollowersRepository topicFollowersRepository;

    @MockBean
    private TopicNotInterestedRepository topicNotInterestedRepository;

    @Before
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    public void isTopicFollowed() {
        when(topicFollowersRepository.isTopicFollowed(USER_ID, 3L)).thenReturn(true);
        assertTrue(topicProjectionHelper.isTopicFollowed(3L));
        verify(topicFollowersRepository, times(1)).isTopicFollowed(USER_ID, 3L);
    }

    @Test
    public void isTopicNotInterested() {
        when(topicNotInterestedRepository.isTopicNotInterested(USER_ID, 3L)).thenReturn(true);
        assertTrue(topicProjectionHelper.isTopicNotInterested(3L));
        verify(topicNotInterestedRepository, times(1)).isTopicNotInterested(USER_ID, 3L);
    }
}