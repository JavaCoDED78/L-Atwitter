package com.gmail.javacoded78.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PathConstants {

    public  final String AUTH_USER_ID_HEADER = "X-auth-user-id";

    public  final String API_V1 = "/api/v1";
    public  final String UI_V1 = "/ui/v1";

//    Auth
    public  final String AUTH = "/auth";
    public  final String UI_V1_AUTH = UI_V1 + AUTH;
    public  final String LOGIN = "/login";
    public  final String EMAIL = "/email";
    public  final String TOKEN = "/token";
    public  final String REGISTRATION_CHECK = "/registration/check";
    public  final String REGISTRATION_CODE = "/registration/code";
    public  final String REGISTRATION_ACTIVATE_CODE = "/registration/activate/{code}";
    public  final String REGISTRATION_CONFIRM = "/registration/confirm";
    public  final String FORGOT = "/forgot";
    public  final String FORGOT_EMAIL = FORGOT + EMAIL;
    public  final String RESET = "/reset";
    public  final String RESET_CODE = RESET + "/{code}";
    public  final String RESET_CURRENT = RESET + "/current";

//    User
    public  final String USER = "/user";
    public  final String API_V1_USER = API_V1 + USER;
    public  final String UI_V1_USER = UI_V1 + USER;
    public  final String BLOCKED = "/blocked";
    public  final String USER_ID = "/{userId}";
    public  final String BLOCKED_USER_ID = BLOCKED + USER_ID;
    public  final String FOLLOWERS_USER_ID = "/followers/{userId}";
    public  final String FOLLOWING_USER_ID = "/following/{userId}";
    public  final String FOLLOWER_REQUESTS = "/follower-requests";
    public  final String FOLLOW_USER_ID = "/follow/{userId}";
    public  final String FOLLOW_OVERALL = "/follow/overall/{userId}";
    public  final String FOLLOW_PRIVATE = "/follow/private/{userId}";
    public  final String FOLLOW_ACCEPT = "/follow/accept/{userId}";
    public  final String FOLLOW_DECLINE = "/follow/decline/{userId}";
    public  final String MUTED = "/muted";
    public  final String MUTED_USER_ID = MUTED + USER_ID;
    public  final String USER_ID_USERNAME = "/id/{username}";
    public  final String ALL = "/all";
    public  final String RELEVANT = "/relevant";
    public  final String SEARCH_USERNAME = "/items/search/{username}";
    public  final String START = "/start";
    public  final String SUBSCRIBE_USER_ID = "/subscribe/{userId}";
    public  final String PIN_TWEET_ID = "/pin/tweet/{tweetId}";
    public  final String DETAILS_USER_ID = "/details/{userId}";
    public  final String TAGGED_IMAGE = "/tagged/image";

    public  final String UI_V1_USER_SETTINGS_UPDATE = UI_V1_USER + "/settings/update";
    public  final String USERNAME = "/username";
    public  final String PHONE = "/phone";
    public  final String COUNTRY = "/country";
    public  final String GENDER = "/gender";
    public  final String LANGUAGE = "/language";
    public  final String DIRECT = "/direct";
    public  final String PRIVATE = "/private";
    public  final String COLOR_SCHEME = "/color_scheme";
    public  final String BACKGROUND_COLOR = "/background_color";
    public  final String API_V1_AUTH = API_V1 + AUTH;
    public  final String USER_EMAIL = "/user/{email}";
    public  final String IDS = "/ids";
    public  final String FOLLOWERS_IDS = "/followers/ids";
    public  final String SUBSCRIBERS_USER_ID = "/subscribers/{userId}";
    public  final String IS_FOLLOWED_USER_ID = "/is_followed/{userId}";
    public  final String IS_PRIVATE_USER_ID = "/is_private/{userId}";
    public  final String IS_BLOCKED_USER_ID = "/is_blocked/{userId}/{blockedUserId}";
    public  final String IS_USER_BLOCKED_USER_ID = "/is_user_blocked/{userId}";
    public  final String IS_MY_PROFILE_BLOCKED_USER_ID = "/is_my_profile_blocked/{userId}";
    public  final String NOTIFICATION_USER_ID = "/notification/{userId}";
    public  final String MENTION_USER_ID = "/mention/{userId}";
    public  final String LIKE_COUNT = "/like/count/{increaseCount}";
    public  final String TWEET_COUNT = "/tweet/count/{increaseCount}";
    public  final String MEDIA_COUNT = "/media/count/{increaseCount}";
    public  final String LIST_OWNER_USER_ID = "/list/owner/{userId}";
    public  final String LIST_PARTICIPANTS = "/list/participants";
    public  final String LIST_PARTICIPANTS_USERNAME = LIST_PARTICIPANTS + "/{username}";
    public  final String NOTIFICATION_USER_USER_ID = "/notification/user/{userId}";
    public  final String TWEET_AUTHOR_USER_ID = "/tweet/author/{userId}";
    public  final String TWEET_ADDITIONAL_INFO_USER_ID = "/tweet/additional/info/{userId}";
    public  final String TWEET_PINNED_TWEET_ID = "/tweet/pinned/{tweetId}";
    public  final String TWEET_PINNED_USER_ID = "/tweet/pinned/{userId}";
    public  final String TWEET_VALID_IDS = "/tweet/valid/ids/{text}";
    public  final String VALID_IDS = "/valid/ids";
    public  final String CHAT_PARTICIPANT_USER_ID = "/chat/participant/{userId}";
    public  final String IS_EXISTS_USER_ID = "/is_exists/{userId}";
    public  final String CHAT_USER_ID = "/chat/{userId}";
    public  final String CHAT_VALID_IDS = "/chat/valid/ids";
    public  final String SUBSCRIBERS = "/subscribers";
    public  final String SUBSCRIBERS_IDS = SUBSCRIBERS + "/ids";
    public  final String NOTIFICATION_RESET = "/notification/reset";
    public  final String MENTION_RESET = "/mention/reset";

//    Tag
    public  final String TAGS = "/tags";
    public  final String UI_V1_TAGS = UI_V1 + TAGS;
    public  final String TRENDS = "/trends";
    public  final String SEARCH = "/search";
    public  final String API_V1_TAGS = API_V1 + TAGS;
    public  final String PARSE_TWEET_ID = "/parse/{tweetId}";
    public  final String DELETE_TWEET_ID = "/delete/{tweetId}";

//    Tweet
    public  final String TWEET = "/tweet";
    public  final String TWEETS = "/tweets";
    public  final String API_V1_TWEETS = API_V1 + TWEETS;
    public  final String USER_IDS = "/user/ids";
    public  final String TWEET_ID = "/{tweetId}";
    public  final String NOTIFICATION_TWEET_ID = "/notification/{tweetId}";
    public  final String ID_TWEET_ID = "/id/{tweetId}";
    public  final String COUNT_TEXT = "/count/{text}";
    public  final String CHAT_TWEET_ID = "/chat/{tweetId}";
    public  final String UI_V1_TWEETS = UI_V1 + TWEETS;
    public  final String USER_BOOKMARKS = "/user/bookmarks";
    public  final String USER_BOOKMARKS_TWEET_ID = USER_BOOKMARKS + TWEET_ID;
    public  final String TWEET_ID_BOOKMARKED = "/{tweetId}/bookmarked";
    public  final String LIKED_USER_USER_ID = "/liked/user/{userId}";
    public  final String TWEET_ID_LIKED_USERS = "/{tweetId}/liked-users";
    public  final String LIKE_USER_ID_TWEET_ID = "/like/{userId}/{tweetId}";
    public  final String POLL = "/poll";
    public  final String VOTE = "/vote";
    public  final String REPLIES_USER_ID = "/replies/user/{userId}";
    public  final String TWEET_ID_RETWEETED_USERS = "/{tweetId}/retweeted-users";
    public  final String RETWEET_USER_ID_TWEET_ID = "/retweet/{userId}/{tweetId}";
    public  final String USER_USER_ID = "/user/{userId}";
    public  final String MEDIA_USER_USER_ID = "/media/user/{userId}";
    public  final String IMAGES_USER_ID = "/images/{userId}";
    public  final String TWEET_ID_INFO = "/{tweetId}/info";
    public  final String TWEET_ID_REPLIES = "/{tweetId}/replies";
    public  final String TWEET_ID_QUOTES = "/{tweetId}/quotes";
    public  final String MEDIA = "/media";
    public  final String VIDEO = "/video";
    public  final String FOLLOWER = "/follower";
    public  final String SCHEDULE = "/schedule";
    public  final String MENTION = "/mention";
    public  final String IMAGE_TAGGED = "/image/tagged/{tweetId}";
    public  final String SEARCH_TEXT = "/search/{text}";
    public  final String SEARCH_RESULTS = "/search/results";
    public  final String REPLY_USER_ID_TWEET_ID = "/reply/{userId}/{tweetId}";
    public  final String QUOTE_USER_ID_TWEET_ID = "/quote/{userId}/{tweetId}";
    public  final String REPLY_CHANGE_USER_ID_TWEET_ID = "/reply/change/{userId}/{tweetId}";

    public  final String API_V1_IMAGE = API_V1 + "/image";
    public  final String UPLOAD = "/upload";

    public  final String API_V1_EMAIL = API_V1 + EMAIL;
    public  final String SUGGESTED = "/suggested";

//    Topic
    public  final String UI_V1_TOPICS = UI_V1 + "/topics";
    public  final String CATEGORY = "/category";
    public  final String FOLLOWED = "/followed";
    public  final String FOLLOWED_USER_ID = "/followed/{userId}";
    public  final String NOT_INTERESTED = "/not_interested";
    public  final String NOT_INTERESTED_TOPIC_ID = NOT_INTERESTED + "/{topicId}";
    public  final String FOLLOW_TOPIC_ID = "/follow/{topicId}";

//    List
    public  final String LISTS = "/lists";
    public  final String API_V1_LISTS = API_V1 + LISTS;
    public  final String UI_V1_LISTS = UI_V1 + LISTS;
    public  final String USER_CONSIST = "/user/consist";
    public  final String PINED = "/pined";
    public  final String LIST_ID = "/{listId}";
    public  final String TWEET_LIST_ID = "/tweet/{listId}";
    public  final String FOLLOW_LIST_ID = "/follow/{listId}";
    public  final String PIN_LIST_ID = "/pin/{listId}";
    public  final String ADD_USER_USER_ID = "/add/user/{userId}";
    public  final String ADD_USER = "/add/user";
    public  final String ADD_USER_LIST_ID = "/add/user/{userId}/{listId}";
    public  final String LIST_ID_TWEETS = "/{listId}/tweets";
    public  final String LIST_ID_DETAILS = "/{listId}/details";
    public  final String LIST_ID_FOLLOWERS = "/{listId}/{listOwnerId}/followers";
    public  final String LIST_ID_MEMBERS = "/{listId}/{listOwnerId}/members";
    public  final String SEARCH_LIST_ID = "/search/{listId}/{username}";

//    Chat
    public  final String UI_V1_CHAT = UI_V1 + "/chat";
    public  final String CHAT_ID = "/{chatId}";
    public  final String USERS = "/users";
    public  final String CREATE_USER_ID = "/create/{userId}";
    public  final String CHAT_ID_MESSAGES = "/{chatId}/messages";
    public  final String CHAT_ID_READ_MESSAGES = "/{chatId}/read/messages";
    public  final String ADD_MESSAGE = "/add/message";
    public  final String ADD_MESSAGE_TWEET = ADD_MESSAGE + TWEET;
    public  final String PARTICIPANT_CHAT_ID = "/participant/{participantId}/{chatId}";
    public  final String LEAVE_CHAT_ID = "/leave/{participantId}/{chatId}";

//    Notification
    public  final String NOTIFICATION = "/notification";
    public  final String API_V1_NOTIFICATION = API_V1 + NOTIFICATION;
    public  final String LIST = "/list";
    public  final String TWEET_TWEET_ID = "/tweet/{tweetId}";
    public  final String UI_V1_NOTIFICATION = UI_V1 + NOTIFICATION;
    public  final String SUBSCRIBES = "/subscribes";
    public  final String MENTIONS = "/mentions";
    public  final String NOTIFICATION_ID = "/{notificationId}";
    public  final String TIMELINE = "/timeline";

    public  final String API_V1_WEBSOCKET = API_V1 + "/websocket";
}
