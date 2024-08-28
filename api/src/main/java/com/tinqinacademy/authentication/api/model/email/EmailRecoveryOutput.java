package com.tinqinacademy.authentication.api.model.email;

import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class EmailRecoveryOutput implements OperationOutput {
    private String message;
}
