package com.gmail.javacoded78.service;

import com.gmail.javacoded78.dto.EmailRequest;

import javax.mail.MessagingException;

public interface EmailService {

    void sendMessageHtml(EmailRequest emailRequest) throws MessagingException;
}