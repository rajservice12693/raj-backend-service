package com.raj.jewellers.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.jewellers.constants.Constants;
import com.raj.jewellers.response.handler.ResponseHandler;

@RestController
@CrossOrigin("*")
public class RootController {

    @GetMapping("/")
    public ResponseEntity<Object> welcome() {
        return ResponseHandler.generateResponse(
                "Welcome to Raj Jewellers Backend Service", HttpStatus.OK,
                Constants.ERROR_FALSE, null);
    }
}