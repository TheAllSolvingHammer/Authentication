package com.tinqinacademy.authentication.api.model.login;

import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.*;
import org.springframework.http.HttpHeaders;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class LoginOutput implements OperationOutput {
    private HttpHeaders headers;
}
