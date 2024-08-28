package com.tinqinacademy.authentication.api.model.change;

import com.tinqinacademy.authentication.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class ChangePasswordInput implements OperationInput {
    private String oldPassword;
    private String newPassword;
    private String email;
}
