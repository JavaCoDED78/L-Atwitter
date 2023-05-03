package com.gmail.javacoded78.latwitter.mapper;

import com.gmail.javacoded78.latwitter.dto.request.RegistrationRequest;
import com.gmail.javacoded78.latwitter.dto.request.TweetRequest;
import com.gmail.javacoded78.latwitter.dto.response.TweetResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;
    private final UserService userService;

    UserResponse convertToUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    private Tweet convertToTweetEntity(TweetRequest tweetRequest) {
        return modelMapper.map(tweetRequest, Tweet.class);
    }

    private TweetResponse convertToTweetResponse(Tweet tweet) {
        return modelMapper.map(tweet, TweetResponse.class);
    }

    private List<TweetResponse> convertListToResponseDto(List<Tweet> tweets) {
        return tweets.stream()
                .map(this::convertToTweetResponse)
                .collect(Collectors.toList());
    }

    User convertToEntity(RegistrationRequest registrationRequest) {
        return modelMapper.map(registrationRequest, User.class);
    }

    public UserResponse getUserById(Long userId) {
        return convertToUserResponse(userService.getUserById(userId));
    }

    public List<TweetResponse> getTweets() {
        return convertListToResponseDto(userService.getTweets());
    }

    public TweetResponse getTweetById(Long tweetId) {
        return convertToTweetResponse(userService.getTweetById(tweetId));
    }

    public List<TweetResponse> createTweet(TweetRequest tweetRequest) {
        return convertListToResponseDto(userService.createTweet(convertToTweetEntity(tweetRequest)));
    }

    public List<TweetResponse> deleteTweet(Long tweetId) {
        return convertListToResponseDto(userService.deleteTweet(tweetId));
    }
}
