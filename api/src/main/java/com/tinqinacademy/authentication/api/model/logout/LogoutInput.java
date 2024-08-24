package com.tinqinacademy.authentication.api.model.logout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.authentication.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class LogoutInput implements OperationInput {
    @JsonIgnore
    private UUID id;
    @JsonIgnore
    private String jwt;
}
