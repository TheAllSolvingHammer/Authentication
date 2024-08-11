package com.tinqinacademy.authentication.api.model.recovery;

import com.tinqinacademy.authentication.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class EmailRecoveryInput implements OperationInput {
    private String email;
}
