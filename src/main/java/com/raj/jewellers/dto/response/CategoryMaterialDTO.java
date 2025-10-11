package com.raj.jewellers.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMaterialDTO {

    private Long categoryId;
    private String categoryName;
    private List<MaterialResponseDTO> materials;
}
