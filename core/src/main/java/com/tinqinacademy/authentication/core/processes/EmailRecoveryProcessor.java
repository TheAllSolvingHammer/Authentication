package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.model.recovery.EmailRecoveryInput;
import com.tinqinacademy.authentication.api.model.recovery.EmailRecoveryOperation;
import com.tinqinacademy.authentication.api.model.recovery.EmailRecoveryOutput;
import io.vavr.control.Either;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailRecoveryProcessor extends BaseProcessor implements EmailRecoveryOperation {


    public EmailRecoveryProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper) {
        super(validator, conversionService, errorMapper);
    }

    @Override
    public Either<ErrorsProcessor, EmailRecoveryOutput> process(EmailRecoveryInput input) {
        return null;
    }
}
