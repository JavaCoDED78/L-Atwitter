package com.gmail.javacoded78.service.cron;

import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.feign.WebSocketClient;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.service.util.TweetServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_FEED_SCHEDULE;

@Service
@Transactional
@RequiredArgsConstructor
public class CronService {

    private final WebSocketClient webSocketClient;
    private final TweetRepository tweetRepository;
    private final TweetServiceHelper tweetServiceHelper;
    private final UserClient userClient;

    @Scheduled(initialDelay = 30000, fixedDelay = 30000)
    public void sendTweetBySchedule() {
        List<Tweet> tweets = tweetRepository.findAllByScheduledDate(LocalDateTime.now());
        tweets.forEach(tweet -> {
            if (tweet.getText().contains("youtube.com") || !tweet.getImages().isEmpty()) {
                userClient.updateMediaTweetCount(true);
            } else {
                userClient.updateTweetCount(true);
            }
            tweet.setScheduledDate(null);
            tweet.setDateTime(LocalDateTime.now());
            TweetResponse tweetResponse = tweetServiceHelper.processTweetResponse(tweet);
            webSocketClient.send(TOPIC_FEED_SCHEDULE, tweetResponse);
        });
    }
}
