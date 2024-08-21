package com.tinqinacademy.authentication.persistence.entities;

import com.tinqinacademy.authentication.persistence.enums.RoleType;
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
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "birthday",nullable = false)
    private LocalDate birthday;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name="verified",nullable = false)
    private Boolean verified;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="role",nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
