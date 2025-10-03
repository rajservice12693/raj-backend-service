package com.raj.jewellers.exception.handler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.raj.jewellers.constants.Constants;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, Object> body = new LinkedHashMap<>();

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(error -> error.getField() + ": "
                        + error.getDefaultMessage())
                .toList();

        body.put("message", String.join(" \n ", errors));
        body.put("error", Constants.ERROR_TRUE);
        body.put("status", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("data", null);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
