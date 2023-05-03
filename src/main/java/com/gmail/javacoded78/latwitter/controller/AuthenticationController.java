package com.gmail.javacoded78.latwitter.controller;

import com.gmail.javacoded78.latwitter.dto.request.AuthenticationRequest;
import com.gmail.javacoded78.latwitter.dto.request.PasswordResetRequest;
import com.gmail.javacoded78.latwitter.dto.request.RegistrationRequest;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.exception.ApiRequestException;
import com.gmail.javacoded78.latwitter.exception.InputFieldException;
import com.gmail.javacoded78.latwitter.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            return ResponseEntity.ok(userMapper.login(request.getEmail()));
        } catch (AuthenticationException e) {
            throw new ApiRequestException("Incorrect password or email", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody @Valid RegistrationRequest user, BindingResult bindingResult) {
        if (ControllerUtils.isPasswordDifferent(user.getPassword(), user.getPassword2())) {
            throw new ApiRequestException("Passwords do not match.", HttpStatus.BAD_REQUEST);
        }
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        if (!userMapper.registration(user)) {
            throw new ApiRequestException("Email is already used.", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok("User successfully registered.");
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<String> activateEmailCode(@PathVariable String code) {
        if (!userMapper.activateUser(code)) {
            throw new ApiRequestException("Activation code not found.", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok("User successfully activated.");
        }
    }

    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestBody PasswordResetRequest passwordReset) {
        boolean forgotPassword = userMapper.sendPasswordResetCode(passwordReset.getEmail());
        if (!forgotPassword) {
            throw new ApiRequestException("Email not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("Reset password code is send to your E-mail");
    }

    @GetMapping("/reset/{code}")
    public ResponseEntity<UserResponse> getPasswordResetCode(@PathVariable String code) {
        UserResponse user = userMapper.findByPasswordResetCode(code);
        if (user == null) {
            throw new ApiRequestException("Password reset code is invalid!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/reset")
    public ResponseEntity<String> passwordReset(@RequestBody PasswordResetRequest passwordReset) {
        if (ControllerUtils.isPasswordConfirmEmpty(passwordReset.getPassword2())) {
            throw new ApiRequestException("Password confirmation cannot be empty.", HttpStatus.BAD_REQUEST);
        }
        if (ControllerUtils.isPasswordDifferent(passwordReset.getPassword(), passwordReset.getPassword2())) {
            throw new ApiRequestException("Passwords do not match.", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userMapper.passwordReset(passwordReset.getEmail(), passwordReset.getPassword()));
    }
}
