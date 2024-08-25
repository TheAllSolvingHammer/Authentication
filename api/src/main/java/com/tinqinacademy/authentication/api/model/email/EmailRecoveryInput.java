package com.tinqinacademy.authentication.api.model.email;

import com.tinqinacademy.authentication.api.base.OperationInput;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class EmailRecoveryInput implements OperationInput {
    @Email(message = "Given input is not email")
    private String email;
}
