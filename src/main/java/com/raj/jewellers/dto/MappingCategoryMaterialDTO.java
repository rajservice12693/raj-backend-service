package com.raj.jewellers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MappingCategoryMaterialDTO {

    @NotNull(message = "Material ID is required")
    private Long materialId;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
