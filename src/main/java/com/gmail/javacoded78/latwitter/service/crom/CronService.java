package com.gmail.javacoded78.latwitter.service.crom;

import com.gmail.javacoded78.latwitter.mapper.TweetMapper;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CronService {

    private final SimpMessagingTemplate messagingTemplate;
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    @Scheduled(initialDelay = 300000, fixedDelay = 300000)
    public void sendTweetBySchedule() {
        List<Tweet> tweets = tweetRepository.findAllByScheduledDate(LocalDateTime.now());
        tweets.forEach(tweet -> tweet.setScheduledDate(null));
        tweetRepository.saveAll(tweets);
        messagingTemplate.convertAndSend("/topic/feed/schedule", tweetMapper.convertListToResponse(tweets));
    }
}
