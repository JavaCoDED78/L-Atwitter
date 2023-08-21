package com.gmail.javacoded78.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessage {

//    Token
    public  final String JWT_TOKEN_EXPIRED = "JWT token is expired or invalid";

//    User
    public  final String USER_NOT_FOUND = "User not found";
    public  final String USER_PROFILE_BLOCKED = "User profile blocked";
    public  final String USER_ID_NOT_FOUND = "User (id:%s) not found";
    public  final String BLANK_NAME = "What’s your name?";
    public  final String NAME_NOT_VALID = "Please enter a valid name.";
    public  final String ACTIVATION_CODE_NOT_FOUND = "Activation code not found.";
    public  final String INCORRECT_USERNAME_LENGTH = "Incorrect username length";
    public  final String INVALID_PHONE_NUMBER = "Not valid phone number";
    public  final String INVALID_GENDER_LENGTH = "Incorrect gender length";

//    Email
    public  final String EMAIL_NOT_FOUND = "Email not found";
    public  final String EMAIL_NOT_VALID = "Please enter a valid email address.";
    public  final String EMAIL_HAS_ALREADY_BEEN_TAKEN = "Email has already been taken.";

//    Password
    public  final String PASSWORDS_NOT_MATCH = "Passwords do not match.";
    public  final String INCORRECT_PASSWORD = "The password you entered was incorrect.";
    public  final String INVALID_PASSWORD_RESET_CODE = "Password reset code is invalid!";
    public  final String PASSWORD_LENGTH_ERROR = "Your password needs to be at least 8 characters";
    public  final String EMPTY_PASSWORD = "Password cannot be empty.";
    public  final String EMPTY_CURRENT_PASSWORD = "Current password cannot be empty.";
    public  final String EMPTY_PASSWORD_CONFIRMATION = "Password confirmation cannot be empty.";
    public  final String SHORT_PASSWORD = "Your password needs to be at least 8 characters. Please enter a longer one.";

//    Tweet
    public  final String TWEET_NOT_FOUND = "Tweet not found";
    public  final String INCORRECT_TWEET_TEXT_LENGTH = "Incorrect tweet text length";
    public  final String TWEET_DELETED = "Sorry, that Tweet has been deleted.";

//    Poll
    public  final String INCORRECT_POLL_CHOICES = "Incorrect poll choices";
    public  final String INCORRECT_CHOICE_TEXT_LENGTH = "Incorrect choice text length";
    public  final String POLL_NOT_FOUND = "Poll in tweet not exist";
    public  final String POLL_CHOICE_NOT_FOUND = "Poll choice not found";
    public  final String POLL_IS_NOT_AVAILABLE = "Poll is not available";
    public  final String USER_VOTED_IN_POLL = "User voted in poll";

//    Topic
    public  final String TOPIC_NOT_FOUND = "Topic not found";

//    Tag
    public  final String TAG_NOT_FOUND = "Tag not found";

//    Notification
    public  final String NOTIFICATION_NOT_FOUND = "Notification not found";

//    Chat
    public  final String CHAT_NOT_FOUND = "Chat not found";
    public  final String CHAT_PARTICIPANT_NOT_FOUND = "Participant not found";
    public  final String CHAT_PARTICIPANT_BLOCKED = "Participant is blocked";
    public  final String INCORRECT_CHAT_MESSAGE_LENGTH = "Incorrect chat message length";

//    Lists
    public  final String LIST_NOT_FOUND = "List not found";
    public  final String INCORRECT_LIST_NAME_LENGTH = "Incorrect list name length";
    public  final String LIST_OWNER_NOT_FOUND = "List owner not found";
    public  final String USER_ID_BLOCKED = "User with ID:%s is blocked";
}
