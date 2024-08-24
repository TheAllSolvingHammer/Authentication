package com.tinqinacademy.authentication.core.families.casehandlers;

import com.tinqinacademy.authentication.api.exceptions.EntityException;
import com.tinqinacademy.authentication.api.exceptions.ErrorsProcessor;
import com.tinqinacademy.authentication.api.exceptions.InputException;
import com.tinqinacademy.authentication.api.exceptions.TokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.vavr.API;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

@Slf4j
public class JwtEntityExceptionCase {
    public static ErrorsProcessor handleThrowable(Throwable throwable) {
        return API.Match(throwable).of(
                Case($(instanceOf(TokenException.class)), e -> {
                    log.error("Invalid token: {}", e.getMessage());
                    return ErrorsProcessor.builder()
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build();
                }),
                Case($(instanceOf(InputException.class)), e -> {
                    log.error("Invalid input: {}", e.getMessage());
                    return ErrorsProcessor.builder()
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build();
                }),
                Case($(instanceOf(IllegalArgumentException.class)), e -> {
                    log.error("Illegal arguments: {}", e.getMessage());
                    return ErrorsProcessor.builder()
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build();
                }),
                Case($(instanceOf(ExpiredJwtException.class)), e -> {
                    log.error("Token expired: {}", e.getMessage());
                    return ErrorsProcessor.builder()
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build();
                }),
                Case($(instanceOf(EntityException.class)), e -> {
                    log.error("Entity error: {}", e.getMessage());
                    return ErrorsProcessor.builder()
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build();
                })


        );
    }
}