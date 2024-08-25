package com.tinqinacademy.authentication.api.model.register;

import com.tinqinacademy.authentication.api.base.OperationInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class UserRegistrationInput implements OperationInput {
    @NotNull(message = "Username can not be empty")
    private String username;
    @NotNull(message = "Password can not be null")
    private String password;
    @NotNull(message = "Firstname can not be null")
    private String firstName;
    @NotNull(message = "Lastname can not be null")
    private String lastName;
    @Email(message = "Must provide valid email")
    private String email;
    @Past(message = "Birthdate must be in the past")
    private LocalDate birthdate;
}
