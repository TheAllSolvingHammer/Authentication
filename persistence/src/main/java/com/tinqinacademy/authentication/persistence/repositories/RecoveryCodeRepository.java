package com.tinqinacademy.authentication.persistence.repositories;

import com.tinqinacademy.authentication.persistence.entities.RecoveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecoveryCodeRepository extends JpaRepository<RecoveryEntity, UUID> {
}
