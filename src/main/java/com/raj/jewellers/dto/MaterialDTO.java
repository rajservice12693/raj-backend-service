package com.raj.jewellers.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MaterialDTO {

    @NotBlank(message = "Matrial Name is required.")
    public String materialName;

}
