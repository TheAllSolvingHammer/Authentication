package com.tinqinacademy.authentication.api.model.confirmation;

import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class UserRegistrationOutput implements OperationOutput {
    private UUID id;
}
