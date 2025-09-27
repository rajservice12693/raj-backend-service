package com.raj.jewellers.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raj.jewellers.dto.CategoryDTO;
import com.raj.jewellers.dto.MaterialDTO;
import com.raj.jewellers.entity.MasterCategoryEntity;
import com.raj.jewellers.entity.MasterMaterialEntity;
import com.raj.jewellers.enums.HttpStatusEnum;
import com.raj.jewellers.exception.CustomException;
import com.raj.jewellers.respository.MasterCategoryRepository;
import com.raj.jewellers.respository.MasterMaterialRepository;
import com.raj.jewellers.service.MasterJewellersService;

@Service
public class MasterJewellersServiceImpl implements MasterJewellersService {

    @Autowired
    private MasterCategoryRepository categoryRepository;

    @Autowired
    private MasterMaterialRepository materialsRepository;

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

        categoryRepository
                .findByCategoryNameAllIgnoringCase(dto.getCategoryName())
                .ifPresent(cate -> {
                    throw new CustomException(HttpStatusEnum.BAD_REQUEST,
                            "Category '" + cate.getCategoryName()
                                    + "' is already exist.");
                });
        MasterCategoryEntity category = new MasterCategoryEntity();
        category.setCategoryName(dto.categoryName);
        return categoryRepository.save(category);
    }

    @Override
    public MasterMaterialEntity saveMaterial(MaterialDTO dto) {
        MasterCategoryEntity category =
                categoryRepository.findById(dto.categoryId).orElseThrow(
                        () -> new RuntimeException("Category not found"));

        boolean isExist = category.getMaterials().stream()
                .anyMatch(obj -> dto.materialName
                        .equalsIgnoreCase(obj.getMaterialName()));
        if (isExist) {
            throw new CustomException(HttpStatusEnum.BAD_REQUEST,
                    "Material name '" + dto.materialName
                            + "' already exist for the category '"
                            + category.getCategoryName() + "'");
        }
        MasterMaterialEntity material = new MasterMaterialEntity();
        material.setMaterialName(dto.materialName);
        material.setCategory(category);

        return materialsRepository.save(material);
    }

}
