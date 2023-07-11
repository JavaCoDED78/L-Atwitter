package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.model.Chat;
import com.gmail.javacoded78.latwitter.model.ChatMessage;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.ChatMessageRepository;
import com.gmail.javacoded78.latwitter.repository.ChatRepository;
import com.gmail.javacoded78.latwitter.repository.UserRepository;
import com.gmail.javacoded78.latwitter.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public List<Chat> getUserChats() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        return user.getChats();
    }

    @Override
    public Chat createChat(Long userId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        User participant = userRepository.getOne(userId);
        Chat chat = new Chat();
        chat.setParticipants(Arrays.asList(user, participant));
        return chatRepository.save(chat);
    }

    @Override
    public List<ChatMessage> getChatMessages(Long chatId) {
        return chatMessageRepository.getAllByChatId(chatId);
    }

    @Override
    public User readChatMessages(Long chatId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        user.setUnreadMessages(user.getUnreadMessages().stream()
                .filter(message -> !message.getChat().getId().equals(chatId))
                .collect(Collectors.toList()));
        return userRepository.save(user);
    }

    @Override
    public ChatMessage addMessage(ChatMessage chatMessage, Long chatId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User author = userRepository.findByEmail(principal.getName());
        Chat chat = chatRepository.getOne(chatId);
        chatMessage.setAuthor(author);
        chatMessage.setChat(chat);
        chatMessageRepository.save(chatMessage);
        List<ChatMessage> messages = chat.getMessages();
        messages.add(chatMessage);
        chatRepository.save(chat);
        chatMessage.getChat().getParticipants()
                .forEach(user -> {
                    if (!user.getUsername().equals(author.getUsername())) {
                        List<ChatMessage> unread = user.getUnreadMessages();
                        unread.add(chatMessage);
                        user.setUnreadMessages(unread);
                        userRepository.save(user);
                    }
                });
        return chatMessage;
    }
}
