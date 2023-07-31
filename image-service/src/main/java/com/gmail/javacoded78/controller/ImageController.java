package com.gmail.javacoded78.controller;

import com.gmail.javacoded78.common.models.Image;
import com.gmail.javacoded78.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.gmail.javacoded78.common.controller.PathConstants.API_V1_IMAGE;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_IMAGE)
public class ImageController {

    private final ImageMapper imageMapper;

    @PostMapping("/upload")
    public String uploadImage(@RequestPart("file") MultipartFile file) {
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
