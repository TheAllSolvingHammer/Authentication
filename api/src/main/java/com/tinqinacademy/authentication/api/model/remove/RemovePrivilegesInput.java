package com.tinqinacademy.authentication.api.model.remove;

import com.tinqinacademy.authentication.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class RemovePrivilegesInput implements OperationInput {
    private UUID id;
}
