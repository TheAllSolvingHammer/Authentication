package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.EntityException;
import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.exceptions.InputException;
import com.tinqinacademy.authentication.api.model.register.UserRegistrationInput;
import com.tinqinacademy.authentication.api.model.register.UserRegistrationOperation;
import com.tinqinacademy.authentication.api.model.register.UserRegistrationOutput;
import com.tinqinacademy.authentication.core.coms.EmailService;
import com.tinqinacademy.authentication.core.families.casehandlers.InputQueryEntityExceptionCase;
import com.tinqinacademy.authentication.persistence.entities.ActivateCodeEntity;
import com.tinqinacademy.authentication.persistence.entities.UserEntity;
import com.tinqinacademy.authentication.persistence.enums.RoleType;
import com.tinqinacademy.authentication.persistence.repositories.ActivateCodeRepository;
import com.tinqinacademy.authentication.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class RegisterProcessor extends BaseProcessor implements UserRegistrationOperation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ActivateCodeRepository activateCodeRepository;
    private final EmailService emailService;
    @Autowired
    public RegisterProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, ActivateCodeRepository activateCodeRepository, EmailService emailService) {
        super(validator, conversionService, errorMapper);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.activateCodeRepository = activateCodeRepository;
        this.emailService = emailService;
    }


    @Override
    public Either<ErrorsProcessor, UserRegistrationOutput> process(UserRegistrationInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{

                    log.info("Start register operation {}",input);
                    checkBirthday(input.getBirthdate());
                    checkUsername(input.getUsername());
                    checkEmail(input.getEmail());
                    UserEntity user= UserEntity.builder()
                            .username(input.getUsername())
                            .password(passwordEncoder.encode(input.getPassword()))
                            .birthday(input.getBirthdate())
                            .email(input.getEmail())
                            .firstName(input.getFirstName())
                            .lastName(input.getLastName())
                            .verified(false)
                            .roleType(RoleType.USER)
                            .build();
                    UserEntity addedUser=userRepository.save(user);

                    ActivateCodeEntity codeEntity = ActivateCodeEntity.builder()
                            .code(generateCode())
                            .createdAt(LocalDate.now())
                            .email(addedUser.getEmail())
                            .build();
                    ActivateCodeEntity activateResult=activateCodeRepository.save(codeEntity);
                    emailService.emailActivation(activateResult.getEmail(),activateResult.getCode());
                    UserRegistrationOutput output = UserRegistrationOutput.builder()
                            .id(addedUser.getId())
                            .build();
                    log.info("End register operation{}",output);
                    return output;

                }).toEither()
                .mapLeft(InputQueryEntityExceptionCase::handleThrowable));
    }

    private void checkUsername(String input) {
       if(userRepository.existsByEmail(input)){
           throw new EntityException("User with this username is already registered");
       }
    }

    private void checkEmail(String input) {
        if(userRepository.existsByUsername(input)){
            throw new EntityException("User with this email is already registered");
        }
    }

    private String generateCode(){
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private void checkBirthday(LocalDate date){
        Long year= ChronoUnit.YEARS.between(date, LocalDate.now());
        if(year<18L){
            throw new InputException("User is not old enough");
        }
    }
}
