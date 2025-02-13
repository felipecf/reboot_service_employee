package com.reboot.employee_service.exception;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestResponseExceptionHandlerTest {

    private RestResponseExceptionHandler exceptionHandler;
    private ObjectMapper objectMapper;
    private WebRequest webRequest;

    private final String ERRORS = "{\"ERRORS\": %s}";

    @BeforeEach
    public void setup() {
        exceptionHandler = new RestResponseExceptionHandler();
        objectMapper = new ObjectMapper();
        MockHttpServletRequest request = new MockHttpServletRequest();
        webRequest = new ServletWebRequest(request);
    }

    @Test
    public void testHandlerValidation() throws JsonProcessingException {
        Map<Integer,List<String>> messages = new HashMap<>();
        messages.put(1, Arrays.asList("error1", "error2"));

        ValidationErrorException ex = new ValidationErrorException(messages);

        String expectedBody = String.format(ERRORS, objectMapper.writeValueAsString(messages));

        ResponseEntity<Object> response = exceptionHandler.handlerValidation(ex, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        HttpHeaders headers = response.getHeaders();
        assertTrue(headers.getContentType().toString().contains("application/json"));
        assertEquals(expectedBody, response.getBody());
    }

    @Test
    public void testHandlerNotFound() throws JsonProcessingException {
        NotFoundException ex = new NotFoundException("Not Found");

        String expectedBody = String.format(ERRORS, objectMapper.writeValueAsString(ex.getMessage()));

        ResponseEntity<Object> response = exceptionHandler.handlerNotFound(ex, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        HttpHeaders headers = response.getHeaders();
        assertTrue(headers.getContentType().toString().contains("application/json"));
        assertEquals(expectedBody, response.getBody());
    }

    @Test
    public void testHandleExceptionParseException() throws JsonProcessingException {
        RuntimeException ex = new RuntimeException("Parse error");
        String expectedBody = String.format(ERRORS, objectMapper.writeValueAsString(ex.getMessage()));

        ResponseEntity<Object> response = exceptionHandler.handleException(ex, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        HttpHeaders headers = response.getHeaders();
        assertTrue(headers.getContentType().toString().contains("application/json"));
        assertEquals(expectedBody, response.getBody());
    }
}
