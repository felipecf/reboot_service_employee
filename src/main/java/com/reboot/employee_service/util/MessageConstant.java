package com.reboot.employee_service.util;

public class MessageConstant {

    public static final String EMPLOYEES_NOT_FOUND = "Employees Not Found";
    public static final String EMPLOYEE_ID_NOT_FOUND = "Employee ID: %s Not Found";
    public static final String EMPLOYEE_NOT_FOUND = "Employee Not Found";
    public static final String ERRORS = "{\"ERRORS\": %s}";

    public static final String EMPLOYEE_ID_GT_ZERO = "Employee ID should be greater than 0";
    public static final String EMPLOYEE_ID_DELETED = "Employee ID: %s has been deleted";
    public static final String EMPLOYEE_ID_UPDATED = "Employee ID: %s has been updated";
    public static final String EMPLOYEE_POST_BAD_REQUEST = "You need to provide at least one employee to be stored";


    public static final String API_GET_ALL_EMPLOYEES_CALL = "/getAllEmployees has been called";
    public static final String API_DELETE_EMPLOYEE_CALL = "/deleteEmployee has been called";
    public static final String API_PUT_EMPLOYEE_CALL = "/updateEmployee has been called";
    public static final String API_POST_EMPLOYEE_CALL = "/createEmployee has been called";
}
