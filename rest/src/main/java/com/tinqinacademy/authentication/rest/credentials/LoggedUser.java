package com.tinqinacademy.authentication.rest.credentials;

import com.tinqinacademy.authentication.persistence.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter

@Setter
@Component
public class LoggedUser {
    private UserEntity loggedUser;
    private String token;
}