package com.gmail.javacoded78.service.impl;
;
import com.gmail.javacoded78.dto.ChatTweetUserResponse;
import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.NotificationRequest;
import com.gmail.javacoded78.dto.TweetAdditionalInfoUserResponse;
import com.gmail.javacoded78.dto.TweetAuthorResponse;
import com.gmail.javacoded78.dto.UserResponse;
import com.gmail.javacoded78.dto.lists.UserIdsRequest;
import com.gmail.javacoded78.dto.notification.NotificationResponse;
import com.gmail.javacoded78.enums.LinkCoverSize;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.NotificationClient;
import com.gmail.javacoded78.feign.TagClient;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.model.LikeTweet;
import com.gmail.javacoded78.model.Poll;
import com.gmail.javacoded78.model.PollChoice;
import com.gmail.javacoded78.model.PollChoiceVoted;
import com.gmail.javacoded78.model.Retweet;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.BookmarkRepository;
import com.gmail.javacoded78.repository.LikeTweetRepository;
import com.gmail.javacoded78.repository.PollChoiceRepository;
import com.gmail.javacoded78.repository.PollChoiceVotedRepository;
import com.gmail.javacoded78.repository.PollRepository;
import com.gmail.javacoded78.repository.RetweetRepository;
import com.gmail.javacoded78.repository.projection.TweetAdditionalInfoProjection;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.service.TweetService;
import com.gmail.javacoded78.util.AuthUtil;
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
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final PollRepository pollRepository;
    private final PollChoiceRepository pollChoiceRepository;
    private final PollChoiceVotedRepository pollChoiceVotedRepository;
    private final LikeTweetRepository likeTweetRepository;
    private final RetweetRepository retweetRepository;
    private final BookmarkRepository bookmarkRepository;
    private final NotificationClient notificationClient;
    private final UserClient userClient;
    private final TagClient tagClient;
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
        TweetProjection tweet = tweetRepository.findTweetById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        checkIsValidUserProfile(tweet.getAuthorId());
        checkIsTweetDeleted(tweet.isDeleted());
        return tweet;
    }

    @Override
    public TweetAdditionalInfoProjection getTweetAdditionalInfoById(Long tweetId) {
        TweetAdditionalInfoProjection additionalInfo = tweetRepository.getTweetAdditionalInfoById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        checkIsValidUserProfile(additionalInfo.getAuthorId());
        checkIsTweetDeleted(additionalInfo.isDeleted());
        return additionalInfo;
    }

    @Override
    public List<TweetProjection> getRepliesByTweetId(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        checkIsValidUserProfile(tweet.getAuthorId());
        checkIsTweetDeleted(tweet.isDeleted());
        return tweetRepository.getRepliesByTweetId(tweetId);
    }

    @Override
    public Page<TweetProjection> getQuotesByTweetId(Pageable pageable, Long tweetId) {
        return tweetRepository.getQuotesByTweetId(tweetId, pageable);
    }

    @Override
    public HeaderResponse<UserResponse> getLikedUsersByTweetId(Long tweetId, Pageable pageable) {
        Page<Long> likedUserIds = likeTweetRepository.getLikedUserIds(tweetId, pageable);
        return userClient.getTweetLikedUsersByIds(new UserIdsRequest(likedUserIds.getContent()), pageable);
    }

    @Override
    public HeaderResponse<UserResponse> getRetweetedUsersByTweetId(Long tweetId, Pageable pageable) {
        Page<Long> retweetedUserIds = retweetRepository.getRetweetedUserIds(tweetId, pageable);
        return userClient.getRetweetedUsersByIds(new UserIdsRequest(retweetedUserIds.getContent()), pageable);
    }

    @Override
    public Page<TweetProjection> getMediaTweets(Pageable pageable) {
        return tweetRepository.getMediaTweets(pageable);
    }

    @Override
    public Page<TweetProjection> getTweetsWithVideo(Pageable pageable) {
        return tweetRepository.getTweetsWithVideo(pageable);
    }

    @Override
    public Page<TweetProjection> getFollowersTweets(Pageable pageable) {
        List<Long> userFollowersIds = userClient.getUserFollowersIds();
        return tweetRepository.getFollowersTweets(userFollowersIds, pageable);
    }

    @Override
    public Page<TweetProjection> getScheduledTweets(Pageable pageable) {
        Long userId = AuthUtil.getAuthenticatedUserId();
        return tweetRepository.getScheduledTweets(userId, pageable);
    }

    @Override
    @Transactional
    public TweetProjection createNewTweet(Tweet tweet) {
        Tweet createdTweet = createTweet(tweet);
        return getTweetById(createdTweet.getId());
    }

    @Override
    @Transactional
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
        return getTweetById(createdTweet.getId());
    }

    @Override
    @Transactional
    public TweetProjection updateScheduledTweet(Tweet tweetInfo) {
        Tweet tweet = tweetRepository.findById(tweetInfo.getId())
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        checkTweetTextLength(tweetInfo.getText());
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
    public String deleteTweet(Long tweetId) {
        Long userId = AuthUtil.getAuthenticatedUserId();
        Tweet tweet = tweetRepository.getTweetByUserId(userId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        userClient.updatePinnedTweetId(tweetId);
        tagClient.deleteTagsByTweetId(tweetId);
        tweet.setDeleted(true);
        return "Your Tweet was deleted";
    }

    @Override
    public Page<TweetProjection> searchTweets(String text, Pageable pageable) {
        List<Long> userIds = tweetRepository.getUserIdsByTweetText(text);
        List<Long> validUserIds = userClient.getValidUserIds(new UserIdsRequest(userIds), text);
        return tweetRepository.searchTweets(text, validUserIds, pageable);
    }

    @Override
    @Transactional
    public NotificationResponse likeTweet(Long tweetId) {
        Long userId = AuthUtil.getAuthenticatedUserId();
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        checkIsValidUserProfile(tweet.getAuthorId());
        LikeTweet likedTweet = likeTweetRepository.getLikedTweet(userId, tweetId);
        boolean isTweetLiked;

        if (likedTweet != null) {
            likeTweetRepository.delete(likedTweet);
            userClient.updateLikeCount(false);
            isTweetLiked = false;
        } else {
            LikeTweet newLikeTweet = new LikeTweet(userId, tweetId);
            likeTweetRepository.save(newLikeTweet);
            userClient.updateLikeCount(true);
            isTweetLiked = true;
        }
        return notificationClient.sendTweetNotification(new NotificationRequest(NotificationType.LIKE, isTweetLiked,
                tweet.getAuthorId(), userId, tweetId));
    }

    @Override
    @Transactional
    public NotificationResponse retweet(Long tweetId) {
        Long userId = AuthUtil.getAuthenticatedUserId();
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        checkIsValidUserProfile(tweet.getAuthorId());
        Retweet retweet = retweetRepository.isTweetRetweeted(userId, tweetId);
        boolean isRetweeted;

        if (retweet != null) {
            retweetRepository.delete(retweet);
            userClient.updateTweetCount(false);
            isRetweeted = false;
        } else {
            Retweet newRetweet = new Retweet(userId, tweetId);
            retweetRepository.save(newRetweet);
            userClient.updateTweetCount(true);
            isRetweeted = true;
        }
        return notificationClient.sendTweetNotification(new NotificationRequest(NotificationType.RETWEET, isRetweeted,
                tweet.getAuthorId(), userId, tweetId));
    }

    @Override
    @Transactional
    public TweetProjection replyTweet(Long tweetId, Tweet reply) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        checkIsValidUserProfile(tweet.getAuthorId());
        reply.setAddressedTweetId(tweetId);
        Tweet replyTweet = createTweet(reply);
        tweetRepository.addReply(tweetId, replyTweet.getId());
        return tweetRepository.findTweetById(replyTweet.getId()).get();
    }

    @Override
    @Transactional
    public TweetProjection quoteTweet(Long tweetId, Tweet quote) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        checkIsValidUserProfile(tweet.getAuthorId());
        userClient.updateTweetCount(true);
        quote.setQuoteTweet(tweet);
        Tweet createdTweet = createTweet(quote);
        tweet.getQuotes().add(createdTweet);
        return getTweetById(createdTweet.getId());
    }

    @Override
    @Transactional
    public TweetProjection changeTweetReplyType(Long tweetId, ReplyType replyType) {
        Long userId = AuthUtil.getAuthenticatedUserId();
        Tweet tweet = tweetRepository.getTweetByAuthorIdAndTweetId(tweetId, userId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));

        if (!tweet.getAuthorId().equals(userId)) {
            throw new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND);
        }
        tweet.setReplyType(replyType);
        return getTweetById(tweet.getId());
    }

    @Override
    @Transactional
    public TweetProjection voteInPoll(Long tweetId, Long pollId, Long pollChoiceId) {
        Tweet tweet = tweetRepository.getTweetByPollIdAndTweetId(tweetId, pollId)
                .orElseThrow(() -> new ApiRequestException("Poll in tweet not exist", HttpStatus.NOT_FOUND));
        checkIsValidUserProfile(tweet.getAuthorId());
        Poll poll = pollRepository.getPollByPollChoiceId(pollId, pollChoiceId)
                .orElseThrow(() -> new ApiRequestException("Poll choice not found", HttpStatus.NOT_FOUND));

        if (LocalDateTime.now().isAfter(poll.getDateTime())) {
            throw new ApiRequestException("Poll is not available", HttpStatus.BAD_REQUEST);
        }
        Long userId = AuthUtil.getAuthenticatedUserId();
        boolean ifUserVoted = pollChoiceVotedRepository.ifUserVoted(userId, pollChoiceId);

        if (ifUserVoted) {
            throw new ApiRequestException("User voted in poll", HttpStatus.BAD_REQUEST);
        }
        PollChoiceVoted votedUser = new PollChoiceVoted(userId, pollChoiceId);
        pollChoiceVotedRepository.save(votedUser);
        return getTweetById(tweetId);
    }

    @Override
    public Boolean getIsTweetBookmarked(Long tweetId) {
        return isUserBookmarkedTweet(tweetId);
    }

    public boolean isUserLikedTweet(Long tweetId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return likeTweetRepository.isUserLikedTweet(authUserId, tweetId);
    }

    public boolean isUserRetweetedTweet(Long tweetId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return retweetRepository.isUserRetweetedTweet(authUserId, tweetId);
    }

    public boolean isUserBookmarkedTweet(Long tweetId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return bookmarkRepository.isUserBookmarkedTweet(authUserId, tweetId);
    }

    public boolean isUserFollowByOtherUser(Long userId) {
        return userClient.isUserFollowByOtherUser(userId);
    }

    public boolean isMyProfileBlockedByUser(Long userId) {
        return userClient.isMyProfileBlockedByUser(userId);
    }

    public TweetAuthorResponse getTweetAuthor(Long userId) {
        return userClient.getTweetAuthor(userId);
    }

    public TweetAdditionalInfoUserResponse getTweetAdditionalInfoUser(Long userId) {
        return userClient.getTweetAdditionalInfoUser(userId);
    }

    private void checkTweetTextLength(String text) {
        if (text.length() == 0 || text.length() > 280) {
            throw new ApiRequestException("Incorrect tweet text length", HttpStatus.BAD_REQUEST);
        }
    }

    private void checkIsTweetDeleted(boolean isDeleted) {
        if (isDeleted) {
            throw new ApiRequestException("Sorry, that Tweet has been deleted.", HttpStatus.BAD_REQUEST);
        }
    }

    public boolean isUserHavePrivateProfile(Long userId) {
        return userClient.isUserHavePrivateProfile(userId);
    }

    private void checkIsValidUserProfile(Long userId) {
        if (isUserHavePrivateProfile(userId)) {
            throw new ApiRequestException("User not found", HttpStatus.BAD_REQUEST);
        }
        if (isMyProfileBlockedByUser(userId)) {
            throw new ApiRequestException("User profile blocked", HttpStatus.BAD_REQUEST);
        }
    }

    public ChatTweetUserResponse getChatTweetUser(Long userId) {
        return userClient.getChatTweetUser(userId);
    }

    @Transactional
    public Tweet createTweet(Tweet tweet) {
        checkTweetTextLength(tweet.getText());
        Long userId = AuthUtil.getAuthenticatedUserId();
        tweet.setAuthorId(userId);
        boolean isMediaTweetCreated = parseMetadataFromURL(tweet);
        tweetRepository.save(tweet);

        if (isMediaTweetCreated || tweet.getImages() != null) {
            userClient.updateMediaTweetCount(true);
        } else {
            userClient.updateTweetCount(true);
        }
        tagClient.parseHashtagsInText(tweet.getText(), tweet.getId());
        notificationClient.sendTweetNotificationToSubscribers(tweet.getId());
        return tweet;
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
