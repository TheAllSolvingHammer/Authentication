package com.tinqinacademy.authentication.persistence.repositories;

import com.tinqinacademy.authentication.persistence.entities.BlackListedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlackListedRepository extends JpaRepository<BlackListedEntity, UUID> {
}
