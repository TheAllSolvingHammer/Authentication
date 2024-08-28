package com.tinqinacademy.authentication.api.model.change;

import com.tinqinacademy.authentication.api.base.OperationInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class ChangePasswordInput implements OperationInput {
    @NotNull(message = "Old password can not be null")
    private String oldPassword;
    @NotNull(message = "New password can not be null")
    private String newPassword;
    @Email(message = "Input is not email")
    private String email;
}
