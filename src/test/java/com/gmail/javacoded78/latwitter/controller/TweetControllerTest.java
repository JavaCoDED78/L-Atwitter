package com.gmail.javacoded78.latwitter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.latwitter.dto.request.TweetDeleteRequest;
import com.gmail.javacoded78.latwitter.dto.request.TweetRequest;
import com.gmail.javacoded78.latwitter.dto.request.VoteRequest;
import com.gmail.javacoded78.latwitter.model.LinkCoverSize;
import com.gmail.javacoded78.latwitter.model.ReplyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.javacoded78.latwitter.util.TestConstants.LINK;
import static com.gmail.javacoded78.latwitter.util.TestConstants.LINK_COVER;
import static com.gmail.javacoded78.latwitter.util.TestConstants.LINK_DESCRIPTION;
import static com.gmail.javacoded78.latwitter.util.TestConstants.LINK_TITLE;
import static com.gmail.javacoded78.latwitter.util.TestConstants.TEXT_WITH_YOUTUBE_LINK;
import static com.gmail.javacoded78.latwitter.util.TestConstants.TWEET_DATETIME;
import static com.gmail.javacoded78.latwitter.util.TestConstants.TWEET_SCHEDULED_DATETIME;
import static com.gmail.javacoded78.latwitter.util.TestConstants.TWEET_TEXT;
import static com.gmail.javacoded78.latwitter.util.TestConstants.URL_TWEETS_BASIC;
import static com.gmail.javacoded78.latwitter.util.TestConstants.USER_EMAIL;
import static com.gmail.javacoded78.latwitter.util.TestConstants.YOUTUBE_LINK;
import static com.gmail.javacoded78.latwitter.util.TestConstants.YOUTUBE_LINK_COVER;
import static com.gmail.javacoded78.latwitter.util.TestConstants.YOUTUBE_LINK_TITLE;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.yml")
@Sql(value = {"/sql/populate-table-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/populate-table-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/tweets - Get tweets")
    public void getTweets() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(8)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].text").isNotEmpty())
                .andExpect(jsonPath("$[*].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedUsername").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedId").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedTweetId").isNotEmpty())
                .andExpect(jsonPath("$[*].replyType").isNotEmpty())
                .andExpect(jsonPath("$[*].link").isNotEmpty())
                .andExpect(jsonPath("$[*].linkTitle").isNotEmpty())
                .andExpect(jsonPath("$[*].linkDescription").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCover").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCoverSize").isNotEmpty())
                .andExpect(jsonPath("$[*].quoteTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].user").isNotEmpty())
                .andExpect(jsonPath("$[*].poll").isNotEmpty())
                .andExpect(jsonPath("$[*].images").isNotEmpty())
                .andExpect(jsonPath("$[*].likedTweets").isNotEmpty())
                .andExpect(jsonPath("$[*].retweets").isNotEmpty())
                .andExpect(jsonPath("$[*].replies").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/tweets/43 - Get tweet by id")
    public void getTweetById() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/43"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(43))
                .andExpect(jsonPath("$.text").value(TWEET_TEXT))
                .andExpect(jsonPath("$.dateTime").value(TWEET_DATETIME))
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.addressedTweetId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").value(LINK))
                .andExpect(jsonPath("$.linkTitle").value(LINK_TITLE))
                .andExpect(jsonPath("$.linkDescription").value(LINK_DESCRIPTION))
                .andExpect(jsonPath("$.linkCover").value(LINK_COVER))
                .andExpect(jsonPath("$.linkCoverSize").value(LinkCoverSize.LARGE.toString()))
                .andExpect(jsonPath("$.quoteTweet").isEmpty())
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.poll").isEmpty())
                .andExpect(jsonPath("$.images").isEmpty())
                .andExpect(jsonPath("$.likedTweets").isEmpty())
                .andExpect(jsonPath("$.retweets").isEmpty())
                .andExpect(jsonPath("$.replies").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/tweets/99 - Should Not Found tweet by id")
    public void getTweetById_ShouldNotFound() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Tweet not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/tweets/media -Get media tweets")
    public void getMediaTweets() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/media"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].text").isNotEmpty())
                .andExpect(jsonPath("$[*].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedUsername").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedId").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedTweetId").isNotEmpty())
                .andExpect(jsonPath("$[*].replyType").isNotEmpty())
                .andExpect(jsonPath("$[*].link").isNotEmpty())
                .andExpect(jsonPath("$[*].linkTitle").isNotEmpty())
                .andExpect(jsonPath("$[*].linkDescription").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCover").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCoverSize").isNotEmpty())
                .andExpect(jsonPath("$[*].quoteTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].user").isNotEmpty())
                .andExpect(jsonPath("$[*].poll").isNotEmpty())
                .andExpect(jsonPath("$[*].images").isNotEmpty())
                .andExpect(jsonPath("$[*].likedTweets").isNotEmpty())
                .andExpect(jsonPath("$[*].retweets").isNotEmpty())
                .andExpect(jsonPath("$[*].replies").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/tweets/video -Get tweets with video")
    public void getTweetsWithVideo() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/video"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(42))
                .andExpect(jsonPath("$[*].text").isNotEmpty())
                .andExpect(jsonPath("$[*].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedUsername").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedId").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedTweetId").isNotEmpty())
                .andExpect(jsonPath("$[*].replyType").isNotEmpty())
                .andExpect(jsonPath("$[*].link").isNotEmpty())
                .andExpect(jsonPath("$[0].link").value(YOUTUBE_LINK))
                .andExpect(jsonPath("$[*].linkTitle").isNotEmpty())
                .andExpect(jsonPath("$[0].linkTitle").value(YOUTUBE_LINK_TITLE))
                .andExpect(jsonPath("$[*].linkDescription").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCover").isNotEmpty())
                .andExpect(jsonPath("$[0].linkCover").value(YOUTUBE_LINK_COVER))
                .andExpect(jsonPath("$[*].linkCoverSize").isNotEmpty())
                .andExpect(jsonPath("$[*].quoteTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].user").isNotEmpty())
                .andExpect(jsonPath("$[*].poll").isNotEmpty())
                .andExpect(jsonPath("$[*].images").isNotEmpty())
                .andExpect(jsonPath("$[*].likedTweets").isNotEmpty())
                .andExpect(jsonPath("$[*].retweets").isNotEmpty())
                .andExpect(jsonPath("$[*].replies").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/tweets/video -Get tweets with video")
    public void getScheduledTweets() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/schedule"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(39))
                .andExpect(jsonPath("$[*].text").isNotEmpty())
                .andExpect(jsonPath("$[*].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedUsername").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedId").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedTweetId").isNotEmpty())
                .andExpect(jsonPath("$[*].replyType").isNotEmpty())
                .andExpect(jsonPath("$[*].link").isNotEmpty())
                .andExpect(jsonPath("$[*].linkTitle").isNotEmpty())
                .andExpect(jsonPath("$[*].linkDescription").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCover").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCoverSize").isNotEmpty())
                .andExpect(jsonPath("$[*].quoteTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].user").isNotEmpty())
                .andExpect(jsonPath("$[*].poll").isNotEmpty())
                .andExpect(jsonPath("$[*].images").isNotEmpty())
                .andExpect(jsonPath("$[*].likedTweets").isNotEmpty())
                .andExpect(jsonPath("$[*].retweets").isNotEmpty())
                .andExpect(jsonPath("$[*].replies").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[400] POST /api/v1/tweets - Should tweet text length more than 280 symbols")
    public void createTweet_ShouldTweetTextLengthMoreThan280Symbols() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText(LINK_DESCRIPTION + LINK_DESCRIPTION);
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(URL_TWEETS_BASIC)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Incorrect tweet text length")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[400] POST /api/v1/tweets - Should tweet text length length is 0")
    public void createTweet_ShouldTweetTextLengthLengthIs0() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText(LINK_DESCRIPTION + LINK_DESCRIPTION);
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(URL_TWEETS_BASIC)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Incorrect tweet text length")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] POST /api/v1/tweets - Create tweet with hashtag")
    public void createTweetWithHashtag() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test tweet #test123");
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(URL_TWEETS_BASIC)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value("test tweet #test123"))
                .andExpect(jsonPath("$.dateTime").isNotEmpty())
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.addressedTweetId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").isEmpty())
                .andExpect(jsonPath("$.linkTitle").isEmpty())
                .andExpect(jsonPath("$.linkDescription").isEmpty())
                .andExpect(jsonPath("$.linkCover").isEmpty())
                .andExpect(jsonPath("$.linkCoverSize").isEmpty())
                .andExpect(jsonPath("$.quoteTweet").isEmpty())
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.poll").isEmpty())
                .andExpect(jsonPath("$.images").isEmpty())
                .andExpect(jsonPath("$.likedTweets").isEmpty())
                .andExpect(jsonPath("$.retweets").isEmpty())
                .andExpect(jsonPath("$.replies").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] POST /api/v1/tweets - Create tweet with link")
    public void createTweetWithLink() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText(TWEET_TEXT);
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(URL_TWEETS_BASIC)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value(TWEET_TEXT))
                .andExpect(jsonPath("$.dateTime").isNotEmpty())
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.addressedTweetId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").value(LINK))
                .andExpect(jsonPath("$.linkTitle").value(LINK_TITLE))
                .andExpect(jsonPath("$.linkDescription").value(LINK_DESCRIPTION))
                .andExpect(jsonPath("$.linkCover").value(LINK_COVER))
                .andExpect(jsonPath("$.linkCoverSize").value(LinkCoverSize.SMALL.toString()))
                .andExpect(jsonPath("$.quoteTweet").isEmpty())
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.poll").isEmpty())
                .andExpect(jsonPath("$.images").isEmpty())
                .andExpect(jsonPath("$.likedTweets").isEmpty())
                .andExpect(jsonPath("$.retweets").isEmpty())
                .andExpect(jsonPath("$.replies").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] POST /api/v1/tweets - Create tweet with YouTube link")
    public void createTweetWithYouTubeLink() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText(TEXT_WITH_YOUTUBE_LINK);
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(URL_TWEETS_BASIC)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value(TEXT_WITH_YOUTUBE_LINK))
                .andExpect(jsonPath("$.dateTime").isNotEmpty())
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.addressedTweetId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").value(YOUTUBE_LINK))
                .andExpect(jsonPath("$.linkTitle").value(YOUTUBE_LINK_TITLE))
                .andExpect(jsonPath("$.linkDescription").isEmpty())
                .andExpect(jsonPath("$.linkCover").value(YOUTUBE_LINK_COVER))
                .andExpect(jsonPath("$.linkCoverSize").isEmpty())
                .andExpect(jsonPath("$.quoteTweet").isEmpty())
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.poll").isEmpty())
                .andExpect(jsonPath("$.images").isEmpty())
                .andExpect(jsonPath("$.likedTweets").isEmpty())
                .andExpect(jsonPath("$.retweets").isEmpty())
                .andExpect(jsonPath("$.replies").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] POST /api/v1/tweets/poll - Create tweet with poll")
    public void createTweetWithPoll() throws Exception {
        List<String> pollChoiceList = new ArrayList<>();
        pollChoiceList.add("Choice 1");
        pollChoiceList.add("Choice 2");
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test text");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        tweetRequest.setChoices(pollChoiceList);
        tweetRequest.setPollDateTime(100L);

        mockMvc.perform(post(URL_TWEETS_BASIC + "/poll")
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value("test text"))
                .andExpect(jsonPath("$.dateTime").isNotEmpty())
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.addressedTweetId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").isEmpty())
                .andExpect(jsonPath("$.linkTitle").isEmpty())
                .andExpect(jsonPath("$.linkDescription").isEmpty())
                .andExpect(jsonPath("$.linkCover").isEmpty())
                .andExpect(jsonPath("$.linkCoverSize").isEmpty())
                .andExpect(jsonPath("$.quoteTweet").isEmpty())
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.poll.pollChoices[0].choice").value("Choice 1"))
                .andExpect(jsonPath("$.poll.pollChoices[1].choice").value("Choice 2"))
                .andExpect(jsonPath("$.images").isEmpty())
                .andExpect(jsonPath("$.likedTweets").isEmpty())
                .andExpect(jsonPath("$.retweets").isEmpty())
                .andExpect(jsonPath("$.replies").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[400] POST /api/v1/tweets/poll - Should incorrect poll choices size is 1")
    public void createTweetWithPoll_ShouldIncorrectPoolChoicesSizeIs1() throws Exception {
        List<String> pollChoiceList = new ArrayList<>();
        pollChoiceList.add("Choice 1");
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test text");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        tweetRequest.setChoices(pollChoiceList);
        tweetRequest.setPollDateTime(100L);

        mockMvc.perform(post(URL_TWEETS_BASIC + "/poll")
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Incorrect poll choices")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[400] POST /api/v1/tweets/poll - Should incorrect poll choices size is 5")
    public void createTweetWithPoll_ShouldIncorrectPoolChoicesSizeIs5() throws Exception {
        List<String> pollChoiceList = new ArrayList<>();
        pollChoiceList.add("Choice 1");
        pollChoiceList.add("Choice 2");
        pollChoiceList.add("Choice 3");
        pollChoiceList.add("Choice 4");
        pollChoiceList.add("Choice 5");
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test text");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        tweetRequest.setChoices(pollChoiceList);
        tweetRequest.setPollDateTime(100L);

        mockMvc.perform(post(URL_TWEETS_BASIC + "/poll")
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Incorrect poll choices")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[400] POST /api/v1/tweets/poll - Should incorrect poll choices text length is 0")
    public void createTweetWithPoll_ShouldIncorrectPoolChoicesTextLengthIs0() throws Exception {
        List<String> pollChoiceList = new ArrayList<>();
        pollChoiceList.add("Choice 1");
        pollChoiceList.add("");
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test text");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        tweetRequest.setChoices(pollChoiceList);
        tweetRequest.setPollDateTime(100L);

        mockMvc.perform(post(URL_TWEETS_BASIC + "/poll")
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Incorrect choice text length")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[400] POST /api/v1/tweets/poll - Should incorrect poll choices text length more than 25")
    public void createTweetWithPoll_ShouldIncorrectPoolChoicesTextLengthMoreThan25() throws Exception {
        List<String> pollChoiceList = new ArrayList<>();
        pollChoiceList.add("Choice 1");
        pollChoiceList.add(LINK_DESCRIPTION);
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test text");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        tweetRequest.setChoices(pollChoiceList);
        tweetRequest.setPollDateTime(100L);

        mockMvc.perform(post(URL_TWEETS_BASIC + "/poll")
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Incorrect choice text length")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] POST /api/v1/tweets/schedule - Create Scheduled Tweet")
    public void createScheduledTweet() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test tweet");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        tweetRequest.setScheduledDate(LocalDateTime.parse(TWEET_SCHEDULED_DATETIME));

        mockMvc.perform(post(URL_TWEETS_BASIC + "/schedule")
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value("test tweet"))
                .andExpect(jsonPath("$.dateTime").isNotEmpty())
                .andExpect(jsonPath("$.scheduledDate").value(TWEET_SCHEDULED_DATETIME))
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.addressedTweetId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").isEmpty())
                .andExpect(jsonPath("$.linkTitle").isEmpty())
                .andExpect(jsonPath("$.linkDescription").isEmpty())
                .andExpect(jsonPath("$.linkCover").isEmpty())
                .andExpect(jsonPath("$.linkCoverSize").isEmpty())
                .andExpect(jsonPath("$.quoteTweet").isEmpty())
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.poll").isEmpty())
                .andExpect(jsonPath("$.images").isEmpty())
                .andExpect(jsonPath("$.likedTweets").isEmpty())
                .andExpect(jsonPath("$.retweets").isEmpty())
                .andExpect(jsonPath("$.replies").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] PUT /api/v1/tweets/schedule - Update Scheduled Tweet")
    public void updateScheduledTweet() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setId(39L);
        tweetRequest.setText("test tweet2");
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(put(URL_TWEETS_BASIC + "/schedule")
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(39))
                .andExpect(jsonPath("$.text").value("test tweet2"))
                .andExpect(jsonPath("$.dateTime").isNotEmpty())
                .andExpect(jsonPath("$.scheduledDate").value(TWEET_SCHEDULED_DATETIME))
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.addressedTweetId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").isEmpty())
                .andExpect(jsonPath("$.linkTitle").isEmpty())
                .andExpect(jsonPath("$.linkDescription").isEmpty())
                .andExpect(jsonPath("$.linkCover").isEmpty())
                .andExpect(jsonPath("$.linkCoverSize").isEmpty())
                .andExpect(jsonPath("$.quoteTweet").isEmpty())
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.poll").isEmpty())
                .andExpect(jsonPath("$.images").isEmpty())
                .andExpect(jsonPath("$.likedTweets").isEmpty())
                .andExpect(jsonPath("$.retweets").isEmpty())
                .andExpect(jsonPath("$.replies").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[400] PUT /api/v1/tweets/schedule - Should tweet text length length is 0")
    public void updateScheduledTweet_ShouldTweetTextLengthLengthIs0() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setId(39L);
        tweetRequest.setText("");
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(put(URL_TWEETS_BASIC + "/schedule")
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Incorrect tweet text length")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[400] PUT /api/v1/tweets/schedule - Should tweet text length more than 280 symbols")
    public void updateScheduledTweet_ShouldTweetTextLengthMoreThan280Symbols() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setId(39L);
        tweetRequest.setText(LINK_DESCRIPTION + LINK_DESCRIPTION);
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(put(URL_TWEETS_BASIC + "/schedule")
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Incorrect tweet text length")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] PUT /api/v1/tweets/schedule - Should Tweet Not Found")
    public void updateScheduledTweet_ShouldTweetNotFound() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setId(99L);
        tweetRequest.setText("test tweet99");
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(put(URL_TWEETS_BASIC + "/schedule")
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Tweet not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] DELETE /api/v1/tweets/schedule - Delete scheduled Tweets")
    public void deleteScheduledTweets() throws Exception {
        TweetDeleteRequest tweetDeleteRequest = new TweetDeleteRequest();
        tweetDeleteRequest.setTweetsIds(List.of(42L));

        mockMvc.perform(delete(URL_TWEETS_BASIC + "/schedule")
                        .content(mapper.writeValueAsString(tweetDeleteRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Scheduled tweets deleted.")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] DELETE /api/v1/tweets/40 - Delete Tweet")
    public void deleteTweet() throws Exception {
        mockMvc.perform(delete(URL_TWEETS_BASIC + "/40"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(40))
                .andExpect(jsonPath("$.text").value("test tweet"))
                .andExpect(jsonPath("$.dateTime").isNotEmpty())
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.addressedTweetId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").isEmpty())
                .andExpect(jsonPath("$.linkTitle").isEmpty())
                .andExpect(jsonPath("$.linkDescription").isEmpty())
                .andExpect(jsonPath("$.linkCover").isEmpty())
                .andExpect(jsonPath("$.linkCoverSize").isEmpty())
                .andExpect(jsonPath("$.quoteTweet").isEmpty())
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.poll").isNotEmpty())
                .andExpect(jsonPath("$.poll.id").value(2))
                .andExpect(jsonPath("$.images").isEmpty())
                .andExpect(jsonPath("$.likedTweets").isNotEmpty())
                .andExpect(jsonPath("$.retweets").isNotEmpty())
                .andExpect(jsonPath("$.replies").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/tweets/search/test - Search tweets by text")
    public void searchTweets() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/search/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(4)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].text").isNotEmpty())
                .andExpect(jsonPath("$[*].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedUsername").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedId").isNotEmpty())
                .andExpect(jsonPath("$[*].addressedTweetId").isNotEmpty())
                .andExpect(jsonPath("$[*].replyType").isNotEmpty())
                .andExpect(jsonPath("$[*].link").isNotEmpty())
                .andExpect(jsonPath("$[*].linkTitle").isNotEmpty())
                .andExpect(jsonPath("$[*].linkDescription").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCover").isNotEmpty())
                .andExpect(jsonPath("$[*].linkCoverSize").isNotEmpty())
                .andExpect(jsonPath("$[*].quoteTweet").isNotEmpty())
                .andExpect(jsonPath("$[*].user").isNotEmpty())
                .andExpect(jsonPath("$[*].poll").isNotEmpty())
                .andExpect(jsonPath("$[*].images").isNotEmpty())
                .andExpect(jsonPath("$[*].likedTweets").isNotEmpty())
                .andExpect(jsonPath("$[*].retweets").isNotEmpty())
                .andExpect(jsonPath("$[*].replies").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/tweets/like/43 - Like tweet by id")
    public void likeTweet() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/like/43"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(43))
                .andExpect(jsonPath("$.text").value(TWEET_TEXT))
                .andExpect(jsonPath("$.dateTime").value(TWEET_DATETIME))
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").value(LINK))
                .andExpect(jsonPath("$.linkTitle").value(LINK_TITLE))
                .andExpect(jsonPath("$.linkDescription").value(LINK_DESCRIPTION))
                .andExpect(jsonPath("$.linkCover").value(LINK_COVER))
                .andExpect(jsonPath("$.linkCoverSize").value(LinkCoverSize.LARGE.toString()))
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.images").isEmpty())
                .andExpect(jsonPath("$.likedTweets").isNotEmpty())
                .andExpect(jsonPath("$.retweets").isEmpty())
                .andExpect(jsonPath("$.replies").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/tweets/like/99 - Should Tweet Not Found by id")
    public void likeTweet_ShouldTweetNotFoundById() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/like/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Tweet not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/tweets/like/45 - Unlike tweet by id")
    public void unlikeTweet() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/like/45"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(45))
                .andExpect(jsonPath("$.text").value("media tweet test"))
                .andExpect(jsonPath("$.dateTime").isNotEmpty())
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").isEmpty())
                .andExpect(jsonPath("$.linkTitle").isEmpty())
                .andExpect(jsonPath("$.linkDescription").isEmpty())
                .andExpect(jsonPath("$.linkCover").isEmpty())
                .andExpect(jsonPath("$.linkCoverSize").isEmpty())
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.images").isNotEmpty())
                .andExpect(jsonPath("$.likedTweets").isEmpty())
                .andExpect(jsonPath("$.retweets").isNotEmpty())
                .andExpect(jsonPath("$.replies").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/tweets/retweet/43 - Retweet tweet by id")
    public void retweet() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/retweet/43"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(43))
                .andExpect(jsonPath("$.text").value(TWEET_TEXT))
                .andExpect(jsonPath("$.dateTime").value(TWEET_DATETIME))
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").value(LINK))
                .andExpect(jsonPath("$.linkTitle").value(LINK_TITLE))
                .andExpect(jsonPath("$.linkDescription").value(LINK_DESCRIPTION))
                .andExpect(jsonPath("$.linkCover").value(LINK_COVER))
                .andExpect(jsonPath("$.linkCoverSize").value(LinkCoverSize.LARGE.toString()))
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.images").isEmpty())
                .andExpect(jsonPath("$.likedTweets").isEmpty())
                .andExpect(jsonPath("$.retweets").isNotEmpty())
                .andExpect(jsonPath("$.replies").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/tweets/retweet/99 - Should Tweet Not Found by id")
    public void retweet_ShouldTweetNotFound() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/retweet/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Tweet not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/tweets/retweet/45 - UnRetweet tweet by id")
    public void unretweet() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/retweet/45"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(45))
                .andExpect(jsonPath("$.text").value("media tweet test"))
                .andExpect(jsonPath("$.dateTime").isNotEmpty())
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").isEmpty())
                .andExpect(jsonPath("$.linkTitle").isEmpty())
                .andExpect(jsonPath("$.linkDescription").isEmpty())
                .andExpect(jsonPath("$.linkCover").isEmpty())
                .andExpect(jsonPath("$.linkCoverSize").isEmpty())
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.images").isNotEmpty())
                .andExpect(jsonPath("$.likedTweets").isNotEmpty())
                .andExpect(jsonPath("$.retweets").isEmpty())
                .andExpect(jsonPath("$.replies").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] POST /api/v1/tweets/reply/43 - Reply tweet by id")
    public void replyTweet() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test reply");
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(URL_TWEETS_BASIC + "/reply/43")
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(43))
                .andExpect(jsonPath("$.text").value(TWEET_TEXT))
                .andExpect(jsonPath("$.dateTime").value(TWEET_DATETIME))
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").value(LINK))
                .andExpect(jsonPath("$.linkTitle").value(LINK_TITLE))
                .andExpect(jsonPath("$.linkDescription").value(LINK_DESCRIPTION))
                .andExpect(jsonPath("$.linkCover").value(LINK_COVER))
                .andExpect(jsonPath("$.linkCoverSize").value(LinkCoverSize.LARGE.toString()))
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.images").isEmpty())
                .andExpect(jsonPath("$.likedTweets").isEmpty())
                .andExpect(jsonPath("$.retweets").isEmpty())
                .andExpect(jsonPath("$.replies").isNotEmpty())
                .andExpect(jsonPath("$.replies[0].text").value("test reply"));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] POST /api/v1/tweets/reply/99 - Should tweet Not Found by id")
    public void replyTweet_ShouldTweetNotFound() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test reply");
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(URL_TWEETS_BASIC + "/reply/99")
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Tweet not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] POST /api/v1/tweets/quote/43 - Quote tweet by id")
    public void quoteTweet() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test quote");
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(URL_TWEETS_BASIC + "/quote/43")
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value("test quote"))
                .andExpect(jsonPath("$.dateTime").isNotEmpty())
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.addressedTweetId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").isEmpty())
                .andExpect(jsonPath("$.linkTitle").isEmpty())
                .andExpect(jsonPath("$.linkDescription").isEmpty())
                .andExpect(jsonPath("$.linkCover").isEmpty())
                .andExpect(jsonPath("$.linkCoverSize").isEmpty())
                .andExpect(jsonPath("$.quoteTweet").isNotEmpty())
                .andExpect(jsonPath("$.quoteTweet.id").value(43))
                .andExpect(jsonPath("$.quoteTweet.text").value(TWEET_TEXT))
                .andExpect(jsonPath("$.quoteTweet.dateTime").value(TWEET_DATETIME))
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.poll").isEmpty())
                .andExpect(jsonPath("$.images").isEmpty())
                .andExpect(jsonPath("$.likedTweets").isEmpty())
                .andExpect(jsonPath("$.retweets").isEmpty())
                .andExpect(jsonPath("$.replies").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] POST /api/v1/tweets/quote/99 - Should Tweet Not Found by id")
    public void quoteTweet_ShouldTweetNotFound() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test quote");
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(post(URL_TWEETS_BASIC + "/quote/99")
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Tweet not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] GET /api/v1/tweets/reply/change/43 - Change Tweet reply type by id")
    public void changeTweetReplyType() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/reply/change/43")
                        .param("replyType", String.valueOf(ReplyType.FOLLOW)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(43))
                .andExpect(jsonPath("$.text").value(TWEET_TEXT))
                .andExpect(jsonPath("$.dateTime").value(TWEET_DATETIME))
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.addressedTweetId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.FOLLOW.toString()))
                .andExpect(jsonPath("$.link").value(LINK))
                .andExpect(jsonPath("$.linkTitle").value(LINK_TITLE))
                .andExpect(jsonPath("$.linkDescription").value(LINK_DESCRIPTION))
                .andExpect(jsonPath("$.linkCover").value(LINK_COVER))
                .andExpect(jsonPath("$.linkCoverSize").value(LinkCoverSize.LARGE.toString()))
                .andExpect(jsonPath("$.quoteTweet").isEmpty())
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.poll").isEmpty())
                .andExpect(jsonPath("$.images").isEmpty())
                .andExpect(jsonPath("$.likedTweets").isEmpty())
                .andExpect(jsonPath("$.retweets").isEmpty())
                .andExpect(jsonPath("$.replies").isEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/tweets/reply/change/99 - Should Tweet Not Found by id")
    public void changeTweetReplyType_ShouldTweetNotFound() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/reply/change/99")
                        .param("replyType", String.valueOf(ReplyType.FOLLOW)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Tweet not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] GET /api/v1/tweets/reply/change/41 - Should Tweet Not Found by user")
    public void changeTweetReplyType_ShouldTweetNotFoundByUser() throws Exception {
        mockMvc.perform(get(URL_TWEETS_BASIC + "/reply/change/41")
                        .param("replyType", String.valueOf(ReplyType.FOLLOW)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Tweet not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[200] POST /api/v1/tweets/vote - Vote in poll")
    public void voteInPoll() throws Exception {
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setTweetId(40L);
        voteRequest.setPollId(2L);
        voteRequest.setPollChoiceId(9L);

        mockMvc.perform(post(URL_TWEETS_BASIC + "/vote")
                        .content(mapper.writeValueAsString(voteRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(40))
                .andExpect(jsonPath("$.text").value("test tweet"))
                .andExpect(jsonPath("$.dateTime").isNotEmpty())
                .andExpect(jsonPath("$.addressedUsername").isEmpty())
                .andExpect(jsonPath("$.addressedId").isEmpty())
                .andExpect(jsonPath("$.addressedTweetId").isEmpty())
                .andExpect(jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$.link").isEmpty())
                .andExpect(jsonPath("$.linkTitle").isEmpty())
                .andExpect(jsonPath("$.linkDescription").isEmpty())
                .andExpect(jsonPath("$.linkCover").isEmpty())
                .andExpect(jsonPath("$.linkCoverSize").isEmpty())
                .andExpect(jsonPath("$.quoteTweet").isEmpty())
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.poll").isNotEmpty())
                .andExpect(jsonPath("$.poll.id").value(2))
                .andExpect(jsonPath("$.images").isEmpty())
                .andExpect(jsonPath("$.likedTweets").isNotEmpty())
                .andExpect(jsonPath("$.retweets").isNotEmpty())
                .andExpect(jsonPath("$.replies").isNotEmpty());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] POST /api/v1/tweets/vote - Should poll Not Found")
    public void voteInPoll_ShouldPollNotFound() throws Exception {
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setTweetId(40L);
        voteRequest.setPollId(99L);
        voteRequest.setPollChoiceId(3L);

        mockMvc.perform(post(URL_TWEETS_BASIC + "/vote")
                        .content(mapper.writeValueAsString(voteRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Poll not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] POST /api/v1/tweets/vote - Should poll choice Not Found")
    public void voteInPoll_ShouldPollChoiceNotFound() throws Exception {
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setTweetId(40L);
        voteRequest.setPollId(2L);
        voteRequest.setPollChoiceId(99L);

        mockMvc.perform(post(URL_TWEETS_BASIC + "/vote")
                        .content(mapper.writeValueAsString(voteRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Poll choice not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] POST /api/v1/tweets/vote - Should tweet Not Found")
    public void voteInPoll_ShouldTweetNotFound() throws Exception {
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setTweetId(99L);
        voteRequest.setPollId(2L);
        voteRequest.setPollChoiceId(9L);

        mockMvc.perform(post(URL_TWEETS_BASIC + "/vote")
                        .content(mapper.writeValueAsString(voteRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Tweet not found")));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    @DisplayName("[404] POST /api/v1/tweets/vote - Should poll in tweet Not Found")
    public void voteInPoll_ShouldPollInTweetNotFound() throws Exception {
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setTweetId(40L);
        voteRequest.setPollId(8L);
        voteRequest.setPollChoiceId(11L);

        mockMvc.perform(post(URL_TWEETS_BASIC + "/vote")
                        .content(mapper.writeValueAsString(voteRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Poll in tweet not exist")));
    }
}