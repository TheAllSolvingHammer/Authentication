package com.tinqinacademy.authentication.api.model.confirmation;

import com.tinqinacademy.authentication.api.base.OperationInput;
import lombok.*;

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
    private String email;
}
