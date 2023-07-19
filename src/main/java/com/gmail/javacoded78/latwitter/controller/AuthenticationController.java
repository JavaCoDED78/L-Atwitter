package com.gmail.javacoded78.latwitter.controller;

import com.gmail.javacoded78.latwitter.dto.request.AuthenticationRequest;
import com.gmail.javacoded78.latwitter.dto.request.PasswordResetRequest;
import com.gmail.javacoded78.latwitter.dto.request.RegistrationRequest;
import com.gmail.javacoded78.latwitter.dto.response.AuthenticationResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.dto.response.projection.AuthUserProjectionResponse;
import com.gmail.javacoded78.latwitter.dto.response.projection.AuthenticationProjectionResponse;
import com.gmail.javacoded78.latwitter.mapper.AuthenticationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationMapper authenticationMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationProjectionResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationMapper.login(authenticationRequest));
    }

    @PostMapping("/registration/check")
    public ResponseEntity<String> checkEmail(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(authenticationMapper.registration(registrationRequest));
    }

    @PostMapping("/registration/code")
    public ResponseEntity<String> sendRegistrationCode(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(authenticationMapper.sendRegistrationCode(registrationRequest.getEmail()));
    }

    @GetMapping("/registration/activate/{code}")
    public ResponseEntity<String> checkRegistrationCode(@PathVariable String code) {
        return ResponseEntity.ok(authenticationMapper.activateUser(code));
    }

    @PostMapping("/registration/confirm")
    public ResponseEntity<AuthenticationProjectionResponse> endRegistration(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(authenticationMapper.endRegistration(registrationRequest));
    }

    @GetMapping("/user")
    public ResponseEntity<AuthenticationProjectionResponse> getUserByToken() {
        return ResponseEntity.ok(authenticationMapper.getUserByToken());
    }

    @PostMapping("/forgot/email")
    public ResponseEntity<String> findExistingEmail(@RequestBody PasswordResetRequest passwordReset) {
        return ResponseEntity.ok(authenticationMapper.findEmail(passwordReset.getEmail()));
    }

    @PostMapping("/forgot")
    public ResponseEntity<String> sendPasswordResetCode(@RequestBody PasswordResetRequest passwordReset) {
        return ResponseEntity.ok(authenticationMapper.sendPasswordResetCode(passwordReset.getEmail()));
    }

    @GetMapping("/reset/{code}")
    public ResponseEntity<AuthUserProjectionResponse> getUserByResetCode(@PathVariable String code) {
        return ResponseEntity.ok(authenticationMapper.findByPasswordResetCode(code));
    }

    @PostMapping("/reset")
    public ResponseEntity<String> passwordReset(@RequestBody PasswordResetRequest passwordReset) {
        return ResponseEntity.ok(authenticationMapper.passwordReset(passwordReset));
    }
}
