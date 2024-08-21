package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.EntityException;
import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.model.recovery.EmailRecoveryInput;
import com.tinqinacademy.authentication.api.model.recovery.EmailRecoveryOperation;
import com.tinqinacademy.authentication.api.model.recovery.EmailRecoveryOutput;
import com.tinqinacademy.authentication.core.coms.EmailService;
import com.tinqinacademy.authentication.core.families.casehandlers.InputQueryEntityExceptionCase;
import com.tinqinacademy.authentication.persistence.entities.RecoveryEntity;
import com.tinqinacademy.authentication.persistence.entities.UserEntity;
import com.tinqinacademy.authentication.persistence.repositories.RecoveryCodeRepository;
import com.tinqinacademy.authentication.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class EmailRecoveryProcessor extends BaseProcessor implements EmailRecoveryOperation {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final RecoveryCodeRepository recoveryCodeRepository;

    public EmailRecoveryProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper, UserRepository userRepository, EmailService emailService, RecoveryCodeRepository recoveryCodeRepository) {
        super(validator, conversionService, errorMapper);
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.recoveryCodeRepository = recoveryCodeRepository;
    }

    @Override
    public Either<ErrorsProcessor, EmailRecoveryOutput> process(EmailRecoveryInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
            log.info("Started email recovery operation {}",input);
            checkUser(input.getEmail());
            String code=generateCode();
            RecoveryEntity recoveryEntity=RecoveryEntity.builder()
                    .email(input.getEmail())
                    .code(code)
                    .createdAt(LocalDate.now())
                    .build();
            recoveryCodeRepository.save(recoveryEntity);
            emailService.emailRecovery(input.getEmail(),code);
            EmailRecoveryOutput recoveryOutput = EmailRecoveryOutput.builder()
                    .message("Send code for recovery on e-mail")
                    .build();
            log.info("End email recovery operation{}",recoveryOutput);
            return recoveryOutput;
                }).toEither()
                .mapLeft(InputQueryEntityExceptionCase::handleThrowable));
    }
    private void checkUser(String email){
        if(!userRepository.existsByEmail(email)){
            throw new EntityException("Entity not found");
        }
    }
    private String generateCode(){
        return RandomStringUtils.randomAlphanumeric(15);
    }
}
