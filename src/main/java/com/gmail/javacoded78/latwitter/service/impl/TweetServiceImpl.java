package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.model.Bookmark;
import com.gmail.javacoded78.latwitter.model.LikeTweet;
import com.gmail.javacoded78.latwitter.model.Notification;
import com.gmail.javacoded78.latwitter.model.NotificationType;
import com.gmail.javacoded78.latwitter.model.Poll;
import com.gmail.javacoded78.latwitter.model.PollChoice;
import com.gmail.javacoded78.latwitter.model.ReplyType;
import com.gmail.javacoded78.latwitter.model.Retweet;
import com.gmail.javacoded78.latwitter.model.Tag;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.BookmarkRepository;
import com.gmail.javacoded78.latwitter.repository.ImageRepository;
import com.gmail.javacoded78.latwitter.repository.LikeTweetRepository;
import com.gmail.javacoded78.latwitter.repository.NotificationRepository;
import com.gmail.javacoded78.latwitter.repository.PollChoiceRepository;
import com.gmail.javacoded78.latwitter.repository.PollRepository;
import com.gmail.javacoded78.latwitter.repository.RetweetRepository;
import com.gmail.javacoded78.latwitter.repository.TagRepository;
import com.gmail.javacoded78.latwitter.repository.TweetRepository;
import com.gmail.javacoded78.latwitter.repository.UserRepository;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

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
    private final RestTemplate restTemplate;

    @Value("${google.api.key}")
    private String googleApiKey;

    @Override
    public List<Tweet> getTweets() {
        return tweetRepository.findByAddressedUsernameIsNullOrderByDateTimeDesc();
    }

    @Override
    public Tweet getTweetById(Long tweetId) {
        return tweetRepository.getOne(tweetId);
    }

    @Override
    public List<Tweet> getMediaTweets() {
        return tweetRepository.findByImagesIsNotNullOrderByDateTimeDesc();
    }

    @Override
    public Tweet createTweet(Tweet tweet) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        tweet.setUser(user);
        parseMetadataInText(tweet); // find metadata in link
        Tweet createdTweet = tweetRepository.save(tweet);
        user.getTweets().add(createdTweet);
        user.setTweetCount(user.getTweetCount() + 1);
        userRepository.save(user);
        parseHashtagInText(tweet); // find hashtag in text
        return createdTweet;
    }

    @Override
    public Tweet createPoll(Long pollDateTime, List<String> choices, Tweet tweet) {
        Tweet createdTweet = createTweet(tweet);
        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(pollDateTime);
        Poll poll = new Poll();
        poll.setTweet(createdTweet);
        poll.setDateTime(dateTime);
        List<PollChoice> pollChoices = new ArrayList<>();
        choices.forEach(choice -> {
            PollChoice pollChoice = new PollChoice();
            pollChoice.setChoice(choice);
            pollChoiceRepository.save(pollChoice);
            pollChoices.add(pollChoice);
        });
        poll.setPollChoices(pollChoices);
        pollRepository.save(poll);
        createdTweet.setPoll(poll);
        return tweetRepository.save(createdTweet);
    }

    @Override
    public Tweet deleteTweet(Long tweetId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        Tweet tweet = user.getTweets().stream()
                .filter(t -> t.getId().equals(tweetId))
                .findFirst().get();
        tweet.getImages().forEach(imageRepository::delete);
        tweet.getLikedTweets().forEach(likeTweetRepository::delete);
        tweet.getRetweets().forEach(retweetRepository::delete);
        tweet.getReplies().forEach(reply -> reply.getUser().getTweets()
                .removeIf(replyingTweet -> replyingTweet.equals(reply)));
        List<Tweet> replies = new ArrayList<>(tweet.getReplies());
        tweet.getReplies().removeAll(tweet.getReplies());
        tweetRepository.deleteAll(replies);
        List<Notification> notifications = user.getNotifications().stream()
                .filter(notification -> notification.getTweet().equals(tweet))
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
        user.getTweets().remove(tweet);
        tweetRepository.delete(tweet);
        return tweet;
    }

    @Override
    public List<Tweet> searchTweets(String text) {
        Set<Tweet> tweets = new HashSet<>();
        List<Tweet> tweetsByText = tweetRepository.findAllByTextIgnoreCaseContaining(text);
        List<Tag> tagsByText = tagRepository.findByTagNameContaining(text);
        List<User> usersByText = userRepository.findByFullNameOrUsernameContaining(text, text);

        if (tweetsByText != null) {
            tweets.addAll(tweetsByText);
        }
        if (tagsByText != null) {
            tagsByText.forEach(tag -> tweets.addAll(tag.getTweets()));
        }
        if (usersByText != null) {
            usersByText.forEach(user -> tweets.addAll(tweetRepository.findAllByUser(user)));
        }
        return List.copyOf(tweets);
    }

    @Override
    public Notification likeTweet(Long tweetId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        Tweet tweet = tweetRepository.getOne(tweetId);

        List<LikeTweet> likedTweets = user.getLikedTweets();
        Optional<LikeTweet> likedTweet = likedTweets.stream()
                .filter(t -> t.getTweet().equals(tweet))
                .findFirst();

        if (likedTweet.isPresent()) {
            likedTweets.remove(likedTweet.get());
            likeTweetRepository.delete(likedTweet.get());
        } else {
            LikeTweet newLikedTweet = new LikeTweet();
            newLikedTweet.setTweet(tweet);
            newLikedTweet.setUser(user);
            likeTweetRepository.save(newLikedTweet);
            likedTweets.add(newLikedTweet);
        }

        Optional<Notification> notification = tweet.getUser().getNotifications().stream()
                .filter(n -> n.getNotificationType().equals(NotificationType.LIKE)
                        && n.getTweet().equals(tweet)
                        && n.getUser().equals(user))
                .findFirst();

        if (notification.isEmpty()) {
            Notification newNotification = new Notification();
            newNotification.setNotificationType(NotificationType.LIKE);
            newNotification.setUser(user);
            newNotification.setTweet(tweet);
            notificationRepository.save(newNotification);
            tweet.getUser().setNotificationsCount(tweet.getUser().getNotificationsCount() + 1);
            List<Notification> notifications = tweet.getUser().getNotifications();
            notifications.add(newNotification);
            userRepository.save(user);
            return newNotification;
        }
        tweetRepository.save(tweet);
        return notification.get();
    }

    @Override
    public Notification retweet(Long tweetId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        Tweet tweet = tweetRepository.getOne(tweetId);

        List<Retweet> retweets = user.getRetweets();
        Optional<Retweet> retweet = retweets.stream()
                .filter(t -> t.getTweet().equals(tweet))
                .findFirst();

        if (retweet.isPresent()) {
            retweets.remove(retweet.get());
            retweetRepository.delete(retweet.get());
            user.setTweetCount(user.getTweetCount() - 1);
        } else {
            Retweet newRetweet = new Retweet();
            newRetweet.setTweet(tweet);
            newRetweet.setUser(user);
            retweetRepository.save(newRetweet);
            retweets.add(newRetweet);
            user.setTweetCount(user.getTweetCount() + 1);
        }

        Optional<Notification> notification = tweet.getUser().getNotifications().stream()
                .filter(n -> n.getNotificationType().equals(NotificationType.RETWEET)
                        && n.getTweet().equals(tweet)
                        && n.getUser().equals(user))
                .findFirst();

        if (notification.isEmpty()) {
            Notification newNotification = new Notification();
            newNotification.setNotificationType(NotificationType.RETWEET);
            newNotification.setUser(user);
            newNotification.setTweet(tweet);
            notificationRepository.save(newNotification);
            tweet.getUser().setNotificationsCount(tweet.getUser().getNotificationsCount() + 1);
            List<Notification> notifications = tweet.getUser().getNotifications();
            notifications.add(newNotification);
            userRepository.save(user);
            return newNotification;
        }
        tweetRepository.save(tweet);
        return notification.get();
    }

    @Override
    public Tweet replyTweet(Long tweetId, Tweet reply) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        user.setTweetCount(user.getTweetCount() + 1);
        userRepository.save(user);

        reply.setAddressedTweetId(tweetId);
        Tweet replyTweet = createTweet(reply);
        Tweet tweet = tweetRepository.getOne(tweetId);
        tweet.getReplies().add(replyTweet);
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet quoteTweet(Long tweetId, Tweet quote) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        user.setTweetCount(user.getTweetCount() + 1);
        userRepository.save(user);

        Tweet tweet = tweetRepository.getOne(tweetId);
        quote.setQuoteTweet(tweet);
        return createTweet(quote);
    }

    @Override
    public Tweet changeTweetReplyType(Long tweetId, ReplyType replyType) {
        Tweet tweet = tweetRepository.getOne(tweetId);
        tweet.setReplyType(replyType);
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet voteInPoll(Long tweetId, Long pollChoiceId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        Tweet tweet = tweetRepository.getOne(tweetId);
        PollChoice pollChoice = tweet.getPoll().getPollChoices().stream()
                .filter(choice -> choice.getId().equals(pollChoiceId))
                .findFirst().get();
        pollChoice.getVotedUser().add(user);
        return tweetRepository.save(tweet);
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
    private void parseMetadataInText(Tweet tweet) {
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

                tweet.setLinkTitle(getContent(title.first()));
                tweet.setLinkDescription(getContent(description.first()));
                tweet.setLinkCover(getContent(cover.first()));
            } else {
                String youTubeVideoId = null;
                Matcher youTubeMatcher = youTubeUrlRegex.matcher(url);

                if (youTubeMatcher.find()){
                    youTubeVideoId = youTubeMatcher.group();
                }
                String youtubeUrl = String.format(
                        "https://www.googleapis.com/youtube/v3/videos?id=%s&key=%s&part=snippet,contentDetails,statistics,status",
                        youTubeVideoId, googleApiKey);
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
            }
        }
    }

    private String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }
}
