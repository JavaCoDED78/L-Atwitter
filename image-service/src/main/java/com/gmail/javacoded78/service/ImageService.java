package com.gmail.javacoded78.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile multipartFile);
}
