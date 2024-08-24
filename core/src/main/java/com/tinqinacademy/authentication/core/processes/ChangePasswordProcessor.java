package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.EntityException;
import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.exceptions.InputException;
import com.tinqinacademy.authentication.api.model.change.ChangePasswordInput;
import com.tinqinacademy.authentication.api.model.change.ChangePasswordOperation;
import com.tinqinacademy.authentication.api.model.change.ChangePasswordOutput;
import com.tinqinacademy.authentication.core.families.casehandlers.JwtEntityExceptionCase;
import com.tinqinacademy.authentication.persistence.entities.UserEntity;
import com.tinqinacademy.authentication.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChangePasswordProcessor extends BaseProcessor implements ChangePasswordOperation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public ChangePasswordProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(validator, conversionService, errorMapper);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Either<ErrorsProcessor, ChangePasswordOutput> process(ChangePasswordInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(() -> {
                    log.info("Start change password operation{}", input);
                    UserEntity user = getUser(input.getEmail());
                    checkPassword(input, user);
                    user.setPassword(passwordEncoder.encode(input.getNewPassword()));
                    userRepository.save(user);
                    ChangePasswordOutput output = ChangePasswordOutput.builder()
                            .message("Successfully change password")
                            .build();
                    log.info("End change password operation{}",output);
                    return output;

                }).toEither()
                .mapLeft(JwtEntityExceptionCase::handleThrowable));
    }

    private void checkPassword(ChangePasswordInput input, UserEntity user) {
        if(!passwordEncoder.matches(input.getOldPassword(), user.getPassword())){
            throw new InputException("Password doesnt match!");
        }
    }


    private UserEntity getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new EntityException("No entity was found"));
    }


}
