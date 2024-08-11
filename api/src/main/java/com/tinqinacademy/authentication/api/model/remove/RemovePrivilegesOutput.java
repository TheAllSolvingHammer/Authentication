package com.tinqinacademy.authentication.api.model.remove;

import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class RemovePrivilegesOutput implements OperationOutput {
    private String message;
}
