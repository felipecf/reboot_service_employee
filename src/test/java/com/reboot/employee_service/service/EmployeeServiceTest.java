package com.reboot.employee_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.reboot.employee_service.dto.EmployeeDTO;
import com.reboot.employee_service.exception.NotFoundException;
import com.reboot.employee_service.maping.EmployeeMapper;
import com.reboot.employee_service.maping.EmployeeMapperImpl;
import com.reboot.employee_service.model.Employee;
import com.reboot.employee_service.repository.EmployeeRepository;
import com.reboot.employee_service.util.EmployeePosition;
import com.reboot.employee_service.util.MessageConstant;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

@SpringBootTest
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @MockBean
    private EmployeeMapperImpl employeeMapperImpl;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void testGetAllEmployee_Success() {
        Employee emp1 = new Employee();
        Employee emp2 = new Employee();
        List<Employee> employees = Arrays.asList(emp1, emp2);
        int page = 0, size = 10;

        EmployeeDTO dto1 = new EmployeeDTO();
        EmployeeDTO dto2 = new EmployeeDTO();

        when(employeeRepository.findAll()).thenReturn(employees);
        when(employeeMapper.employeeToEmployeeDTO(emp1)).thenReturn(dto1);
        when(employeeMapper.employeeToEmployeeDTO(emp2)).thenReturn(dto2);

        Page<EmployeeDTO> result = employeeService.getAllEmployee(page,size);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void testGetAllEmployee_NotFound() {
        int page = 0, size = 10;
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            employeeService.getAllEmployee(page,size);
        });

        assertTrue(ex.getMessage().contains(MessageConstant.EMPLOYEES_NOT_FOUND));
    }

    @Test
    public void testDeleteEmployee_Success() {
        int id = 1;
        Employee emp = new Employee();
        EmployeeDTO dto = new EmployeeDTO();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(emp));
        when(employeeMapper.employeeToEmployeeDTO(emp)).thenReturn(dto);

        EmployeeDTO result = employeeService.deleteEmployee(id);

        assertNotNull(result);
        assertEquals(dto, result);
        verify(employeeRepository).delete(emp);
    }

    @Test
    public void testDeleteEmployee_NotFound() {
        int id = 99;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            employeeService.deleteEmployee(id);
        });

        assertTrue(ex.getMessage().contains(String.format(MessageConstant.EMPLOYEE_ID_NOT_FOUND, id)));
    }

    @Test
    public void testCreateEmployee_Success() {
        EmployeeDTO inputDTO = new EmployeeDTO();
        List<EmployeeDTO> inputList = new ArrayList<>();
        inputList.add(inputDTO);

        Employee emp = new Employee();
        when(employeeMapper.employeeDTOToEmployee(inputDTO)).thenReturn(emp);

        List<Employee> savedEmployees = Arrays.asList(emp);
        when(employeeRepository.saveAll(any(List.class))).thenReturn(savedEmployees);

        EmployeeDTO outputDTO = new EmployeeDTO();
        when(employeeMapper.employeeToEmployeeDTO(emp)).thenReturn(outputDTO);

        List<EmployeeDTO> result = employeeService.createEmployee(inputList);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(outputDTO, result.get(0));
    }

    @Test
    public void testUpdateEmployee_Success() throws ParseException {
        int id = 1;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("1987-09-11");

        EmployeeDTO inputDTO = new EmployeeDTO(1, "Alice","", "Cruz","Camarillo",45,"H", "1987-09-11" , EmployeePosition.CEO);
        Employee existingEmployee = new Employee(1, "Alice","", "Cruz","Camarillo",45,"H", date , "CEO");
        Employee updatedEmployee = new Employee(1, "Alice","", "Cruz","Camarillo",45,"H", date , "CEO");
        EmployeeDTO outputDTO = new EmployeeDTO(1, "Alice","", "Cruz","Camarillo",45,"H", "1987-09-11" , EmployeePosition.CEO);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
        when(employeeMapper.employeeDTOToEmployee(inputDTO)).thenReturn(updatedEmployee);
        when(employeeMapper.employeeToEmployeeDTO(updatedEmployee)).thenReturn(outputDTO);
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        EmployeeDTO result = employeeService.updateEmployee(inputDTO, id);

        assertNotNull(result);
        assertEquals(outputDTO, result);
    }

    @Test
    public void testUpdateEmployee_NotFound() {
        int id = 99;
        EmployeeDTO inputDTO = new EmployeeDTO();
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            employeeService.updateEmployee(inputDTO, id);
        });

        assertTrue(ex.getMessage().contains(String.format(MessageConstant.EMPLOYEE_ID_NOT_FOUND, id)));
    }

    @Test
    public void testGetEmployeeById_Success() throws ParseException {
        int id = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("1987-09-11");
        Employee emp = new Employee(1, "Alice","", "Cruz","Camarillo",45,"H", date , EmployeePosition.CEO.name());
        EmployeeDTO dto = new EmployeeDTO(1, "Alice","", "Cruz","Camarillo",45,"H", "1987-09-11", EmployeePosition.CEO);

        when(this.employeeRepository.findById(anyInt())).thenReturn(Optional.of(emp));
        when(employeeMapper.employeeToEmployeeDTO(any(Employee.class))).thenReturn(dto);
        when(employeeMapperImpl.employeeToEmployeeDTO(any(Employee.class))).thenReturn(dto);

        EmployeeDTO result = employeeService.getEmployeeById(id);

        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    public void testGetEmployeeById_NotFound() {
        int id = 99;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            employeeService.getEmployeeById(id);
        });

        assertTrue(ex.getMessage().contains(String.format(MessageConstant.EMPLOYEE_ID_NOT_FOUND, id)));
    }
}
