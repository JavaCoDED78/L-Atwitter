package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.model.Chat;
import com.gmail.javacoded78.latwitter.model.ChatMessage;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.ChatMessageRepository;
import com.gmail.javacoded78.latwitter.repository.ChatRepository;
import com.gmail.javacoded78.latwitter.repository.UserRepository;
import com.gmail.javacoded78.latwitter.service.AuthenticationService;
import com.gmail.javacoded78.latwitter.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final AuthenticationService authenticationService;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public List<Chat> getUserChats() {
        User user = authenticationService.getAuthenticatedUser();
        List<Chat> chats = user.getChats();
        chats.forEach(chat -> {
            if (chat.getParticipants().get(1).getId().equals(user.getId())) {
                Collections.swap(chat.getParticipants(), 1, 0);
            }
        });
        return chats;
    }

    @Override
    public Chat createChat(Long userId) {
        User user = authenticationService.getAuthenticatedUser();
        User participant = userRepository.getOne(userId);
        Optional<Chat> chatWithParticipant = user.getChats().stream()
                .filter(chat -> chat.getParticipants().get(0).getId().equals(participant.getId()))
                .findFirst();

        if (chatWithParticipant.isEmpty()) {
            Chat chat = new Chat();
            chat.setParticipants(Arrays.asList(user, participant));
            return chatRepository.save(chat);
        }
        return chatWithParticipant.get();
    }

    @Override
    public List<ChatMessage> getChatMessages(Long chatId) {
        return chatMessageRepository.getAllByChatId(chatId);
    }

    @Override
    public User readChatMessages(Long chatId) {
        User user = authenticationService.getAuthenticatedUser();
        user.setUnreadMessages(user.getUnreadMessages().stream()
                .filter(message -> !message.getChat().getId().equals(chatId))
                .collect(Collectors.toList()));
        return userRepository.save(user);
    }

    @Override
    public ChatMessage addMessage(ChatMessage chatMessage, Long chatId) {
        User author = authenticationService.getAuthenticatedUser();
        Chat chat = chatRepository.getOne(chatId);
        chatMessage.setAuthor(author);
        chatMessage.setChat(chat);
        chatMessageRepository.save(chatMessage);
        List<ChatMessage> messages = chat.getMessages();
        messages.add(chatMessage);
        chatRepository.save(chat);
        notifyChatParticipants(chatMessage, author);
        return chatMessage;
    }

    @Override
    public List<ChatMessage> addMessageWithTweet(String text, Tweet tweet, List<User> users) {
        User author = authenticationService.getAuthenticatedUser();
        List<ChatMessage> chatMessages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setAuthor(author);
        chatMessage.setText(text);
        chatMessage.setTweet(tweet);
        users.forEach(user -> {
            Optional<Chat> chatWithParticipant = author.getChats().stream()
                    .filter(c -> c.getParticipants().get(0).getId().equals(user.getId()))
                    .findFirst();

            if (chatWithParticipant.isEmpty()) {
                Chat chat = new Chat();
                chat.setParticipants(Arrays.asList(author, user));
                Chat newChat = chatRepository.save(chat);
                chatMessage.setChat(newChat);
                chatMessageRepository.save(chatMessage);
            } else {
                chatMessage.setChat(chatWithParticipant.get());
                ChatMessage newChatMessage = chatMessageRepository.save(chatMessage);
                List<ChatMessage> messages = chatWithParticipant.get().getMessages();
                messages.add(newChatMessage);
                chatRepository.save(chatWithParticipant.get());
            }
            chatMessages.add(chatMessage);
            notifyChatParticipants(chatMessage, author);
        });
        return chatMessages;
    }

    private void notifyChatParticipants(ChatMessage chatMessage, User author) {
        chatMessage.getChat().getParticipants()
                .forEach(user -> {
                    if (!user.getUsername().equals(author.getUsername())) {
                        List<ChatMessage> unread = user.getUnreadMessages();
                        unread.add(chatMessage);
                        user.setUnreadMessages(unread);
                        userRepository.save(user);
                    }
                });
    }
}
