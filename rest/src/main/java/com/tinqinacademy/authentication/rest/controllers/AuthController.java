package com.tinqinacademy.authentication.rest.controllers;

import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.mappings.MappingConstants;
import com.tinqinacademy.authentication.api.model.activate.ActivateInput;
import com.tinqinacademy.authentication.api.model.change.ChangePasswordInput;
import com.tinqinacademy.authentication.api.model.email.EmailRecoveryInput;
import com.tinqinacademy.authentication.api.model.login.LoginInput;
import com.tinqinacademy.authentication.api.model.login.LoginOutput;
import com.tinqinacademy.authentication.api.model.logout.LogoutInput;
import com.tinqinacademy.authentication.api.model.promote.PromotionInput;
import com.tinqinacademy.authentication.api.model.recover.PasswordMailRecoverInput;
import com.tinqinacademy.authentication.api.model.register.UserRegistrationInput;
import com.tinqinacademy.authentication.api.model.remove.RemovePrivilegesInput;
import com.tinqinacademy.authentication.api.model.validate.ValidateInput;
import com.tinqinacademy.authentication.core.processes.*;
import com.tinqinacademy.authentication.rest.credentials.LoggedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController extends BaseController{
    private final RegisterProcessor registerProcessor;
    private final ActivateProcessor activateProcessor;
    private final LoginProcessor loginProcessor;
    private final EmailRecoveryProcessor emailRecoveryProcessor;
    private final DemoteProcessor demoteProcessor;
    private final ChangePasswordProcessor changePasswordProcessor;
    private final RecoverPasswordProcessor recoverPasswordProcessor;
    private final PromotionProcessor promotionProcessor;
    private final LoggedUser loggedUser;
    private final LogoutProcessor logoutProcessor;
    private final ValidateProcessor validateProcessor;

    @PostMapping(MappingConstants.login)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is logged in"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Logs in user")
    public ResponseEntity<?>  loginUser(@RequestBody LoginInput input){
        Either<ErrorsProcessor, LoginOutput> result=loginProcessor.process(input);
        return result.fold(
                error -> ResponseEntity.status(error.getHttpStatus()).body(error),
                success-> ResponseEntity.status(HttpStatus.OK.value()).headers(success.getHeaders()).body("Successfully logged in!")
        );

    }

    @PostMapping(MappingConstants.register)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered a user"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Registers user")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationInput input){
        return handleOperation(registerProcessor.process(input));
    }

    @PostMapping(MappingConstants.recover)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recovered password was sent to email"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Recovers password")
    public ResponseEntity<?> recover(@RequestBody EmailRecoveryInput input){
        return handleOperation(emailRecoveryProcessor.process(input));
    }

    @PostMapping(MappingConstants.confirm)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activates user account"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Activates user account")
    public ResponseEntity<?> activate(@RequestBody ActivateInput input){
       return handleOperation(activateProcessor.process(input));
    }

    @PostMapping(MappingConstants.change)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password was changed successfully"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Changes user password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordInput input){
        return handleOperation(changePasswordProcessor.process(input));
    }

    @PostMapping(MappingConstants.recoverChange)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password was changed successfully"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Recovers user password")
    public ResponseEntity<?> recoverUserPassword(@RequestBody PasswordMailRecoverInput input){
        return handleOperation(recoverPasswordProcessor.process(input));
    }

    @PostMapping(MappingConstants.promote)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is promoted to admin"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Promotes user to admin")
    public ResponseEntity<?> promote(@RequestBody PromotionInput input){
        return handleOperation(promotionProcessor.process(input));
    }

    @PostMapping(MappingConstants.demote)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Demoted admin successfully"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Demotes user account")
    public ResponseEntity<?> demote(@RequestBody RemovePrivilegesInput input){
        return handleOperation(demoteProcessor.process(input));
    }

    @PostMapping(MappingConstants.logout)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged out"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Logs out user")
    public ResponseEntity<?> logoutUser(){
        LogoutInput input = LogoutInput.builder()
                .id(loggedUser.getLoggedUser().getId())
                .jwt(loggedUser.getToken())
                .build();
        return handleOperation(logoutProcessor.process(input));
    }


    @PostMapping(MappingConstants.validate)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully validated"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Validate user token")
    public ResponseEntity<?> validateUser(@RequestBody ValidateInput input){
        return handleOperation(validateProcessor.process(input));
    }

}
