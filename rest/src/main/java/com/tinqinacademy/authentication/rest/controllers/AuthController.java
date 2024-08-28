package com.tinqinacademy.authentication.rest.controllers;

import com.tinqinacademy.authentication.api.model.activate.ActivateInput;
import com.tinqinacademy.authentication.api.model.change.ChangePasswordInput;
import com.tinqinacademy.authentication.api.model.promote.PromotionInput;
import com.tinqinacademy.authentication.api.model.register.UserRegistrationInput;
import com.tinqinacademy.authentication.api.model.login.LoginInput;
import com.tinqinacademy.authentication.api.model.email.EmailRecoveryInput;
import com.tinqinacademy.authentication.api.model.remove.RemovePrivilegesInput;
import com.tinqinacademy.authentication.core.processes.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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

    @PostMapping(MappingConstants.login)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is logged in"),
            @ApiResponse(responseCode = "400", description = "Wrong syntax"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Logs in user")
    public ResponseEntity<?>  loginUser(@RequestBody LoginInput input){
        return handleOperation(loginProcessor.process(input));
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
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden request"),
            @ApiResponse(responseCode = "404", description = "Server was not found")
    })
    @Operation(summary = "Demotes user account")
    public ResponseEntity<?> demote(@RequestBody RemovePrivilegesInput input){
        return handleOperation(demoteProcessor.process(input));
    }

}
