package com.tinqinacademy.authentication.api.model.change;

import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class ChangePasswordOutput implements OperationOutput {
    private String message;
}
