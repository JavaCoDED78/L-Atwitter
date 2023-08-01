package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.request.AuthenticationRequest;
import com.gmail.javacoded78.dto.request.CurrentPasswordResetRequest;
import com.gmail.javacoded78.dto.request.EndRegistrationRequest;
import com.gmail.javacoded78.dto.request.PasswordResetRequest;
import com.gmail.javacoded78.dto.request.ProcessEmailRequest;
import com.gmail.javacoded78.dto.request.RegistrationRequest;
import com.gmail.javacoded78.dto.response.AuthUserResponse;
import com.gmail.javacoded78.dto.response.AuthenticationResponse;
import com.gmail.javacoded78.mapper.AuthenticationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.gmail.javacoded78.controller.PathConstants.UI_V1_AUTH;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_AUTH)
public class AuthenticationController {

    private final AuthenticationMapper authenticationMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.login(request, bindingResult));
    }

    @PostMapping("/registration/check")
    public ResponseEntity<String> checkEmail(@Valid @RequestBody RegistrationRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.registration(request, bindingResult));
    }

    @PostMapping("/registration/code")
    public ResponseEntity<String> sendRegistrationCode(@Valid @RequestBody ProcessEmailRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.sendRegistrationCode(request.getEmail(), bindingResult));
    }

    @GetMapping("/registration/activate/{code}")
    public ResponseEntity<String> checkRegistrationCode(@PathVariable String code) {
        return ResponseEntity.ok(authenticationMapper.activateUser(code));
    }

    @PostMapping("/registration/confirm")
    public ResponseEntity<AuthenticationResponse> endRegistration(@Valid @RequestBody EndRegistrationRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.endRegistration(request, bindingResult));
    }

    @GetMapping("/user")
    public ResponseEntity<AuthenticationResponse> getUserByToken() {
        return ResponseEntity.ok(authenticationMapper.getUserByToken());
    }

    @PostMapping("/forgot/email")
    public ResponseEntity<String> getExistingEmail(@Valid @RequestBody ProcessEmailRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.getEmail(request.getEmail(), bindingResult));
    }

    @PostMapping("/forgot")
    public ResponseEntity<String> sendPasswordResetCode(@Valid @RequestBody ProcessEmailRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.sendPasswordResetCode(request.getEmail(), bindingResult));
    }

    @GetMapping("/reset/{code}")
    public ResponseEntity<AuthUserResponse> getUserByResetCode(@PathVariable String code) {
        return ResponseEntity.ok(authenticationMapper.getByPasswordResetCode(code));
    }

    @PostMapping("/reset")
    public ResponseEntity<String> passwordReset(@Valid @RequestBody PasswordResetRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.passwordReset(request, bindingResult));
    }

    @PostMapping("/reset/current")
    public ResponseEntity<String> currentPasswordReset(@Valid @RequestBody CurrentPasswordResetRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.currentPasswordReset(request, bindingResult));
    }
}
