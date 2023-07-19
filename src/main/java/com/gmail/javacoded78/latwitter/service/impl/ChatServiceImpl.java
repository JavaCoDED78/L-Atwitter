package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.exception.ApiRequestException;
import com.gmail.javacoded78.latwitter.model.Chat;
import com.gmail.javacoded78.latwitter.model.ChatMessage;
import com.gmail.javacoded78.latwitter.model.ChatParticipant;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.ChatMessageRepository;
import com.gmail.javacoded78.latwitter.repository.ChatParticipantRepository;
import com.gmail.javacoded78.latwitter.repository.ChatRepository;
import com.gmail.javacoded78.latwitter.repository.UserRepository;
import com.gmail.javacoded78.latwitter.repository.projection.chat.ChatMessageProjection;
import com.gmail.javacoded78.latwitter.repository.projection.chat.ChatParticipantsProjection;
import com.gmail.javacoded78.latwitter.repository.projection.chat.ChatProjection;
import com.gmail.javacoded78.latwitter.service.AuthenticationService;
import com.gmail.javacoded78.latwitter.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserServiceImpl userService;

    @Override
    public List<ChatProjection> getUserChats() {
        Long userId = authenticationService.getAuthenticatedUserId();
        List<ChatParticipantsProjection> chatParticipants = chatParticipantRepository.getChatParticipants(userId);
        return chatParticipants.stream()
                .filter(participant -> !participant.getParticipant().getLeftChat()
                        || !userService.isUserBlockedByMyProfile(participant.getParticipant().getUser().getId()))
                .map(participant -> participant.getParticipant().getChat())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ChatProjection createChat(Long userId) {
        User authUser = authenticationService.getAuthenticatedUser();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("Participant not found", HttpStatus.NOT_FOUND));

        if (userService.isUserBlockedByMyProfile(user.getId())) {
            throw new ApiRequestException("Participant is blocked", HttpStatus.BAD_REQUEST);
        }
        Optional<ChatParticipant> chatWithParticipant = getChatParticipant(user, userId);

        if (chatWithParticipant.isEmpty()) {
            Chat chat = new Chat();
            chatRepository.save(chat);
            ChatParticipant authUserParticipant = chatParticipantRepository.save(new ChatParticipant(authUser, chat));
            ChatParticipant userParticipant = chatParticipantRepository.save(new ChatParticipant(user, chat));
            chat.setParticipants(Arrays.asList(authUserParticipant, userParticipant));
            return chatRepository.getChatById(chat.getId());
        }
        return chatRepository.getChatById(chatWithParticipant.get().getChat().getId());
    }

    @Override
    public List<ChatMessageProjection> getChatMessages(Long chatId) {
        Long userId = authenticationService.getAuthenticatedUserId();
        List<ChatMessageProjection> messages = chatMessageRepository.getAllByChatId(chatId, userId).stream()
                .map(ChatMessagesProjection::getMessage)
                .collect(Collectors.toList());

        if (messages.contains(null)) {
            throw new ApiRequestException("Chat messages not found", HttpStatus.NOT_FOUND);
        }
        return messages;
    }

    @Override
    @Transactional
    public Integer readChatMessages(Long chatId) {
        User user = authenticationService.getAuthenticatedUser();
        user.setUnreadMessages(user.getUnreadMessages().stream()
                .filter(message -> !message.getChat().getId().equals(chatId))
                .collect(Collectors.toList()));
        return user.getUnreadMessages().size();
    }

    @Override
    @Transactional
    public Map<String, Object> addMessage(ChatMessage chatMessage, Long chatId) {
        User author = authenticationService.getAuthenticatedUser();
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ApiRequestException("Chat not found", HttpStatus.NOT_FOUND));
        Optional<ChatParticipant> chatParticipant = chat.getParticipants().stream()
                .filter(participant -> participant.getUser().getId().equals(author.getId()))
                .findAny();

        if (chatParticipant.isEmpty()) {
            throw new ApiRequestException("Chat participant not found", HttpStatus.NOT_FOUND);
        }

        Optional<ChatParticipant> blockedChatParticipant = chat.getParticipants().stream()
                .filter(participant -> !participant.getUser().getId().equals(author.getId())
                        && userService.isUserBlockedByMyProfile(author.getId())
                        || userService.isMyProfileBlockedByUser(participant.getUser().getId()))
                .findFirst();

        if (blockedChatParticipant.isPresent()) {
            throw new ApiRequestException("Participant is blocked", HttpStatus.BAD_REQUEST);
        }
        chatMessage.setAuthor(author);
        chatMessage.setChat(chat);
        updateParticipantWhoLeftChat(chat);
        chatMessageRepository.save(chatMessage);
        List<ChatMessage> messages = chat.getMessages();
        messages.add(chatMessage);
        notifyChatParticipants(chatMessage, author);

        List<Long> chatParticipantsIds = chat.getParticipants().stream()
                .map(participant -> participant.getUser().getId())
                .collect(Collectors.toList());
        ChatMessageProjection message = chatMessageRepository.getChatMessageById(chatMessage.getId());
        return Map.of("chatParticipantsIds", chatParticipantsIds, "message", message);
    }

    @Override
    @Transactional
    public List<ChatMessage> addMessageWithTweet(String text, Tweet tweet, List<User> users) {
        User author = authenticationService.getAuthenticatedUser();
        List<ChatMessage> chatMessages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setAuthor(author);
        chatMessage.setText(text);
        chatMessage.setTweet(tweet);
        users.forEach(user -> {
            boolean participantBlocked = isParticipantBlocked(author, user);
            Optional<ChatParticipant> chatWithParticipant = getChatParticipant(author, user.getId());

            if (chatWithParticipant.isEmpty() && !participantBlocked) {
                Chat chat = new Chat();
                Chat newChat = chatRepository.save(chat);
                ChatParticipant authorParticipant = chatParticipantRepository.save(new ChatParticipant(author, chat));
                ChatParticipant userParticipant = chatParticipantRepository.save(new ChatParticipant(user, chat));
                chat.setParticipants(Arrays.asList(authorParticipant, userParticipant));
                chatMessage.setChat(newChat);
                chatMessageRepository.save(chatMessage);
            } else if (!participantBlocked) {
                Chat participantsChat = chatWithParticipant.get().getChat();
                updateParticipantWhoLeftChat(participantsChat);
                chatMessage.setChat(participantsChat);
                ChatMessage newChatMessage = chatMessageRepository.save(chatMessage);
                List<ChatMessage> messages = participantsChat.getMessages();
                messages.add(newChatMessage);
                chatRepository.save(participantsChat);
            }
            chatMessages.add(chatMessage);
            notifyChatParticipants(chatMessage, author);
        });
        return chatMessages;
    }

    @Override
    public User getParticipant(Long participantId, Long chatId) {
        Long userId = authenticationService.getAuthenticatedUserId();
        Chat chat = chatRepository.getChatByUserId(chatId, userId)
                .orElseThrow(() -> new ApiRequestException("Chat not found", HttpStatus.NOT_FOUND));
        ChatParticipant chatParticipant = chat.getParticipants().stream()
                .filter(participant -> participant.getId().equals(participantId))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException("Participant not found", HttpStatus.NOT_FOUND));
        return chatParticipant.getUser();
    }

    @Override
    public String leaveFromConversation(Long participantId, Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ApiRequestException("Chat not found", HttpStatus.NOT_FOUND));
        int isChatParticipantUpdated = chatParticipantRepository.leaveFromConversation(participantId, chatId);

        if (isChatParticipantUpdated != 1) {
            throw new ApiRequestException("Participant not found", HttpStatus.NOT_FOUND);
        }

        boolean isParticipantsLeftFromChat = chat.getParticipants().stream().allMatch(ChatParticipant::isLeftChat);

        if (isParticipantsLeftFromChat) {
            chatMessageRepository.deleteAll(chat.getMessages());
            chatParticipantRepository.deleteAll(chat.getParticipants());
            chatRepository.delete(chat);
            return "Chat successfully deleted";
        }
        return "Successfully left the chat";
    }

    private boolean isParticipantBlocked(User user, User participant) {
        return user.getUserBlockedList().contains(participant);
    }

    private void updateParticipantWhoLeftChat(Chat chat) {
        chat.getParticipants().forEach(participant -> {
            if (participant.isLeftChat()) {
                participant.setLeftChat(false);
            }
        });
    }

    private Optional<ChatParticipant> getChatParticipant(User user, Long userId) {
        return user.getChats().stream()
                .filter(chatParticipant -> chatParticipant.getChat().getParticipants().stream()
                        .anyMatch(participant -> participant.getUser().getId().equals(userId)))
                .findFirst();
    }

    private void notifyChatParticipants(ChatMessage chatMessage, User author) {
        chatMessage.getChat().getParticipants()
                .forEach(participant -> {
                    if (!participant.getUser().getUsername().equals(author.getUsername())) {
                        List<ChatMessage> unread = participant.getUser().getUnreadMessages();
                        unread.add(chatMessage);
                        participant.getUser().setUnreadMessages(unread);
                        userRepository.save(participant.getUser());
                    }
                });
    }
}
