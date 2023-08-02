package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import static com.gmail.javacoded78.controller.PathConstants.API_V1_IMAGE;

@FeignClient(name = "image-service", configuration = FeignConfiguration.class)
public interface ImageClient {

    @PostMapping(API_V1_IMAGE + "/upload")
    String uploadImage(@RequestPart("file") MultipartFile file);
}
