package com.reboot.employee_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reboot.employee_service.dto.EmployeeDTO;
import com.reboot.employee_service.exception.ValidationErrorException;
import com.reboot.employee_service.service.EmployeeService;
import com.reboot.employee_service.util.ValidationEmployeeUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.reboot.employee_service.util.MessageConstant.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/getAllEmployees")
    public ResponseEntity<Object> getAllEmployees(@RequestHeader Map<String, String> headersMap) throws JsonProcessingException {
        log.info(API_GET_ALL_EMPLOYEES_CALL);

        ObjectMapper mapper = new ObjectMapper();
        log.info(mapper.writeValueAsString(headersMap));


        List<EmployeeDTO> employeeList = employeeService.getAllEmployee();

        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable int id, @RequestHeader Map<String, String> headersMap) throws JsonProcessingException {
        log.info(API_DELETE_EMPLOYEE_CALL);
        ObjectMapper mapper = new ObjectMapper();
        log.info(mapper.writeValueAsString(headersMap));

        ResponseEntity<Object> response;

        if (id <= BigInteger.ZERO.intValue()) {
            response = new ResponseEntity<>(EMPLOYEE_ID_GT_ZERO, HttpStatus.BAD_REQUEST);
        } else {
            EmployeeDTO employeeDTO = employeeService.deleteEmployee(id);
            log.info(String.format(EMPLOYEE_ID_DELETED, id));
            response = new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        }

        return response;
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable int id, @RequestHeader Map<String, String> headersMap) throws JsonProcessingException {
        log.info(API_PUT_EMPLOYEE_CALL);
        ObjectMapper mapper = new ObjectMapper();
        log.info(mapper.writeValueAsString(headersMap));

        ResponseEntity<Object> response;

        if (id > BigInteger.ZERO.intValue()) {
            EmployeeDTO employeeUpdatedDTO = employeeService.updateEmployee(employeeDTO, id);
            log.info(String.format(EMPLOYEE_ID_UPDATED,id));
            response = new ResponseEntity<>(employeeUpdatedDTO, HttpStatus.OK);

        } else {
            response = new ResponseEntity<>(EMPLOYEE_ID_GT_ZERO, HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @PostMapping("/createEmployee")
    public ResponseEntity<Object> createEmployee(@RequestBody List<EmployeeDTO> employeeDTOs, @RequestHeader Map<String, String> headersMap) throws JsonProcessingException {
        log.info(API_POST_EMPLOYEE_CALL);

        ObjectMapper mapper = new ObjectMapper();
        log.info(mapper.writeValueAsString(headersMap));

        ResponseEntity<Object> response = null;

        if (!employeeDTOs.isEmpty()) {

            List<String> errorList = ValidationEmployeeUtil.validateAddEmployeesRequest(employeeDTOs);

            if (!errorList.isEmpty()) {
                throw new ValidationErrorException(errorList);
            }

            List<EmployeeDTO> employeeDTOSCreated = employeeService.createEmployee(employeeDTOs);

            if (!employeeDTOSCreated.isEmpty()) {
                response = new ResponseEntity<>(employeeDTOSCreated, HttpStatus.CREATED);
            }
        } else {
            response = new ResponseEntity<>(EMPLOYEE_POST_BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}
