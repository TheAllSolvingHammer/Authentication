package com.tinqinacademy.authentication.api.model.login;

import com.tinqinacademy.authentication.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class LoginInput implements OperationInput {
    private String username;
    private String password;
}
