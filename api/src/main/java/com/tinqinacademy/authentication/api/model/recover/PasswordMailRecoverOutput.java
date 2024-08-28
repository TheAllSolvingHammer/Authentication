package com.tinqinacademy.authentication.api.model.recover;

import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class PasswordMailRecoverOutput implements OperationOutput {
    private String message;
}
