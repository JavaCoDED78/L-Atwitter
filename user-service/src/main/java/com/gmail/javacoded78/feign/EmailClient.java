package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.gmail.javacoded78.controller.PathConstants.API_V1_EMAIL;

@FeignClient(value = "email-service", configuration = FeignConfiguration.class)
public interface EmailClient {

    @PostMapping(API_V1_EMAIL + "/suggested")
    ResponseEntity<Void> sendEmail(@RequestBody EmailRequest emailRequest);
}
