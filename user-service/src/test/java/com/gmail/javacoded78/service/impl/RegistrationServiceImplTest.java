package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.amqp.AmqpProducer;
import com.gmail.javacoded78.dto.request.RegistrationRequest;
import com.gmail.javacoded78.model.User;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.security.JwtProvider;
import com.gmail.javacoded78.service.RegistrationService;
import com.gmail.javacoded78.service.util.UserServiceHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class RegistrationServiceImplTest extends AbstractAuthTest {

    private final RegistrationService registrationService;

    @MockBean
    private final UserRepository userRepository;

//    @MockBean
//    private final UserServiceHelper userServiceHelper;
//
//    @MockBean
//    private final PasswordEncoder passwordEncoder;
//
//    @MockBean
//    private final JwtProvider jwtProvider;
//
//    @MockBean
//    private final AmqpProducer amqpProducer;

    @Test
    void registration_ShouldReturnSuccessMessage() {
        User user = new User();
        user.setEmail(TestConstants.USER_EMAIL);
        user.setUsername(TestConstants.USERNAME);
        user.setFullName(TestConstants.USERNAME);
        user.setBirthday(TestConstants.BIRTHDAY);
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail(TestConstants.USER_EMAIL);
        request.setUsername(TestConstants.USERNAME);
        request.setBirthday(TestConstants.BIRTHDAY);
        BindingResult bindingResult = mock(BindingResult.class);
        when(userRepository.getUserByEmail(TestConstants.USER_EMAIL, User.class)).thenReturn(Optional.empty());
        assertEquals("User data checked.", registrationService.registration(request, bindingResult));
        verify(userRepository, times(1)).getUserByEmail(TestConstants.USER_EMAIL, User.class);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void registrationExistedUser_ShouldReturnSuccessMessage() {
        User user = new User();
        user.setEmail(TestConstants.USER_EMAIL);
        user.setUsername(TestConstants.USERNAME);
        user.setFullName(TestConstants.USERNAME);
        user.setBirthday(TestConstants.BIRTHDAY);
        user.setActive(false);
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail(TestConstants.USER_EMAIL);
        request.setUsername(TestConstants.USERNAME);
        request.setBirthday(TestConstants.BIRTHDAY);
        BindingResult bindingResult = mock(BindingResult.class);
        when(userRepository.getUserByEmail(TestConstants.USER_EMAIL, User.class)).thenReturn(Optional.of(user));
        assertEquals("User data checked.", registrationService.registration(request, bindingResult));
        verify(userRepository, times(1)).getUserByEmail(TestConstants.USER_EMAIL, User.class);
        verify(userRepository, times(1)).save(user);
    }
}