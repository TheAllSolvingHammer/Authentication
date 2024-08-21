package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.EntityException;
import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.model.activate.ActivateInput;
import com.tinqinacademy.authentication.api.model.activate.ActivateOperation;
import com.tinqinacademy.authentication.api.model.activate.ActivateOutput;
import com.tinqinacademy.authentication.core.families.casehandlers.InputQueryEntityExceptionCase;
import com.tinqinacademy.authentication.persistence.entities.ActivateCodeEntity;
import com.tinqinacademy.authentication.persistence.entities.UserEntity;
import com.tinqinacademy.authentication.persistence.repositories.ActivateCodeRepository;
import com.tinqinacademy.authentication.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivateProcessor extends BaseProcessor implements ActivateOperation {

    private final ActivateCodeRepository activateCodeRepository;
    private final UserRepository userRepository;

    public ActivateProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper, ActivateCodeRepository activateCodeRepository, UserRepository userRepository) {
        super(validator, conversionService, errorMapper);
        this.activateCodeRepository = activateCodeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Either<ErrorsProcessor, ActivateOutput> process(ActivateInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                log.info("Start activating operation{}",input);
                ActivateCodeEntity activateCodeEntity = checkConfirmation(input.getConfirmationCode());
                UserEntity user=checkUser(activateCodeEntity.getEmail());
                user.setVerified(true);
                userRepository.save(user);
                activateCodeRepository.deleteById(activateCodeEntity.getUuid());
                ActivateOutput activateOutput = ActivateOutput.builder()
                        .message("User is activated")
                        .build();
                log.info("End activation operation{}",activateOutput);
                return activateOutput;

                }).toEither()
                .mapLeft(InputQueryEntityExceptionCase::handleThrowable));
    }

    private ActivateCodeEntity checkConfirmation(String code){
        return activateCodeRepository.findByCode(code)
                .orElseThrow(() -> new EntityException("No account with this code was found!"));
    }

    private UserEntity checkUser(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()->new EntityException("No user account exist with this email. If this appears it mean the database was altered."));
    }
}
