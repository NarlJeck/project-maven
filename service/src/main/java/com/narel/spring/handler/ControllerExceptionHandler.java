package com.narel.spring.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "com.narel.spring.http.controller")
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handlerExceptions(Exception exception, HttpServletRequest request) {
        log.error("Failed to return response", exception);
        return "error/error500";
    }
}
