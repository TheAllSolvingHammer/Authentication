package com.tinqinacademy.authentication.core.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Around("execution(* (@org.springframework.stereotype.Service *).*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecution(joinPoint);
    }

    @Around("execution(* (@com.tinqinacademy.authentication.core.aspect.LogExecution *).*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logExecution(joinPoint);
    }
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String inputArgs= Arrays.toString(joinPoint.getArgs());
        String className=joinPoint.getSignature().getDeclaringTypeName();
        log.info("Starting execution \"{}\", in class \"{}\", with args \"{}\" ", methodName,className,inputArgs);

        Object result = joinPoint.proceed();

        log.info("Finished execution method \"{}\", in class\"{}\", with result \"{}\" ", methodName,className,result);

        return result;
    }
}
