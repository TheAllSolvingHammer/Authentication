package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.EntityException;
import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.model.recover.PasswordMailRecoverInput;
import com.tinqinacademy.authentication.api.model.recover.PasswordMailRecoverOperation;
import com.tinqinacademy.authentication.api.model.recover.PasswordMailRecoverOutput;
import com.tinqinacademy.authentication.core.families.casehandlers.JwtEntityExceptionCase;
import com.tinqinacademy.authentication.persistence.entities.RecoveryEntity;
import com.tinqinacademy.authentication.persistence.entities.UserEntity;
import com.tinqinacademy.authentication.persistence.repositories.RecoveryCodeRepository;
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
public class RecoverPasswordProcessor extends BaseProcessor implements PasswordMailRecoverOperation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RecoveryCodeRepository recoveryCodeRepository;

    @Autowired
    public RecoverPasswordProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, RecoveryCodeRepository recoveryCodeRepository) {
        super(validator, conversionService, errorMapper);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.recoveryCodeRepository = recoveryCodeRepository;
    }

    @Override
    public Either<ErrorsProcessor, PasswordMailRecoverOutput> process(PasswordMailRecoverInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(() -> {
                    log.info("Start password recovery operation{}", input);
                    RecoveryEntity recoveryEntity=getRecovery(input.getCode());
                    recoveryCodeRepository.delete(recoveryEntity);
                    UserEntity user=getUser(input.getEmail());
                    user.setPassword(passwordEncoder.encode(input.getNewPassword()));
                    userRepository.save(user);
                    PasswordMailRecoverOutput output = PasswordMailRecoverOutput.builder()
                            .message("Successfully changed user password")
                            .build();
                    log.info("End password recovery operation{}",output);
                    return output;

                }).toEither()
                .mapLeft(JwtEntityExceptionCase::handleThrowable));
    }

    private RecoveryEntity getRecovery(String code){
        return recoveryCodeRepository.findByCode(code).orElseThrow(()->new EntityException("No recover code was matching in the system"));
    }

    private UserEntity getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new EntityException("No entity was found"));
    }
}
