package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.request.AuthenticationRequest;
import com.gmail.javacoded78.dto.request.CurrentPasswordResetRequest;
import com.gmail.javacoded78.dto.request.PasswordResetRequest;
import com.gmail.javacoded78.dto.request.ProcessEmailRequest;
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

import static com.gmail.javacoded78.constants.PathConstants.FORGOT;
import static com.gmail.javacoded78.constants.PathConstants.FORGOT_EMAIL;
import static com.gmail.javacoded78.constants.PathConstants.LOGIN;
import static com.gmail.javacoded78.constants.PathConstants.RESET;
import static com.gmail.javacoded78.constants.PathConstants.RESET_CODE;
import static com.gmail.javacoded78.constants.PathConstants.RESET_CURRENT;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_AUTH;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_AUTH)
public class AuthenticationController {

    private final AuthenticationMapper authenticationMapper;

    @PostMapping(LOGIN)
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.login(request, bindingResult));
    }

    @PostMapping(FORGOT_EMAIL)
    public ResponseEntity<String> getExistingEmail(@Valid @RequestBody ProcessEmailRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.getExistingEmail(request.getEmail(), bindingResult));
    }

    @PostMapping(FORGOT)
    public ResponseEntity<String> sendPasswordResetCode(@Valid @RequestBody ProcessEmailRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.sendPasswordResetCode(request.getEmail(), bindingResult));
    }

    @GetMapping(RESET_CODE)
    public ResponseEntity<AuthUserResponse> getUserByPasswordResetCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(authenticationMapper.getUserByPasswordResetCode(code));
    }

    @PostMapping(RESET)
    public ResponseEntity<String> passwordReset(@Valid @RequestBody PasswordResetRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.passwordReset(request, bindingResult));
    }

    @PostMapping(RESET_CURRENT)
    public ResponseEntity<String> currentPasswordReset(@Valid @RequestBody CurrentPasswordResetRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.currentPasswordReset(request, bindingResult));
    }
}
