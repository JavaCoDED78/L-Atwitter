package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.common.dto.ImageResponse;
import com.gmail.javacoded78.common.mapper.BasicMapper;
import com.gmail.javacoded78.common.models.Image;
import com.gmail.javacoded78.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ImageMapper {

    private final BasicMapper mapper;
    private final ImageService imageService;

    public String uploadImage(MultipartFile file) {
        return imageService.uploadImage(file);
    }

    public Image saveImage(Image image) {
        return imageService.saveImage(image);
    }

    public void deleteImage(Image image) {
        imageService.deleteImage(image);
    }
}
