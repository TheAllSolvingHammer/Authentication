package com.tinqinacademy.authentication.persistence.repositories;

import com.tinqinacademy.authentication.persistence.entities.BlackListedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BlackListedRepository extends JpaRepository<BlackListedEntity, UUID> {
    boolean existsByJwt(String jwt);

    String query= """
            SELECT bc.jwt
            FROM blacklisted_codes bc
            """;
    @Query(value = query,nativeQuery = true)
    List<String> getAllTokens();

}
