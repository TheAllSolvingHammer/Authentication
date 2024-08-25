package com.tinqinacademy.authentication.api.model.recover;

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
public class PasswordMailRecoverInput implements OperationInput {
    @Email(message = "Not a valid email")
    private String email;
    @NotNull(message = "Code from the email is empty")
    private String code;
    @NotNull(message = "New password can not be empty")
    private String newPassword;
}
