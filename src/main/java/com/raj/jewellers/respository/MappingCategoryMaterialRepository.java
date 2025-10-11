package com.raj.jewellers.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raj.jewellers.entity.MappingCategoryMaterialEntity;
import com.raj.jewellers.entity.key.MappingCategoryMaterialId;

@Repository
public interface MappingCategoryMaterialRepository extends
        JpaRepository<MappingCategoryMaterialEntity, MappingCategoryMaterialId> {

}
