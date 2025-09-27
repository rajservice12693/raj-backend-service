package com.raj.jewellers.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raj.jewellers.dto.CategoryDTO;
import com.raj.jewellers.dto.MaterialDTO;
import com.raj.jewellers.entity.MasterCategoryEntity;
import com.raj.jewellers.entity.MasterMaterialEntity;

@Service
public interface MasterJewellersService {

    List<MasterCategoryEntity> getAllCategories();

    List<MasterMaterialEntity> getAllMaterials();

    MasterCategoryEntity saveCategory(CategoryDTO dto);

    MasterMaterialEntity saveMaterial(MaterialDTO dto);
}
