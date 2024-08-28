package com.tinqinacademy.authentication.api.model.login;

import com.tinqinacademy.authentication.api.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class LoginInput implements OperationInput {
    @NotNull(message = "Username can not be null")
    private String username;
    @NotNull(message = "Password can not be null")
    private String password;
}
