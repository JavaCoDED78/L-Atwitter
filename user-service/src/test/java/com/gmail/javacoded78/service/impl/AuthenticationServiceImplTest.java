package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.amqp.AmqpProducer;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.model.User;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.security.JwtProvider;
import com.gmail.javacoded78.service.AuthenticationService;
import com.gmail.javacoded78.service.util.UserServiceHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class AuthenticationServiceImplTest extends AbstractAuthTest {

    private final AuthenticationService authenticationService;

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
    public void getAuthenticatedUser_ShouldReturnAuthenticatedUser() {
        User user = new User();
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        assertEquals(user, authenticationService.getAuthenticatedUser());
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void getAuthenticatedUser_ShouldThrowUserNotFoundException() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> userRepository.findById(any()));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

    }
}
