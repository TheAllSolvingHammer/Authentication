package com.tinqinacademy.authentication.api.model.activate;

import com.tinqinacademy.authentication.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class ActivateInput implements OperationInput {
    private String confirmationCode;
}
