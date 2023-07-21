package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.enums.LinkCoverSize;
import com.gmail.javacoded78.latwitter.enums.ReplyType;
import com.gmail.javacoded78.latwitter.exception.ApiRequestException;
import com.gmail.javacoded78.latwitter.model.Bookmark;
import com.gmail.javacoded78.latwitter.model.ChatMessage;
import com.gmail.javacoded78.latwitter.model.LikeTweet;
import com.gmail.javacoded78.latwitter.model.Notification;
import com.gmail.javacoded78.latwitter.enums.NotificationType;
import com.gmail.javacoded78.latwitter.model.Poll;
import com.gmail.javacoded78.latwitter.model.PollChoice;
import com.gmail.javacoded78.latwitter.model.Retweet;
import com.gmail.javacoded78.latwitter.model.Tag;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.BookmarkRepository;
import com.gmail.javacoded78.latwitter.repository.ChatMessageRepository;
import com.gmail.javacoded78.latwitter.repository.ImageRepository;
import com.gmail.javacoded78.latwitter.repository.LikeTweetRepository;
import com.gmail.javacoded78.latwitter.repository.NotificationRepository;
import com.gmail.javacoded78.latwitter.repository.PollChoiceRepository;
import com.gmail.javacoded78.latwitter.repository.PollRepository;
import com.gmail.javacoded78.latwitter.repository.RetweetRepository;
import com.gmail.javacoded78.latwitter.repository.TagRepository;
import com.gmail.javacoded78.latwitter.repository.TweetRepository;
import com.gmail.javacoded78.latwitter.repository.UserRepository;
import com.gmail.javacoded78.latwitter.repository.projection.tweet.TweetProjection;
import com.gmail.javacoded78.latwitter.repository.projection.tweet.TweetsProjection;
import com.gmail.javacoded78.latwitter.repository.projection.user.UserProjection;
import com.gmail.javacoded78.latwitter.service.AuthenticationService;
import com.gmail.javacoded78.latwitter.service.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final AuthenticationService authenticationService;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final RetweetRepository retweetRepository;
    private final LikeTweetRepository likeTweetRepository;
    private final NotificationRepository notificationRepository;
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final PollRepository pollRepository;
    private final PollChoiceRepository pollChoiceRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final RestTemplate restTemplate;

    @Value("${google.api.url}")
    private String googleApiUrl;

    @Value("${google.api.key}")
    private String googleApiKey;

    @Override
    public Page<TweetProjection> getTweets(Pageable pageable) {
        return tweetRepository.findAllTweets(pageable);
    }

    @Override
    public TweetProjection getTweetById(Long tweetId) {
        return tweetRepository.findTweetById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<TweetProjection> getRepliesByTweetId(Long tweetId) {
        List<TweetsProjection> repliesByTweetId = tweetRepository.getRepliesByTweetId(tweetId);
        return repliesByTweetId.contains(null) ? new ArrayList<>() : repliesByTweetId.stream()
                .map(TweetsProjection::getTweet)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TweetProjection> getQuotesByTweetId(Pageable pageable, Long tweetId) {
        return tweetRepository.getQuotesByTweetId(pageable, tweetId);
    }

    @Override
    public List<UserProjection> getLikedUsersByTweetId(Long tweetId) {
        return tweetRepository.getLikedUsersByTweetId(tweetId);
    }

    @Override
    public List<UserProjection> getRetweetedUsersByTweetId(Long tweetId) {
        return tweetRepository.getRetweetedUsersByTweetId(tweetId);
    }

    @Override
    public Page<TweetProjection> getMediaTweets(Pageable pageable) {
        return tweetRepository.findAllTweetsWithImages(pageable);
    }

    @Override
    public Page<TweetProjection> getTweetsWithVideo(Pageable pageable) {
        return tweetRepository.findAllTweetsWithVideo(pageable);
    }

    @Override
    public Page<TweetProjection> getFollowersTweets(Pageable pageable) {
        Long userId = authenticationService.getAuthenticatedUserId();
        List<Long> userFollowersIds = userRepository.getUserFollowersIds(userId);
        userFollowersIds.add(userId);
        return tweetRepository.findTweetsByUserIds(userFollowersIds, pageable);
    }

    @Override
    public List<TweetProjection> getScheduledTweets() {
        Long userId = authenticationService.getAuthenticatedUserId();
        return tweetRepository.findAllScheduledTweetsByUserId(userId);
    }

    @Override
    @Transactional
    public TweetProjection createNewTweet(Tweet tweet) {
        Tweet createdTweet = createTweet(tweet);
        return getTweetById(createdTweet.getId());
    }

    @Override
    @Transactional(rollbackFor = ApiRequestException.class)
    public TweetProjection createPoll(Long pollDateTime, List<String> choices, Tweet tweet) {
        if (choices.size() < 2 || choices.size() > 4) {
            throw new ApiRequestException("Incorrect poll choices", HttpStatus.BAD_REQUEST);
        }
        Tweet createdTweet = createTweet(tweet);
        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(pollDateTime);
        Poll poll = new Poll();
        poll.setTweet(createdTweet);
        poll.setDateTime(dateTime);
        List<PollChoice> pollChoices = new ArrayList<>();
        choices.forEach(choice -> {
            if (choice.length() == 0 || choice.length() > 25) {
                throw new ApiRequestException("Incorrect choice text length", HttpStatus.BAD_REQUEST);
            }
            PollChoice pollChoice = new PollChoice();
            pollChoice.setChoice(choice);
            pollChoiceRepository.save(pollChoice);
            pollChoices.add(pollChoice);
        });
        poll.setPollChoices(pollChoices);
        pollRepository.save(poll);
        createdTweet.setPoll(poll);
//        tweetRepository.save(createdTweet);
        return getTweetById(createdTweet.getId());
    }

    @Override
    @Transactional
    public TweetProjection updateScheduledTweet(Tweet tweetInfo) {
        if (tweetInfo.getText().length() == 0 || tweetInfo.getText().length() > 280) {
            throw new ApiRequestException("Incorrect tweet text length", HttpStatus.BAD_REQUEST);
        }
        Tweet tweet = tweetRepository.findById(tweetInfo.getId())
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        tweet.setText(tweetInfo.getText());
        tweet.setImages(tweetInfo.getImages());
        return getTweetById(tweet.getId());
    }

    @Override
    @Transactional
    public String deleteScheduledTweets(List<Long> tweetsIds) {
        tweetsIds.forEach(this::deleteTweet);
        return "Scheduled tweets deleted.";
    }

    @Override
    @Transactional
    public Tweet deleteTweet(Long tweetId) { // TODO rewrite
        User user = authenticationService.getAuthenticatedUser();
        Tweet tweet = user.getTweets().stream()
                .filter(t -> t.getId().equals(tweetId))
                .findFirst().get();
        imageRepository.deleteAll(tweet.getImages());
        likeTweetRepository.deleteAll(tweet.getLikedTweets());
        retweetRepository.deleteAll(tweet.getRetweets());
        tweet.getReplies().forEach(reply -> reply.getUser().getTweets()
                .removeIf(replyingTweet -> replyingTweet.equals(reply)));
        List<Tweet> replies = new ArrayList<>(tweet.getReplies());
        tweet.getReplies().removeAll(tweet.getReplies());
        tweetRepository.deleteAll(replies);
        List<Notification> notifications = user.getNotifications().stream()
                .filter(notification -> !notification.getNotificationType().equals(NotificationType.FOLLOW)
                        && notification.getTweet().getId().equals(tweet.getId()))
                .collect(Collectors.toList());
        notifications.forEach(notification -> {
            user.getNotifications().remove(notification);
            notificationRepository.delete(notification);
        });
        List<Bookmark> bookmarks = user.getBookmarks();
        Optional<Bookmark> bookmark = bookmarks.stream()
                .filter(b -> b.getTweet().equals(tweet))
                .findFirst();
        if (bookmark.isPresent()) {
            bookmarks.remove(bookmark.get());
            bookmarkRepository.delete(bookmark.get());
        }
        if (tweet.getAddressedTweetId() != null) {
            Tweet addressedTweet = tweetRepository.getOne(tweet.getAddressedTweetId());
            List<Tweet> addressedTweetReplies = addressedTweet.getReplies();
            Tweet reply = addressedTweetReplies.stream()
                    .filter(r -> r.equals(tweet))
                    .findFirst().get();
            addressedTweetReplies.remove(reply);
            user.getTweets().remove(tweet);
            tweetRepository.delete(tweet);
            return addressedTweet;
        }
        List<Tag> tags = tagRepository.findByTweets_Id(tweetId);
        tags.forEach(tag -> {
            tag.getTweets().remove(tweet);
            long tweetsQuantity = tag.getTweetsQuantity() - 1;

            if (tweetsQuantity == 0) {
                tagRepository.delete(tag);
            } else {
                tag.setTweetsQuantity(tweetsQuantity);
            }
        });
        List<User> unreadMessagesWithTweet = userRepository.findByUnreadMessages_Tweet(tweet);
        unreadMessagesWithTweet
                .forEach(user1 -> user1.getUnreadMessages()
                        .removeIf(chatMessage -> chatMessage.getTweet() != null
                                && chatMessage.getTweet().getId().equals(tweet.getId())));
        List<ChatMessage> messagesWithTweet = chatMessageRepository.findByTweet(tweet);
        chatMessageRepository.deleteAll(messagesWithTweet);

        List<Tweet> tweetsWithQuote = tweetRepository.findByQuoteTweetId(tweetId);
        tweetsWithQuote.forEach(quote -> quote.setQuoteTweet(null));

        if (user.getPinnedTweet() != null) {
            user.setPinnedTweet(null);
        }
        user.getTweets().remove(tweet);
        tweetRepository.delete(tweet);
        return tweet;
    }

    @Override
    public List<TweetProjection> searchTweets(String text) {
        return tweetRepository.findAllByText(text).stream()
                .map(TweetsProjection::getTweet)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Map<String, Object> likeTweet(Long tweetId) {
        User user = authenticationService.getAuthenticatedUser();
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        List<LikeTweet> likedTweets = user.getLikedTweets();
        Optional<LikeTweet> likedTweet = likedTweets.stream()
                .filter(t -> t.getTweet().getId().equals(tweet.getId()))
                .findFirst();
        boolean isTweetLiked;

        if (likedTweet.isPresent()) {
            likedTweets.remove(likedTweet.get());
            likeTweetRepository.delete(likedTweet.get());
            user.setLikeCount(user.getLikeCount() - 1);
            isTweetLiked = false;
        } else {
            LikeTweet newLikedTweet = new LikeTweet();
            newLikedTweet.setTweet(tweet);
            newLikedTweet.setUser(user);
            user.setLikeCount(user.getLikeCount() + 1);
            likeTweetRepository.save(newLikedTweet);
            likedTweets.add(newLikedTweet);
            isTweetLiked = true;
        }
        Notification notification = notificationHandler(user, tweet, NotificationType.LIKE);
        return Map.of("notification", notification, "isTweetLiked", isTweetLiked);
    }

    @Override
    @Transactional
    public Map<String, Object> retweet(Long tweetId) {
        User user = authenticationService.getAuthenticatedUser();
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        List<Retweet> retweets = user.getRetweets();
        Optional<Retweet> retweet = retweets.stream()
                .filter(t -> t.getTweet().getId().equals(tweet.getId()))
                .findFirst();
        boolean isTweetRetweeted;

        if (retweet.isPresent()) {
            retweets.remove(retweet.get());
            retweetRepository.delete(retweet.get());
            user.setTweetCount(user.getTweetCount() - 1);
            isTweetRetweeted = false;
        } else {
            Retweet newRetweet = new Retweet();
            newRetweet.setTweet(tweet);
            newRetweet.setUser(user);
            retweetRepository.save(newRetweet);
            retweets.add(newRetweet);
            user.setTweetCount(user.getTweetCount() + 1);
            isTweetRetweeted = true;
        }
        Notification notification = notificationHandler(user, tweet, NotificationType.RETWEET);
        return Map.of("notification", notification, "isTweetRetweeted", isTweetRetweeted);
    }

    @Override
    @Transactional
    public TweetProjection replyTweet(Long tweetId, Tweet reply) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        reply.setAddressedTweetId(tweetId);
        Tweet replyTweet = createTweet(reply);
        tweet.getReplies().add(replyTweet);
        return getTweetById(replyTweet.getId());
    }

    @Override
    @Transactional
    public TweetProjection quoteTweet(Long tweetId, Tweet quote) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        User user = authenticationService.getAuthenticatedUser();
        user.setTweetCount(user.getTweetCount() + 1);
        quote.setQuoteTweet(tweet);
        Tweet createdTweet = createTweet(quote);
        tweet.getQuotes().add(createdTweet);
        return getTweetById(createdTweet.getId());
    }

    @Override
    @Transactional
    public TweetProjection changeTweetReplyType(Long tweetId, ReplyType replyType) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        User user = authenticationService.getAuthenticatedUser();
        user.getTweets().stream()
                .filter(userTweet -> userTweet.getId().equals(tweet.getId()))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        tweet.setReplyType(replyType);
        return getTweetById(tweet.getId());
    }

    @Override
    @Transactional
    public TweetProjection voteInPoll(Long tweetId, Long pollId, Long pollChoiceId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new ApiRequestException("Poll not found", HttpStatus.NOT_FOUND));
        PollChoice pollChoice = pollChoiceRepository.findById(pollChoiceId)
                .orElseThrow(() -> new ApiRequestException("Poll choice not found", HttpStatus.NOT_FOUND));
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));

        if (!tweet.getPoll().getId().equals(poll.getId())) {
            throw new ApiRequestException("Poll in tweet not exist", HttpStatus.NOT_FOUND);
        }
        User user = authenticationService.getAuthenticatedUser();
        List<PollChoice> pollChoices = tweet.getPoll().getPollChoices().stream()
                .peek(choice -> {
                    if (choice.getId().equals(pollChoice.getId())) {
                        choice.getVotedUser().add(user);
                    }
                })
                .collect(Collectors.toList());
        tweet.getPoll().setPollChoices(pollChoices);
        return getTweetById(tweet.getId());
    }

    public List<Long> getRetweetsUserIds(Long tweetId) {
        List<Long> retweetsUserIds = tweetRepository.getRetweetsUserIds(tweetId);
        return retweetsUserIds.contains(null) ? new ArrayList<>() : retweetsUserIds;
    }

    public boolean isUserLikedTweet(Long tweetId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return tweetRepository.isUserLikedTweet(authUserId, tweetId);
    }

    public boolean isUserRetweetedTweet(Long tweetId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return tweetRepository.isUserRetweetedTweet(authUserId, tweetId);
    }

    public boolean isUserBookmarkedTweet(Long tweetId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return tweetRepository.isUserBookmarkedTweet(authUserId, tweetId);
    }

    private Notification notificationHandler(User user, Tweet tweet, NotificationType notificationType) {
        Notification notification = new Notification();
        notification.setNotificationType(notificationType);
        notification.setUser(user);
        notification.setTweet(tweet);

        if (!tweet.getUser().getId().equals(user.getId())) {
            Optional<Notification> userNotification = tweet.getUser().getNotifications().stream()
                    .filter(n -> n.getNotificationType().equals(notificationType)
                            && n.getTweet().equals(tweet)
                            && n.getUser().equals(user))
                    .findFirst();

            if (userNotification.isEmpty()) {
                Notification newNotification = notificationRepository.save(notification);
                tweet.getUser().setNotificationsCount(tweet.getUser().getNotificationsCount() + 1);
                List<Notification> notifications = tweet.getUser().getNotifications();
                notifications.add(newNotification);
                userRepository.save(tweet.getUser());
                return newNotification;
            }
        }
        return notification;
    }

    @Transactional
    public Tweet createTweet(Tweet tweet) {
        if (tweet.getText().length() == 0 || tweet.getText().length() > 280) {
            throw new ApiRequestException("Incorrect tweet text length", HttpStatus.BAD_REQUEST);
        }
        User user = authenticationService.getAuthenticatedUser();
        tweet.setUser(user);
        boolean isMediaTweetCreated = parseMetadataFromURL(tweet); // find metadata from url
        Tweet createdTweet = tweetRepository.save(tweet);

        if (isMediaTweetCreated || createdTweet.getImages() != null) {
            user.setMediaTweetCount(user.getMediaTweetCount() + 1);
        } else {
            user.setTweetCount(user.getTweetCount() + 1);
        }
        user.getTweets().add(createdTweet);
//        userRepository.save(user);
        parseHashtagInText(tweet); // find hashtag in text

        Notification notification = new Notification();
        notification.setNotificationType(NotificationType.TWEET);
        notification.setUser(user);
        notification.setTweet(tweet);

        user.getSubscribers().forEach(subscriber -> {
            subscriber.setNotificationsCount(subscriber.getNotificationsCount() + 1);
            List<Notification> notifications = subscriber.getNotifications();
//            userRepository.save(subscriber);
            notifications.add(notification);
            notificationRepository.save(notification);
        });
        return createdTweet;
    }

    private void parseHashtagInText(Tweet tweet) {
        Pattern pattern = Pattern.compile("(#\\w+)\\b");
        Matcher match = pattern.matcher(tweet.getText());
        List<String> hashtags = new ArrayList<>();

        while (match.find()) {
            hashtags.add(match.group(1));
        }

        if (!hashtags.isEmpty()) {
            hashtags.forEach(hashtag -> {
                Tag tag = tagRepository.findByTagName(hashtag);

                if (tag != null) {
                    Long tweetsQuantity = tag.getTweetsQuantity();
                    tweetsQuantity = tweetsQuantity + 1;
                    tag.setTweetsQuantity(tweetsQuantity);
                    List<Tweet> taggedTweets = tag.getTweets();
                    taggedTweets.add(tweet);
                    tagRepository.save(tag);
                } else {
                    Tag newTag = new Tag();
                    newTag.setTagName(hashtag);
                    newTag.setTweetsQuantity(1L);
                    newTag.setTweets(Collections.singletonList(tweet));
                    tagRepository.save(newTag);
                }
            });
        }
    }

    @SneakyThrows
    private boolean parseMetadataFromURL(Tweet tweet) {
        Pattern urlRegex = Pattern.compile("https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+", Pattern.CASE_INSENSITIVE);
        Pattern imgRegex = Pattern.compile("\\.(jpeg|jpg|gif|png)$", Pattern.CASE_INSENSITIVE);
        Pattern youTubeUrlRegex = Pattern.compile("(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*", Pattern.CASE_INSENSITIVE);
        String text = tweet.getText();
        Matcher matcher = urlRegex.matcher(text);

        if (matcher.find()) {
            String url = text.substring(matcher.start(), matcher.end());
            matcher = imgRegex.matcher(url);
            tweet.setLink(url);

            if (matcher.find()) {
                tweet.setLinkCover(url);
            } else if (!url.contains("youtu")) {
                Document doc = Jsoup.connect(url).get(); // TODO add error handler
                Elements title = doc.select("meta[name$=title],meta[property$=title]");
                Elements description = doc.select("meta[name$=description],meta[property$=description]");
                Elements cover = doc.select("meta[name$=image],meta[property$=image]");

                BufferedImage coverData = ImageIO.read(new URL(getContent(cover.first())));
                double coverDataSize = (504.0 / (double) coverData.getWidth()) * coverData.getHeight();

                tweet.setLinkTitle(getContent(title.first()));
                tweet.setLinkDescription(getContent(description.first()));
                tweet.setLinkCover(getContent(cover.first()));
                tweet.setLinkCoverSize(coverDataSize > 267.0 ? LinkCoverSize.SMALL : LinkCoverSize.LARGE);
            } else {
                String youTubeVideoId = null;
                Matcher youTubeMatcher = youTubeUrlRegex.matcher(url);

                if (youTubeMatcher.find()) {
                    youTubeVideoId = youTubeMatcher.group();
                }
                String youtubeUrl = String.format(googleApiUrl, youTubeVideoId, googleApiKey);
                String youTubeVideData = restTemplate.getForObject(youtubeUrl, String.class);
                JSONObject jsonObject = new JSONObject(youTubeVideData);
                JSONArray items = jsonObject.getJSONArray("items");
                String videoTitle = null;
                String videoCoverImage = null;

                for (int i = 0; i < items.length(); i++) {
                    videoTitle = items.getJSONObject(i)
                            .getJSONObject("snippet")
                            .getString("title");
                    videoCoverImage = items.getJSONObject(i)
                            .getJSONObject("snippet")
                            .getJSONObject("thumbnails")
                            .getJSONObject("medium")
                            .getString("url");
                }
                tweet.setLinkTitle(videoTitle);
                tweet.setLinkCover(videoCoverImage);
                return true;
            }
        }
        return false;
    }

    private String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }
}
