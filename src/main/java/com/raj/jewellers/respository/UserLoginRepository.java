package com.raj.jewellers.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raj.jewellers.entity.UserLoginEntity;

@Repository
public interface UserLoginRepository
        extends JpaRepository<UserLoginEntity, Integer> {

    Optional<UserLoginEntity> findByLoginId(String loginId);
}
