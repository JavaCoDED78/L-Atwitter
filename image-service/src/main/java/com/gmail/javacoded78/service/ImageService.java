package com.gmail.javacoded78.service;

import com.gmail.javacoded78.common.models.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    Image uploadImage(MultipartFile multipartFile);

    Image saveImage(Image image);

    void deleteImage(Image image);
}
