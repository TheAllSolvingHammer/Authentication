package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.model.remove.RemovePrivilegesInput;
import com.tinqinacademy.authentication.api.model.remove.RemovePrivilegesOperation;
import com.tinqinacademy.authentication.api.model.remove.RemovePrivilegesOutput;
import io.vavr.control.Either;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class DemoteProcessor extends BaseProcessor implements RemovePrivilegesOperation {


    public DemoteProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper) {
        super(validator, conversionService, errorMapper);
    }

    @Override
    public Either<ErrorsProcessor, RemovePrivilegesOutput> process(RemovePrivilegesInput input) {
        return null;
    }
}
