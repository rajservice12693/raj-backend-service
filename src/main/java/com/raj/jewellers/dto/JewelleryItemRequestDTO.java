package com.raj.jewellers.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JewelleryItemRequestDTO {

    @NotBlank(message = "Item name is required")
    @Size(max = 100, message = "Item name must be at most 100 characters")
    private String itemName;

    @NotNull(message = "Category ID is required")
    private Integer categoryId;

    @NotNull(message = "Material ID is required")
    private Integer materialId;

    @Size(max = 20, message = "Purity must be at most 20 characters")
    private String purity;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.01", inclusive = true,
            message = "Weight must be greater than 0")
    private BigDecimal weight;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", inclusive = true,
            message = "Price must be greater than 0")
    private BigDecimal price;

    @Min(value = 0, message = "Stock quantity must be at least 0")
    private Integer stockQuantity = 0;

    private String description;

    private String imagePaths; // e.g. "img1.jpg,img2.jpg"

    private Boolean stockAvailable = true;
}
