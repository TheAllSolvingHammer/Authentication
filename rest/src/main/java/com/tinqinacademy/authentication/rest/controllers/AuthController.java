package com.tinqinacademy.authentication.rest.controllers;

import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.mappings.MappingConstants;
import com.tinqinacademy.authentication.api.model.activate.ActivateInput;
import com.tinqinacademy.authentication.api.model.change.ChangePasswordInput;
import com.tinqinacademy.authentication.api.model.login.LoginOutput;
import com.tinqinacademy.authentication.api.model.promote.PromotionInput;
import com.tinqinacademy.authentication.api.model.register.UserRegistrationInput;
import com.tinqinacademy.authentication.api.model.login.LoginInput;
import com.tinqinacademy.authentication.api.model.recovery.EmailRecoveryInput;
import com.tinqinacademy.authentication.core.processes.ActivateProcessor;
import com.tinqinacademy.authentication.core.processes.EmailRecoveryProcessor;
import com.tinqinacademy.authentication.core.processes.LoginProcessor;
import com.tinqinacademy.authentication.core.processes.RegisterProcessor;
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
        return ResponseEntity.ok("d2");
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
        return ResponseEntity.ok("d2");
    }

    @PostMapping(MappingConstants.demote)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Demoted admin successfully"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Demotes user account")
    public ResponseEntity<?> demote(@RequestBody ActivateInput input){
        return ResponseEntity.ok("d2");
    }

}
