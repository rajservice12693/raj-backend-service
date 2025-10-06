package com.raj.jewellers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.raj.jewellers.constants.Constants;
import com.raj.jewellers.dto.JewelleryItemRequestDTO;
import com.raj.jewellers.dto.LoginRequestDto;
import com.raj.jewellers.exception.CustomException;
import com.raj.jewellers.response.handler.ResponseHandler;
import com.raj.jewellers.service.ApiService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@Slf4j
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/")
    public ResponseEntity<Object> welcome() {
        return ResponseHandler.generateResponse(
                "Welcome to Raj Jewellers API", HttpStatus.OK,
                Constants.ERROR_FALSE, null);
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object>
            login(@Valid @RequestBody final LoginRequestDto dto) {
        try {
            return ResponseHandler.generateResponse(
                    "Login successfully !!", HttpStatus.OK,
                    Constants.ERROR_FALSE, apiService.loginUser(dto));
        } catch (CustomException e) {
            return ResponseHandler.generateResponse(e.getMessage(),
                    e.getErrorCode().getHttpStatus(), Constants.ERROR_TRUE,
                    null);
        } catch (Exception e) {
            log.error("Login error occurred: {}", e.getMessage());
            return ResponseHandler.generateResponse(
                    "Error occurred while checking login details.",
                    HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_TRUE,
                    null);
        }
    }

    @PostMapping(value = "/saveItems",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> saveJewelleryItems(
            @Valid
            @RequestPart("payload") final JewelleryItemRequestDTO dto,
            @RequestPart("images") MultipartFile[] images) {
        try {
            log.info("payload: {}", dto);
            return ResponseHandler.generateResponse(
                    "Item saved successfully !!", HttpStatus.CREATED,
                    Constants.ERROR_FALSE,
                    apiService.saveItems(dto, images));
        } catch (CustomException e) {
            return ResponseHandler.generateResponse(e.getMessage(),
                    e.getErrorCode().getHttpStatus(), Constants.ERROR_TRUE,
                    null);
        } catch (Exception e) {
            log.error("Item save error occurred: {}", e.getMessage());
            return ResponseHandler.generateResponse(
                    "Error occurred while checking saving item.",
                    HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_TRUE,
                    null);
        }
    }

    @GetMapping("/items")
    public ResponseEntity<Object> fetchAllItems() {
        try {
            return ResponseHandler.generateResponse(
                    "Data Fetched successfully !!", HttpStatus.OK,
                    Constants.ERROR_FALSE, apiService.fetchAllItems());

        } catch (CustomException e) {
            return ResponseHandler.generateResponse(e.getMessage(),
                    e.getErrorCode().getHttpStatus(), Constants.ERROR_TRUE,
                    null);
        } catch (Exception e) {
            log.error("Exception while getting items: {}", e.getMessage());
            return ResponseHandler.generateResponse(
                    "Exception while getting items.",
                    HttpStatus.BAD_REQUEST, Constants.ERROR_TRUE, null);
        }
    }

    @GetMapping("/dashboardCount")
    public ResponseEntity<Object> dashboardCount() {
        try {
            return ResponseHandler.generateResponse(
                    "Data Fetched successfully !!", HttpStatus.OK,
                    Constants.ERROR_FALSE,
                    apiService.dashBoradItemCount());

        } catch (CustomException e) {
            return ResponseHandler.generateResponse(e.getMessage(),
                    e.getErrorCode().getHttpStatus(), Constants.ERROR_TRUE,
                    null);
        } catch (Exception e) {
            log.error("Exception while getting count: {}", e.getMessage());
            return ResponseHandler.generateResponse(
                    "Exception while getting items count.",
                    HttpStatus.BAD_REQUEST, Constants.ERROR_TRUE, null);
        }
    }

    @DeleteMapping("/item")
    public ResponseEntity<Object>
            deleteItem(@RequestParam("itemId") Long itemId) {
        try {
            return ResponseHandler.generateResponse(
                    "Data deleted successfully !!", HttpStatus.OK,
                    Constants.ERROR_FALSE,
                    apiService.deleteItemById(itemId));

        } catch (CustomException e) {
            return ResponseHandler.generateResponse(e.getMessage(),
                    e.getErrorCode().getHttpStatus(), Constants.ERROR_TRUE,
                    null);
        } catch (Exception e) {
            log.error("Exception while deleting items: {}",
                    e.getMessage());
            return ResponseHandler.generateResponse(
                    "Exception while deleting items.",
                    HttpStatus.BAD_REQUEST, Constants.ERROR_TRUE, null);
        }
    }
}
