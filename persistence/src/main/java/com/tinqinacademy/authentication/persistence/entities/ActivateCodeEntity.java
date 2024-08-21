package com.tinqinacademy.authentication.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@Entity
@Table(name="activate_code")
public class ActivateCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "code", nullable = false,unique = true, length = 12)
    private String code;

    @Column(name = "email", nullable = false, unique = true, length = 64)
    private String email;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;


}
