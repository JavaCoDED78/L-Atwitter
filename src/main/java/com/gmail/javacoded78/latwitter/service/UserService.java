package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Image;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User getUserById(Long userId);

    List<Tweet> getUserTweets(Long userId);

    Image uploadImage(MultipartFile multipartFile);

    User updateUserProfile(User userInfo);

    User follow(Long userId);

    User unfollow(Long userId);
}
