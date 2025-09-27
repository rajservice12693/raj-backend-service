package com.raj.jewellers.dto.response;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class JewelleryItemResponseDto {

    private Long id;

    private String itemName;

    private Long categoryId;

    private String categoryName;

    private Long materialId;

    private String materialName;

    private String purity;

    private BigDecimal weight;

    private BigDecimal price;

    private String description;

    private List<String> images;

}
