package com.raj.jewellers.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raj.jewellers.entity.MasterMaterialEntity;

@Repository
public interface MasterMaterialRepository
        extends JpaRepository<MasterMaterialEntity, Long> {

    Optional<MasterMaterialEntity> findByMaterialName(String materialName);

}
