package com.reboot.employee_service.service;

import com.reboot.employee_service.dto.EmployeeDTO;
import com.reboot.employee_service.exception.NotFoundException;
import com.reboot.employee_service.maping.EmployeeMapper;
import com.reboot.employee_service.maping.EmployeeMapperImpl;
import com.reboot.employee_service.model.Employee;
import com.reboot.employee_service.repository.EmployeeRepository;
import com.reboot.employee_service.util.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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

    /**
     * Retrieve all the employees on the DB , it uses pagination to retrieve chunks of 10 rows by page
     * @param page: the page number
     * @param size: the number of elements on the page
     * */
    public Page<EmployeeDTO> getAllEmployee(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));

        Page<Employee> employeePage = employeeRepository.findAll(pageRequest);

        //List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();


        if(employeePage.getTotalElements() > 0 ){

            for (Employee employee : employeePage) {
                employeeDTOs.add(employeeMapper.employeeToEmployeeDTO(employee));
            }
            return new PageImpl<>(employeeDTOs, pageRequest, employeePage.getTotalElements());
        }else{
            throw new NotFoundException(MessageConstant.EMPLOYEES_NOT_FOUND);
        }
    }

    /**
     * Deletes an employee based on the id
     * @param id: the identifier of the employee, it should be greater than zero
     * */
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

    /**
     * The method receive a list of dtos and store in the db
     * @param employeeDTOs: A list of employees. At least it should contain one DTO
     * */
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

    /**
     * Updates a record based on the id and the DTO information
     * @param employeeDTO: The DTO with the information to be updated
     * @param id: The identifier of the record to be updated
     * */
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

    /**
     * Get an employee by id
     * (API not implementedd)
     * @param id: the id of the employee to be search
     * */
    public EmployeeDTO getEmployeeById(Integer id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            return employeeMapper.employeeToEmployeeDTO(employeeOptional.get());
        } else {
            throw new NotFoundException(String.format(MessageConstant.EMPLOYEE_ID_NOT_FOUND, id));
        }
    }


}
