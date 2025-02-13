package com.reboot.employee_service.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorException extends RuntimeException{

    private List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public ValidationErrorException() {}

    public ValidationErrorException(List<String> messages) {
        this.messages = messages;
    }

}
