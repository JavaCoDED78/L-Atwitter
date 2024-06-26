package com.gmail.javacoded78.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class InputFieldExceptionHandler {

    @ExceptionHandler(InputFieldException.class)
    public ResponseEntity<Map<String, String>> handleInputFieldException(InputFieldException exception) {
        InputFieldException inputFieldException = (exception.getBindingResult() != null)
                ? new InputFieldException(exception.getBindingResult())
                : new InputFieldException(exception.getStatus(), exception.getErrorsMap());

        return ResponseEntity.status(inputFieldException.getStatus()).body(inputFieldException.getErrorsMap());
    }
}
