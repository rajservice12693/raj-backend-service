package com.raj.jewellers.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raj.jewellers.entity.JewelleryItemEntity;

@Repository
public interface JewelleryItemRepository
        extends JpaRepository<JewelleryItemEntity, Long> {

}
