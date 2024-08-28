package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.model.validate.ValidateInput;
import com.tinqinacademy.authentication.api.model.validate.ValidateOperation;
import com.tinqinacademy.authentication.api.model.validate.ValidateOutput;
import com.tinqinacademy.authentication.core.util.JwtService;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ValidateProcessor extends BaseProcessor implements ValidateOperation {
    private final JwtService jwtService;
    @Autowired
    public ValidateProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper, JwtService jwtService) {
        super(validator, conversionService, errorMapper);
        this.jwtService = jwtService;
    }

    @Override
    public Either<ErrorsProcessor, ValidateOutput> process(ValidateInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(() -> {
                    log.info("Start validate operation {}", input);
                    Boolean isValid = jwtService.isTokenValid(input.getJwt());
                    ValidateOutput output = ValidateOutput.builder()
                            .isValid(isValid)
                            .build();
                    log.info("End validate operation: {}", output);
                    return output;
                }).toEither()
                .mapLeft(throwable -> ErrorsProcessor.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(throwable.getMessage())
                        .build()));
    }
}
