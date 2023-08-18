package com.gmail.javacoded78.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final AmazonS3 amazonS3client;

    @Value("${amazon.s3.bucket.name}")
    private String bucketName;

    @SneakyThrows
    @Override
    public String uploadImage(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        Path file = Path.of(fileName);
        try {
            multipartFile.transferTo(file);
            amazonS3client.putObject(bucketName, fileName, file.toFile());
            return amazonS3client.getUrl(bucketName, fileName).toString();
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
