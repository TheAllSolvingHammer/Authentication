package com.tinqinacademy.authentication.api.model.logout;

import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class LogoutOutput implements OperationOutput {
    private String message;
}
