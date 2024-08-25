package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.EntityException;
import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.model.remove.RemovePrivilegesInput;
import com.tinqinacademy.authentication.api.model.remove.RemovePrivilegesOperation;
import com.tinqinacademy.authentication.api.model.remove.RemovePrivilegesOutput;
import com.tinqinacademy.authentication.core.families.casehandlers.InputQueryEntityExceptionCase;
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
public class DemoteProcessor extends BaseProcessor implements RemovePrivilegesOperation {
    private final UserRepository userRepository;

    @Autowired
    public DemoteProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper, UserRepository userRepository) {
        super(validator, conversionService, errorMapper);
        this.userRepository = userRepository;
    }

    @Override
    public Either<ErrorsProcessor, RemovePrivilegesOutput> process(RemovePrivilegesInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
            log.info("Started remove privileges operation{}",input);
           
            UserEntity userEntity=userRepository.getReferenceById(input.getId());
            checkForLowerPrivileges(userEntity);
            userEntity.setRoleType(RoleType.USER);
            userRepository.save(userEntity);

            RemovePrivilegesOutput output = RemovePrivilegesOutput.builder()
                    .message("Successfully demoted user")
                    .build();
            log.info("End removed privileges operation{}",output);
            return output;
                }).toEither()
                .mapLeft(InputQueryEntityExceptionCase::handleThrowable));
    }

    private void checkForLowerPrivileges(UserEntity userEntity) {
        if(userEntity.getRoleType()==RoleType.USER){
            throw new EntityException("User already has the lowest right on the system");
        }
    }
}
