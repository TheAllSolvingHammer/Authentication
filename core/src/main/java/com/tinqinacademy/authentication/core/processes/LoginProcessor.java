package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.EntityException;
import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.exceptions.RegistrationLoginException;
import com.tinqinacademy.authentication.api.model.login.LoginInput;
import com.tinqinacademy.authentication.api.model.login.LoginOperation;
import com.tinqinacademy.authentication.api.model.login.LoginOutput;
import com.tinqinacademy.authentication.core.families.casehandlers.InputQueryEntityExceptionCase;
import com.tinqinacademy.authentication.core.util.JwtService;
import com.tinqinacademy.authentication.persistence.entities.UserEntity;
import com.tinqinacademy.authentication.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoginProcessor extends BaseProcessor implements LoginOperation {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletResponse response;
    public LoginProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper, UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, HttpServletResponse response) {
        super(validator, conversionService, errorMapper);
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.response = response;
    }

    @Override
    public Either<ErrorsProcessor, LoginOutput> process(LoginInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
        log.info("Started log in operation {}",input);

        UserEntity user = getUser(input.getUsername());
        checkUserConfirm(user);
        checkPasswordMatch(input.getPassword(),user);
        Map<String, String> claims = new HashMap<>();
        claims.put("username", input.getUsername());
        claims.put("role", user.getRoleType().name());

        String token = jwtService.generateToken(claims);
        response.setHeader("Authorization", "Bearer " + token);
        LoginOutput output = LoginOutput.builder()
                .message("Successfully logged in, token: "+token)
                .build();
        log.info("End log in operation {}",output);
        return output;
        }).toEither()
                .mapLeft(InputQueryEntityExceptionCase::handleThrowable));
    }
    private UserEntity getUser(String username){
        return userRepository.findByUsername(username).orElseThrow(()->new EntityException("Entity not found"));
    }
    private void checkUserConfirm(UserEntity user){
        if(user.getVerified().equals(Boolean.FALSE)){
            throw new RegistrationLoginException("User is not verified");
        }
    }
    private void checkPasswordMatch(String password,UserEntity user){
        if(!passwordEncoder.matches(password,user.getPassword())){
            System.out.println(passwordEncoder.encode(password));
            System.out.println(user.getPassword());
            throw new RegistrationLoginException("Password is incorrect!");
        }
    }
}
