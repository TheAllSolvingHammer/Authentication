package com.tinqinacademy.authentication.api.model.activate;

import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class ActivateOutput implements OperationOutput {
    private String message;
}
