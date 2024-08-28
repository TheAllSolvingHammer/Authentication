package com.tinqinacademy.authentication.api.model.activate;

import com.tinqinacademy.authentication.api.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class ActivateInput implements OperationInput {
    @NotNull(message = "Confirmation code can not be null")
    private String confirmationCode;
}
