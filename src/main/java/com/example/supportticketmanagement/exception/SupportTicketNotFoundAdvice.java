package com.example.supportticketmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class SupportTicketNotFoundAdvice {

    @ExceptionHandler(SupportTicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String supportTicketNotFoundHandler(SupportTicketNotFoundException ex) {
        return ex.getMessage();
    }
}