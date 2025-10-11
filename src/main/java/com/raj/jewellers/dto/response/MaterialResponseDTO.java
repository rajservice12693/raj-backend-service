package com.raj.jewellers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialResponseDTO {
    private Long materialId;
    private String materialName;
}
