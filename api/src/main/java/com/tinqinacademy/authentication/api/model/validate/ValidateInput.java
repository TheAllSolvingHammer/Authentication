package com.tinqinacademy.authentication.api.model.validate;

import com.tinqinacademy.authentication.api.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class ValidateInput implements OperationInput {
    @NotNull(message = "Jwt can not be null")
    private String jwt;
}
