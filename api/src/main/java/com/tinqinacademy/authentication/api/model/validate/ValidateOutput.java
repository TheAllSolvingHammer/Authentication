package com.tinqinacademy.authentication.api.model.validate;

import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class ValidateOutput implements OperationOutput {
    private Boolean isValid;
}
