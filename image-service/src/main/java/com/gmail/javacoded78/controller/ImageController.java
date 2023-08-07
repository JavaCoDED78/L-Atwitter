package com.gmail.javacoded78.controller;

import com.gmail.javacoded78.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.gmail.javacoded78.constants.PathConstants.API_V1_IMAGE;
import static com.gmail.javacoded78.constants.PathConstants.UPLOAD;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_IMAGE)
public class ImageController {

    private final ImageService imageService;

    @PostMapping(UPLOAD)
    public String uploadImage(@RequestPart("file") MultipartFile file) {
        return imageService.uploadImage(file);
    }
}
