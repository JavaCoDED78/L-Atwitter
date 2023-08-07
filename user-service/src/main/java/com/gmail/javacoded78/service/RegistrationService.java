package com.gmail.javacoded78.service;

import com.gmail.javacoded78.dto.request.RegistrationRequest;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface RegistrationService {

    String registration(RegistrationRequest request, BindingResult bindingResult);

    String sendRegistrationCode(String email, BindingResult bindingResult);

    String checkRegistrationCode(String code);

    Map<String, Object> endRegistration(String email, String password, BindingResult bindingResult);
}
