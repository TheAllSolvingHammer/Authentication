package com.tinqinacademy.authentication.api.model.login;

import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class LoginOutput implements OperationOutput {
    private String jwt;
}
