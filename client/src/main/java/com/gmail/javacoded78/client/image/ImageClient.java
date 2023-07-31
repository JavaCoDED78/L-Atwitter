package com.gmail.javacoded78.client.image;

import com.gmail.javacoded78.common.configuration.FeignConfiguration;
import com.gmail.javacoded78.common.dto.ImageResponse;
import com.gmail.javacoded78.common.models.Image;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import static com.gmail.javacoded78.common.controller.PathConstants.API_V1_IMAGE;

@FeignClient(value = "image-service", configuration = FeignConfiguration.class)
public interface ImageClient {

    @PostMapping(API_V1_IMAGE + "/upload")
    ImageResponse uploadImage(@RequestPart("file") MultipartFile file);

    @PostMapping(API_V1_IMAGE + "/save")
    Image saveImage(@RequestBody Image image);

    @DeleteMapping(API_V1_IMAGE + "/delete")
    void deleteImage(@RequestBody Image image);
}