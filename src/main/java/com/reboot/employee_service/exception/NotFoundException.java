package com.reboot.employee_service.exception;

public class NotFoundException extends RuntimeException {

    private String message;

    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }


}
