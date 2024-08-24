package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.EntityException;
import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.model.promote.PromotionInput;
import com.tinqinacademy.authentication.api.model.promote.PromotionOperation;
import com.tinqinacademy.authentication.api.model.promote.PromotionOutput;
import com.tinqinacademy.authentication.core.families.casehandlers.JwtEntityExceptionCase;
import com.tinqinacademy.authentication.persistence.entities.UserEntity;
import com.tinqinacademy.authentication.persistence.enums.RoleType;
import com.tinqinacademy.authentication.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PromotionProcessor extends BaseProcessor implements PromotionOperation {
    private final UserRepository userRepository;
    @Autowired
    public PromotionProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper, UserRepository userRepository) {
        super(validator, conversionService, errorMapper);
        this.userRepository = userRepository;
    }

    @Override
    public Either<ErrorsProcessor, PromotionOutput> process(PromotionInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Started give privileges operation{}",input);

                    UserEntity userEntity=userRepository.getReferenceById(input.getId());
                    checkForHigherPrivileges(userEntity);
                    userEntity.setRoleType(RoleType.USER);
                    userRepository.save(userEntity);

                    PromotionOutput output = PromotionOutput.builder()
                            .message("Successfully promoted user")
                            .build();
                    log.info("End give privileges operation{}",output);
                    return output;
                }).toEither()
                .mapLeft(JwtEntityExceptionCase::handleThrowable));
    }

    private void checkForHigherPrivileges(UserEntity userEntity) {
        if(userEntity.getRoleType()==RoleType.ADMIN){
            throw new EntityException("User already has the lowest right on the system");
        }
    }
}
