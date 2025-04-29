package com.narel.spring.handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "com.narel.spring.controller.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
