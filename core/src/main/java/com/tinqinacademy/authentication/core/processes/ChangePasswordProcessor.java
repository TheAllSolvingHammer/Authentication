package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.model.change.ChangePasswordInput;
import com.tinqinacademy.authentication.api.model.change.ChangePasswordOperation;
import com.tinqinacademy.authentication.api.model.change.ChangePasswordOutput;
import io.vavr.control.Either;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChangePasswordProcessor extends BaseProcessor implements ChangePasswordOperation {


    public ChangePasswordProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper) {
        super(validator, conversionService, errorMapper);
    }

    @Override
    public Either<ErrorsProcessor, ChangePasswordOutput> process(ChangePasswordInput input) {
        return null;
    }
}
