package com.gmail.javacoded78.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WebsocketConstants {

    public  final String TOPIC = "/topic";
    public  final String TOPIC_NOTIFICATIONS = TOPIC + "/notifications/";
    public  final String TOPIC_MENTIONS = TOPIC + "/mentions/";
    public  final String TOPIC_FEED = TOPIC + "/feed";
    public  final String TOPIC_FEED_ADD = TOPIC + "/feed/add";
    public  final String TOPIC_FEED_VOTE = TOPIC + "/feed/vote";
    public  final String TOPIC_FEED_SCHEDULE = TOPIC + "/feed/add/schedule";
    public  final String TOPIC_CHAT = TOPIC + "/chat/";
    public  final String TOPIC_TWEET = TOPIC + "/tweet/";
    public  final String TOPIC_TWEET_VOTE = TOPIC + "/tweet/vote/";
    public  final String TOPIC_USER_UPDATE_TWEET = TOPIC + "/user/update/tweet/";
    public  final String TOPIC_USER_ADD_TWEET = TOPIC + "/user/add/tweet/";
    public  final String TOPIC_USER_VOTE_TWEET = TOPIC + "/user/vote/tweet/";
}
