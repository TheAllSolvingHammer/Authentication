package com.tinqinacademy.authentication.api.model.recover;

import com.tinqinacademy.authentication.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class PasswordMailRecoverInput implements OperationInput {
    private String email;
    private String code;
    private String newPassword;
}
