package com.gmail.javacoded78.service;

import javax.mail.MessagingException;

public interface EmailService {

    void sendMessageHtml(EmailRequest emailRequest) throws MessagingException;
}