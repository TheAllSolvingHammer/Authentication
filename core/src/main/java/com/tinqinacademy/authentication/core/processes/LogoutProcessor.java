package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.model.logout.LogoutInput;
import com.tinqinacademy.authentication.api.model.logout.LogoutOperation;
import com.tinqinacademy.authentication.api.model.logout.LogoutOutput;
import io.vavr.control.Either;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogoutProcessor extends BaseProcessor implements LogoutOperation {
    public LogoutProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper) {
        super(validator, conversionService, errorMapper);
    }

    @Override
    public Either<ErrorsProcessor, LogoutOutput> process(LogoutInput input) {
        return null;
    }
}
