package com.raj.jewellers.response.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    private ResponseHandler() {
    }

    public static ResponseEntity<Object> generateResponse(
            final String message, final HttpStatus status,
            final String error, final Object responseObj) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);
        map.put("error", error);
        return new ResponseEntity<>(map, status);
    }
    
}
