package com.raj.jewellers.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raj.jewellers.entity.MasterCategoryEntity;

@Repository
public interface MasterCategoryRepository
        extends JpaRepository<MasterCategoryEntity, Integer> {

    Optional<MasterCategoryEntity>
            findByCategoryNameAllIgnoringCase(String categoryName);

}
