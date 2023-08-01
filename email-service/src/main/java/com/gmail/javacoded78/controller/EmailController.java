package com.gmail.javacoded78.controller;

import com.gmail.javacoded78.dto.EmailRequest;
import com.gmail.javacoded78.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

import static com.gmail.javacoded78.controller.PathConstants.API_V1_EMAIL;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_EMAIL)
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/suggested")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        emailService.sendMessageHtml(emailRequest);
        return ResponseEntity.noContent().build();
    }
}
