package com.reboot.employee_service.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationErrorException extends RuntimeException{

    private Map<Integer,List<String>> messages = new HashMap<>();

    public Map<Integer,List<String>> getMessages() {
        return messages;
    }

    public ValidationErrorException(Map<Integer,List<String>> messages) {
        this.messages = messages;
    }

}
