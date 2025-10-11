package com.raj.jewellers.service.impl;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raj.jewellers.dto.CategoryDTO;
import com.raj.jewellers.dto.MappingCategoryMaterialDTO;
import com.raj.jewellers.dto.MaterialDTO;
import com.raj.jewellers.dto.response.CategoryMaterialDTO;
import com.raj.jewellers.dto.response.MaterialResponseDTO;
import com.raj.jewellers.entity.MappingCategoryMaterialEntity;
import com.raj.jewellers.entity.MasterCategoryEntity;
import com.raj.jewellers.entity.MasterMaterialEntity;
import com.raj.jewellers.entity.key.MappingCategoryMaterialId;
import com.raj.jewellers.enums.HttpStatusEnum;
import com.raj.jewellers.exception.CustomException;
import com.raj.jewellers.respository.MappingCategoryMaterialRepository;
import com.raj.jewellers.respository.MasterCategoryRepository;
import com.raj.jewellers.respository.MasterMaterialRepository;
import com.raj.jewellers.service.MasterJewellersService;
import com.raj.jewellers.utility.Utility;

@Service
public class MasterJewellersServiceImpl implements MasterJewellersService {

    @Autowired
    private MasterCategoryRepository categoryRepository;

    @Autowired
    private MasterMaterialRepository materialsRepository;

    @Autowired
    private MappingCategoryMaterialRepository mappingCategoryMaterialRepository;

    @Override
    public List<MasterCategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<MasterMaterialEntity> getAllMaterials() {
        return materialsRepository.findAll();
    }

    @Override
    public MasterCategoryEntity saveCategory(CategoryDTO dto) {
        String materialNameWithTitleCase =
                Utility.toTitleCase(dto.getCategoryName());
        categoryRepository.findByCategoryName(materialNameWithTitleCase)
                .ifPresent(cate -> {
                    throw new CustomException(HttpStatusEnum.BAD_REQUEST,
                            "Category '" + cate.getCategoryName()
                                    + "' is already exist.");
                });
        MasterCategoryEntity category = new MasterCategoryEntity();
        category.setCategoryName(materialNameWithTitleCase);
        return categoryRepository.save(category);
    }

    @Override
    public void saveMaterialMapping(MappingCategoryMaterialDTO dto) {

        MappingCategoryMaterialId mappingBuilder =
                MappingCategoryMaterialId.builder()
                        .categoryId(dto.getCategoryId())
                        .materialId(dto.getMaterialId()).build();

        if (mappingCategoryMaterialRepository.findById(mappingBuilder)
                .isPresent()) {
            throw new CustomException(HttpStatusEnum.BAD_REQUEST,
                    "Category and meterial is already exists.");
        }

        MasterCategoryEntity category =
                categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() -> new CustomException(
                                HttpStatusEnum.BAD_REQUEST,
                                "Invalid category ID"));

        MasterMaterialEntity material =
                materialsRepository.findById(dto.getMaterialId())
                        .orElseThrow(() -> new CustomException(
                                HttpStatusEnum.BAD_REQUEST,
                                "Invalid material ID"));
        MappingCategoryMaterialEntity objectToSave =
                new MappingCategoryMaterialEntity();
        objectToSave.setId(mappingBuilder);
        objectToSave.setCategory(category);
        objectToSave.setMaterial(material);
        mappingCategoryMaterialRepository.save(objectToSave);
    }

    @Override
    public MasterMaterialEntity saveMaterial(MaterialDTO dto) {
        String materialNameWithTitleCase =
                Utility.toTitleCase(dto.getMaterialName());
        if (materialsRepository
                .findByMaterialName(materialNameWithTitleCase)
                .isPresent()) {
            throw new CustomException(HttpStatusEnum.BAD_REQUEST,
                    String.format("Material name %s already exists.",
                            materialNameWithTitleCase));
        }

        MasterMaterialEntity entityForSave = new MasterMaterialEntity();
        entityForSave.setMaterialName(materialNameWithTitleCase);
        return materialsRepository.save(entityForSave);
    }

    @Override
    public List<CategoryMaterialDTO> fetchAllMappingCategoryMaterial() {

        List<MappingCategoryMaterialEntity> list =
                mappingCategoryMaterialRepository.findAll();

        var grouped = list.stream()
                .collect(Collectors.groupingBy(
                        mapping -> new AbstractMap.SimpleEntry<>(
                                mapping.getCategory().getCategoryId(),
                                mapping.getCategory().getCategoryName())));

        return grouped.entrySet().stream().map(entry -> {
            var categoryId = entry.getKey().getKey();
            var categoryName = entry.getKey().getValue();
            var materials = entry.getValue().stream()
                    .map(mapping -> new MaterialResponseDTO(
                            mapping.getMaterial().getMaterialId(),
                            mapping.getMaterial().getMaterialName()))
                    .toList();
            return new CategoryMaterialDTO(categoryId, categoryName,
                    materials);
        }).toList();

    }

}
