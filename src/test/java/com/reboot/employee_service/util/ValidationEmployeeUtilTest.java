package com.reboot.employee_service.util;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.reboot.employee_service.dto.EmployeeDTO;
import org.junit.jupiter.api.Test;

public class ValidationEmployeeUtilTest {

    @Test
    public void testValidateAddEmployeesRequest_AllValid() throws Exception {
        EmployeeDTO emp1 = new EmployeeDTO();
        emp1.setEmployeeName("John");
        emp1.setEmployeeLastName("Doe");
        emp1.setEmployeeMotherSurName("Smith");
        emp1.setEmployeeAge(30);
        emp1.setEmployeeGender("M");
        emp1.setEmployeeBirthDate("1990-05-15");
        emp1.setPosition(EmployeePosition.CEO);

        List<EmployeeDTO> employees = Arrays.asList(emp1);

        Map<Integer, List<String>> errorMap = ValidationEmployeeUtil.validateAddEmployeesRequest(employees);

        assertTrue(errorMap.isEmpty(), "El mapa de errores debe estar vacío para empleados válidos");
    }

    @Test
    public void testValidateAddEmployeesRequest_InvalidEmployee() throws Exception {
        EmployeeDTO emp1 = new EmployeeDTO();
        emp1.setEmployeeName("");
        emp1.setEmployeeLastName(null);
        emp1.setEmployeeMotherSurName("");
        emp1.setEmployeeAge(-1);
        emp1.setEmployeeGender("");
        emp1.setEmployeeBirthDate(null);
        emp1.setPosition(null);

        List<EmployeeDTO> employees = Arrays.asList(emp1);

        Map<Integer, List<String>> errorMap = ValidationEmployeeUtil.validateAddEmployeesRequest(employees);

        assertFalse(errorMap.isEmpty(), "El mapa de errores no debe estar vacío para empleados inválidos");
        assertTrue(errorMap.containsKey(0), "Debe existir error para el empleado en índice 0");

        List<String> errors = errorMap.get(0);
        assertTrue(errors.contains("Employee name is empty, please enter a valid name"));
        assertTrue(errors.contains("Employee last name is empty, please enter a valid last name"));
        assertTrue(errors.contains("Employee mother surname is empty, please enter a valid mother surname"));
        assertTrue(errors.contains("Employee age is empty, please enter a valid age"));
        assertTrue(errors.contains("Employee gender is empty, please enter a valid gender"));
        assertTrue(errors.contains("Employee birthDate is empty, please enter a valid birth date"));
        assertTrue(errors.contains("Employee position is empty, please enter a valid position"));
    }

    @Test
    public void testValidateAddEmployeesRequest_NullEmployee() {
        List<EmployeeDTO> employees = Arrays.asList((EmployeeDTO) null);

        Map<Integer, List<String>> errorMap = ValidationEmployeeUtil.validateAddEmployeesRequest(employees);

        assertFalse(errorMap.isEmpty(), "El mapa de errores no debe estar vacío si hay un empleado nulo");
        assertTrue(errorMap.containsKey(0), "Debe existir error para el empleado nulo en el índice 0");
        List<String> errors = errorMap.get(0);
        assertTrue(errors.contains("Employee is null"));
    }

    @Test
    public void testValidateAddEmployeesRequest_MultipleEmployees() throws Exception {
        EmployeeDTO empValid = new EmployeeDTO();
        empValid.setEmployeeName("Alice");
        empValid.setEmployeeLastName("Wonderland");
        empValid.setEmployeeMotherSurName("Queen");
        empValid.setEmployeeAge(25);
        empValid.setEmployeeGender("F");
        empValid.setEmployeeBirthDate("1995-10-10");
        empValid.setPosition(EmployeePosition.SENIOR_MANAGER);

        EmployeeDTO empInvalid = new EmployeeDTO();
        empInvalid.setEmployeeName("");
        empInvalid.setEmployeeLastName("");
        empInvalid.setEmployeeMotherSurName("");
        empInvalid.setEmployeeAge(null);
        empInvalid.setEmployeeGender("");
        empInvalid.setEmployeeBirthDate(null);
        empInvalid.setPosition(null);

        List<EmployeeDTO> employees = Arrays.asList(empValid, empInvalid);

        Map<Integer, List<String>> errorMap = ValidationEmployeeUtil.validateAddEmployeesRequest(employees);

        assertFalse(errorMap.containsKey(0));
        assertTrue(errorMap.containsKey(1));
        List<String> errors = errorMap.get(1);
        assertTrue(errors.contains("Employee name is empty, please enter a valid name"));
        assertTrue(errors.contains("Employee last name is empty, please enter a valid last name"));
        assertTrue(errors.contains("Employee mother surname is empty, please enter a valid mother surname"));
    }
}
