package com.tinqinacademy.authentication.core.processes;

import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.model.promote.PromotionInput;
import com.tinqinacademy.authentication.api.model.promote.PromotionOperation;
import com.tinqinacademy.authentication.api.model.promote.PromotionOutput;
import io.vavr.control.Either;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PromotionProcessor extends BaseProcessor implements PromotionOperation {


    public PromotionProcessor(Validator validator, ConversionService conversionService, ErrorsProcessor errorMapper) {
        super(validator, conversionService, errorMapper);
    }

    @Override
    public Either<ErrorsProcessor, PromotionOutput> process(PromotionInput input) {
        return null;
    }
}
