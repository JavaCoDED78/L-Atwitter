package com.gmail.javacoded78.latwitter.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gmail.javacoded78.latwitter.model.Image;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.ImageRepository;
import com.gmail.javacoded78.latwitter.repository.UserRepository;
import com.gmail.javacoded78.latwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final AmazonS3 amazonS3client;

    @Value("${amazon.s3.bucket.name}")
    private String bucketName;

    @Override
    public User getUserById(Long userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public List<Tweet> getUserTweets(Long userId) {
        User user = userRepository.getOne(userId);
        return user.getTweets();
    }

    @Override
    public Image uploadImage(MultipartFile multipartFile) {
        Image image = new Image();
        if (multipartFile != null) {
            File file = new File(multipartFile.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipartFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
            amazonS3client.putObject(new PutObjectRequest(bucketName, fileName, file));
            image.setSrc(amazonS3client.getUrl(bucketName, fileName).toString());
            file.delete();
        }
        return imageRepository.save(image);
    }

    @Override
    public User updateUserProfile(User userInfo) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());

        if (userInfo.getAvatar() != null) {
            user.setAvatar(userInfo.getAvatar());
        }
        if (userInfo.getWallpaper() != null) {
            user.setWallpaper(userInfo.getWallpaper());
        }
        user.setUsername(userInfo.getUsername());
        user.setAbout(userInfo.getAbout());
        user.setLocation(userInfo.getLocation());
        user.setWebsite(userInfo.getWebsite());
        return userRepository.save(user);
    }

    @Override
    public User follow(Long userId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        User currentUser = userRepository.getOne(userId);
        user.getFollowers().add(currentUser);
        return userRepository.save(user);
    }

    @Override
    public User unfollow(Long userId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        User currentUser = userRepository.getOne(userId);
        user.getFollowers().remove(currentUser);
        return userRepository.save(user);
    }
}
