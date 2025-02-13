package com.reboot.employee_service.service;

import com.reboot.employee_service.dto.EmployeeDTO;
import com.reboot.employee_service.exception.NotFoundException;
import com.reboot.employee_service.maping.EmployeeMapper;
import com.reboot.employee_service.maping.EmployeeMapperImpl;
import com.reboot.employee_service.model.Employee;
import com.reboot.employee_service.repository.EmployeeRepository;
import com.reboot.employee_service.util.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private final EmployeeRepository employeeRepository;

    private EmployeeMapper employeeMapper = new EmployeeMapperImpl();

    public List<EmployeeDTO> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();

        if (employees.isEmpty()) {
            throw new NotFoundException(MessageConstant.EMPLOYEES_NOT_FOUND);
        } else {
            for (Employee employee : employees) {
                employeeDTOs.add(employeeMapper.employeeToEmployeeDTO(employee));
            }
            return employeeDTOs;
        }
    }

    public EmployeeDTO deleteEmployee(Integer id) {

        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        EmployeeDTO employeeDTO = null;

        if (employeeOptional.isPresent()) {

            employeeDTO = employeeMapper.employeeToEmployeeDTO(employeeOptional.get());
            employeeRepository.delete(employeeOptional.get());
            return employeeDTO;
        } else {
            throw new NotFoundException(String.format(MessageConstant.EMPLOYEE_ID_NOT_FOUND, id));
        }
    }

    public List<EmployeeDTO> createEmployee(List<EmployeeDTO> employeeDTOs) {

        List<Employee> employees = new ArrayList<>();

        employeeDTOs.parallelStream().forEach(employeeDTO -> {
            employees.add(employeeMapper.employeeDTOToEmployee(employeeDTO));
        });

        List<Employee> employeeList = employeeRepository.saveAll(employees);

        if (!employeeList.isEmpty()) {
            employeeDTOs.clear();

            employeeList.forEach(employee -> {
                employeeDTOs.add(employeeMapper.employeeToEmployeeDTO(employee));
            });
        }

        return employeeDTOs;
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, int id) {

        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        Employee employee;

        if(employeeOptional.isPresent()) {
            employeeDTO.setEmployeeId(id);
            employee = employeeRepository.save(employeeMapper.employeeDTOToEmployee(employeeDTO));
        }else{
            throw new NotFoundException(String.format(MessageConstant.EMPLOYEE_ID_NOT_FOUND, id));
        }

        return employeeMapper.employeeToEmployeeDTO(employee);
    }

    public EmployeeDTO getEmployeeById(Integer id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            return employeeMapper.employeeToEmployeeDTO(employeeOptional.get());
        } else {
            throw new NotFoundException(String.format(MessageConstant.EMPLOYEE_ID_NOT_FOUND, id));
        }
    }


}
