package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Image;
import com.gmail.javacoded78.latwitter.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User getUserById(Long userId);

    Image uploadImage(MultipartFile multipartFile);
}
