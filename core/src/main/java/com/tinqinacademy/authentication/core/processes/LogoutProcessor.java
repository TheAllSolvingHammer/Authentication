package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.EntityException;
import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.model.logout.LogoutInput;
import com.tinqinacademy.authentication.api.model.logout.LogoutOperation;
import com.tinqinacademy.authentication.api.model.logout.LogoutOutput;
import com.tinqinacademy.authentication.core.families.casehandlers.JwtEntityExceptionCase;
import com.tinqinacademy.authentication.persistence.entities.BlackListedEntity;
import com.tinqinacademy.authentication.persistence.entities.UserEntity;
import com.tinqinacademy.authentication.persistence.repositories.BlackListedRepository;
import com.tinqinacademy.authentication.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class LogoutProcessor extends BaseProcessor implements LogoutOperation {
    private final BlackListedRepository blackListedRepository;
    private final UserRepository userRepository;
    @Autowired
    public LogoutProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper, BlackListedRepository blackListedRepository, UserRepository userRepository) {
        super(validator, conversionService, errorMapper);
        this.blackListedRepository = blackListedRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Either<ErrorsProcessor, LogoutOutput> process(LogoutInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Started logout operation{}",input);
                    checkLoggedOut(input);
                    UserEntity user = checkUser(input.getId());
                    BlackListedEntity entity = BlackListedEntity.builder()
                            .email(user.getEmail())
                            .jwt(input.getJwt())
                            .build();
                    blackListedRepository.save(entity);
                    LogoutOutput logoutOutput = LogoutOutput.builder()
                            .message("Logged out user")
                            .build();
                    log.info("Ended logout operation{}",logoutOutput);
                    return logoutOutput;
                }).toEither()
                .mapLeft(JwtEntityExceptionCase::handleThrowable));
    }

    private void checkLoggedOut(LogoutInput input) {
        if(blackListedRepository.existsByJwt(input.getJwt())){
            throw new EntityException("User has already logged out");
        }
    }

    private UserEntity checkUser(UUID id){
        return userRepository.findById(id).orElseThrow(()-> new EntityException("Entity was not found"));
    }
}
