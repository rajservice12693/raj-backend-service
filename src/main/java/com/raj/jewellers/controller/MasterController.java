package com.raj.jewellers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.jewellers.constants.Constants;
import com.raj.jewellers.dto.CategoryDTO;
import com.raj.jewellers.dto.MappingCategoryMaterialDTO;
import com.raj.jewellers.dto.MaterialDTO;
import com.raj.jewellers.exception.CustomException;
import com.raj.jewellers.response.handler.ResponseHandler;
import com.raj.jewellers.service.MasterJewellersService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@CrossOrigin()
@Slf4j
public class MasterController {

    @Autowired
    private MasterJewellersService masterJewellersService;

    @GetMapping("/categories")
    public ResponseEntity<Object> fetchAllCategory() {
        try {
            return ResponseHandler.generateResponse(
                    "Data Fetched successfully !!", HttpStatus.OK,
                    Constants.ERROR_FALSE,
                    masterJewellersService.getAllCategories());

        } catch (Exception e) {
            log.error("Exception while getting categories: {}",
                    e.getMessage());
            return ResponseHandler.generateResponse(
                    "Exception while getting categories",
                    HttpStatus.BAD_REQUEST, Constants.ERROR_TRUE, null);
        }
    }

    @PostMapping("/addcategory")
    public ResponseEntity<Object>
            createCategories(@Valid @RequestBody CategoryDTO dto) {
        try {
            return ResponseHandler.generateResponse(
                    "Data created successfully !!", HttpStatus.CREATED,
                    Constants.ERROR_FALSE,
                    masterJewellersService.saveCategory(dto));
        } catch (CustomException e) {
            return ResponseHandler.generateResponse(e.getMessage(),
                    e.getErrorCode().getHttpStatus(), Constants.ERROR_TRUE,
                    null);
        } catch (Exception e) {
            log.error("Add category error occurred: {}", e.getMessage());
            return ResponseHandler.generateResponse(
                    "Error occurred while saving category.",
                    HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_TRUE,
                    null);
        }

    }

    @GetMapping("/materials")
    public ResponseEntity<Object> fetchMaterials() {
        try {
            return ResponseHandler.generateResponse(
                    "Data Fetched successfully !!", HttpStatus.OK,
                    Constants.ERROR_FALSE,
                    masterJewellersService.getAllMaterials());

        } catch (Exception e) {
            log.error("Exception while getting materials: {}",
                    e.getMessage());
            return ResponseHandler.generateResponse(
                    "Exception while getting materials",
                    HttpStatus.BAD_REQUEST, Constants.ERROR_TRUE, null);
        }
    }

    @PostMapping("/addMaterialsMapping")
    public ResponseEntity<Object> createMaterialMapping(
            @Valid @RequestBody final MappingCategoryMaterialDTO dto) {
        try {
            masterJewellersService.saveMaterialMapping(dto);
            return ResponseHandler.generateResponse(
                    "Data created successfully !!", HttpStatus.CREATED,
                    Constants.ERROR_FALSE, "Data created successfully !!");
        } catch (CustomException e) {
            return ResponseHandler.generateResponse(e.getMessage(),
                    e.getErrorCode().getHttpStatus(), Constants.ERROR_TRUE,
                    null);
        } catch (Exception e) {
            log.error("Exception while saving materials: {}",
                    e.getMessage());
            return ResponseHandler.generateResponse(
                    "Exception while saving materials",
                    HttpStatus.BAD_REQUEST, Constants.ERROR_TRUE, null);
        }

    }

    @PostMapping("/addMaterials")
    public ResponseEntity<Object>
            createMaterial(@Valid @RequestBody final MaterialDTO dto) {
        try {
            return ResponseHandler.generateResponse(
                    "Data created successfully !!", HttpStatus.CREATED,
                    Constants.ERROR_FALSE,
                    masterJewellersService.saveMaterial(dto));
        } catch (CustomException e) {
            return ResponseHandler.generateResponse(e.getMessage(),
                    e.getErrorCode().getHttpStatus(), Constants.ERROR_TRUE,
                    null);
        } catch (Exception e) {
            log.error("Exception while saving materials: {}",
                    e.getMessage());
            return ResponseHandler.generateResponse(
                    "Exception while saving materials",
                    HttpStatus.BAD_REQUEST, Constants.ERROR_TRUE, null);
        }
    }

    @GetMapping("/mappingMaterials")
    public ResponseEntity<Object> fetchMappingMaterials() {
        try {
            return ResponseHandler.generateResponse(
                    "Data Fetched successfully !!", HttpStatus.OK,
                    Constants.ERROR_FALSE, masterJewellersService
                            .fetchAllMappingCategoryMaterial());

        } catch (Exception e) {
            log.error("Exception while getting materials: {}",
                    e.getMessage());
            return ResponseHandler.generateResponse(
                    "Exception while getting materials",
                    HttpStatus.BAD_REQUEST, Constants.ERROR_TRUE, null);
        }
    }
}
