package com.raj.jewellers.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MaterialDTO {

    @NotBlank(message = "Matrial Name is required.")
    public String materialName;

    @NotNull(message = "Category Id is required.")
    @Min(value = 1, message = "Category should be greater than 1.")
    public Integer categoryId;
}
