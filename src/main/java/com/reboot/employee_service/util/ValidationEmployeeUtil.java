package com.reboot.employee_service.util;

import com.reboot.employee_service.dto.EmployeeDTO;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.reboot.employee_service.util.MessageConstant.*;

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
            errors.add(EMPLOYEE_IS_NULL);
        }

        if (employee != null && (employee.getEmployeeName() == null || employee.getEmployeeName().isEmpty())) {
            errors.add(EMPLOYEE_NAME_IS_EMPTY);
        }

        if (employee != null && (employee.getEmployeeLastName() == null || employee.getEmployeeLastName().isEmpty())) {
            errors.add(EMPLOYEE_LAST_NAME_IS_EMPTY);
        }

        if (employee != null && (employee.getEmployeeMotherSurName() == null || employee.getEmployeeMotherSurName().isEmpty())) {
            errors.add(EMPLOYEE_MOTHER_SUR__NAME_IS_EMPTY);
        }

        if (employee != null && (employee.getEmployeeAge() == null || employee.getEmployeeAge() < 0)) {
            errors.add(EMPLOYEE_AGE_IS_EMPTY);
        }

        if (employee != null && (employee.getEmployeeGender() == null || employee.getEmployeeGender().isEmpty())) {
            errors.add(EMPLOYEE_GENDER_IS_EMPTY);
        }

        if (employee != null && employee.getEmployeeBirthDate() == null) {
            errors.add(EMPLOYEE_BIRTHDATE_IS_EMPTY);
        }

        if (employee != null && employee.getPosition() == null) {
            errors.add(EMPLOYEE_POSITION_IS_EMPTY);
        }

        if (!errors.isEmpty()) {
            errorMap.put(i, errors);
        }

    }
}
