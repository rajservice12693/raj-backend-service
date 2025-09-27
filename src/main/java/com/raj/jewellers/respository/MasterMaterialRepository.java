package com.raj.jewellers.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raj.jewellers.entity.MasterMaterialEntity;

@Repository
public interface MasterMaterialRepository
        extends JpaRepository<MasterMaterialEntity, Integer> {

}
