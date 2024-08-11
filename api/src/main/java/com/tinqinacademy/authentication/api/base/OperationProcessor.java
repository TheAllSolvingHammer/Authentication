package com.tinqinacademy.authentication.api.base;

import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import io.vavr.control.Either;

public interface OperationProcessor<T extends OperationOutput,E extends OperationInput> {
    Either<ErrorsProcessor,T> process(E input);
}
