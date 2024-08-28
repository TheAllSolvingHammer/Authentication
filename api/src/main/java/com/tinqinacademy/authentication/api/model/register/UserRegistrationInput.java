package com.tinqinacademy.authentication.api.model.register;

import com.tinqinacademy.authentication.api.base.OperationInput;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class UserRegistrationInput implements OperationInput {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private LocalDate birthdate;
}
