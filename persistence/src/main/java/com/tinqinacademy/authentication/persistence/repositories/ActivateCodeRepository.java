package com.tinqinacademy.authentication.persistence.repositories;

import com.tinqinacademy.authentication.persistence.entities.ActivateCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ActivateCodeRepository extends JpaRepository<ActivateCodeEntity, UUID> {
    boolean existsByCode(String code);
    boolean existsByEmail(String email);
    Optional<ActivateCodeEntity> findByCode(String code);
}
