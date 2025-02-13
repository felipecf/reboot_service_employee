package com.reboot.employee_service.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.ParseException;

import static com.reboot.employee_service.util.MessageConstant.ERRORS;

@RestControllerAdvice
@Hidden
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    Logger log = LoggerFactory.getLogger(RestResponseExceptionHandler.class);

    @ExceptionHandler({ValidationErrorException.class})
    protected ResponseEntity<Object> handlerValidation(ValidationErrorException ex, WebRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return handleExceptionInternal(ex, String.format(ERRORS, mapper.writeValueAsString(ex.getMessages())), getHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> handlerNotFound(RuntimeException ex, WebRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        log.error(ex.getMessage());
        return handleExceptionInternal(ex,String.format(ERRORS, mapper.writeValueAsString(ex.getMessage())), getHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ParseException.class})
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        log.error("ERROR",ex);
        return handleExceptionInternal(ex,String.format(ERRORS, mapper.writeValueAsString(ex.getMessage())), getHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }

}
