package com.reboot.employee_service.util;

import com.reboot.employee_service.dto.EmployeeDTO;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ValidationEmployeeUtil {

    public static Map<Integer, List<String>> validateAddEmployeesRequest(List<EmployeeDTO> employeeDTOList) {

        Map<Integer, List<String>> errorMap = new HashMap<>();
        AtomicInteger i = new AtomicInteger();

        employeeDTOList.stream().forEach(employeeDTO -> {
            List<String> errors = new ArrayList<>();
            validateEmployee(employeeDTO, errors, i.get(), errorMap);
            i.getAndIncrement();
        });
        return errorMap;
    }

    private static void validateEmployee(EmployeeDTO employee, List<String> errors, int i, Map<Integer, List<String>> errorMap) {
        if (employee == null) {
            errors.add("Employee is null");
        }

        if (employee != null && (employee.getEmployeeName() == null || employee.getEmployeeName().isEmpty())) {
            errors.add("Employee name is empty, please enter a valid name");
        }

        if (employee != null && (employee.getEmployeeLastName() == null || employee.getEmployeeLastName().isEmpty())) {
            errors.add("Employee last name is empty, please enter a valid last name");
        }

        if (employee != null && (employee.getEmployeeMotherSurName() == null || employee.getEmployeeMotherSurName().isEmpty())) {
            errors.add("Employee mother surname is empty, please enter a valid mother surname");
        }

        if (employee != null && (employee.getEmployeeAge() == null || employee.getEmployeeAge() < 0)) {
            errors.add("Employee age is empty, please enter a valid age");
        }

        if (employee != null && (employee.getEmployeeGender() == null || employee.getEmployeeGender().isEmpty())) {
            errors.add("Employee gender is empty, please enter a valid gender");
        }

        if (employee != null && employee.getEmployeeBirthDate() == null) {
            errors.add("Employee birthDate is empty, please enter a valid birth date");
        }

        if (employee != null && employee.getPosition() == null) {
            errors.add("Employee position is empty, please enter a valid position");
        }

        if (!errors.isEmpty()) {
            errorMap.put(i, errors);
        }

    }
}
