package com.gmail.javacoded78.controller;

import com.gmail.javacoded78.dto.ImageResponse;
import com.gmail.javacoded78.mapper.ImageMapper;
import com.gmail.javacoded78.models.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageMapper imageMapper;

    @PostMapping("/upload")
    public ImageResponse uploadImage(@RequestPart("file") MultipartFile file) {
        return imageMapper.uploadImage(file);
    }

    @PostMapping("/save")
    public Image saveImage(@RequestBody Image image) {
        return imageMapper.saveImage(image);
    }

    @PostMapping("/delete")
    public void deleteImage(@RequestBody Image image) {
        imageMapper.deleteImage(image);
    }
}
