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

    /*@BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }*/

    // Test para getAllEmployee() cuando existen registros
    @Test
    public void testGetAllEmployee_Success() {
        // Se crean dos empleados y sus respectivos DTOs
        Employee emp1 = new Employee();
        Employee emp2 = new Employee();
        List<Employee> employees = Arrays.asList(emp1, emp2);

        EmployeeDTO dto1 = new EmployeeDTO();
        EmployeeDTO dto2 = new EmployeeDTO();

        when(employeeRepository.findAll()).thenReturn(employees);
        when(employeeMapper.employeeToEmployeeDTO(emp1)).thenReturn(dto1);
        when(employeeMapper.employeeToEmployeeDTO(emp2)).thenReturn(dto2);

        List<EmployeeDTO> result = employeeService.getAllEmployee();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    // Test para getAllEmployee() cuando la lista está vacía y debe lanzar NotFoundException
    @Test
    public void testGetAllEmployee_NotFound() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            employeeService.getAllEmployee();
        });

        // Opcional: verificar que el mensaje de error sea el esperado
        assertTrue(ex.getMessage().contains(MessageConstant.EMPLOYEES_NOT_FOUND));
    }

    // Test para deleteEmployee() con id válido
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
        // Verificamos que se haya llamado a delete() del repositorio
        verify(employeeRepository).delete(emp);
    }

    // Test para deleteEmployee() con id inválido (no existe)
    @Test
    public void testDeleteEmployee_NotFound() {
        int id = 99;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            employeeService.deleteEmployee(id);
        });

        assertTrue(ex.getMessage().contains(String.format(MessageConstant.EMPLOYEE_ID_NOT_FOUND, id)));
    }

    // Test para createEmployee() con lista válida de DTOs
    @Test
    public void testCreateEmployee_Success() {
        // Suponemos que se recibe una lista con un EmployeeDTO
        EmployeeDTO inputDTO = new EmployeeDTO();
        List<EmployeeDTO> inputList = new ArrayList<>();
        inputList.add(inputDTO);

        // Al mapear, se obtiene un objeto Employee
        Employee emp = new Employee();
        when(employeeMapper.employeeDTOToEmployee(inputDTO)).thenReturn(emp);

        // Simulamos que saveAll devuelve la lista de empleados guardados
        List<Employee> savedEmployees = Arrays.asList(emp);
        when(employeeRepository.saveAll(any(List.class))).thenReturn(savedEmployees);

        // Mappear el empleado guardado de vuelta a DTO
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
        Employee existingEmployee = new Employee(1, "Alice","", "Cruz","Camarillo",45,"H", date , EmployeePosition.CEO);
        Employee updatedEmployee = new Employee(1, "Alice","", "Cruz","Camarillo",45,"H", date , EmployeePosition.CEO);
        EmployeeDTO outputDTO = new EmployeeDTO(1, "Alice","", "Cruz","Camarillo",45,"H", "1987-09-11" , EmployeePosition.CEO);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
        when(employeeMapper.employeeDTOToEmployee(inputDTO)).thenReturn(updatedEmployee);
        when(employeeMapper.employeeToEmployeeDTO(updatedEmployee)).thenReturn(outputDTO);
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        EmployeeDTO result = employeeService.updateEmployee(inputDTO, id);

        assertNotNull(result);
        assertEquals(outputDTO, result);
    }

    // Test para updateEmployee() con id inválido (empleado no encontrado)
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

    // Test para getEmployeeById() con id válido
    @Test
    public void testGetEmployeeById_Success() throws ParseException {
        int id = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("1987-09-11");
        Employee emp = new Employee(1, "Alice","", "Cruz","Camarillo",45,"H", date , EmployeePosition.CEO);
        EmployeeDTO dto = new EmployeeDTO(1, "Alice","", "Cruz","Camarillo",45,"H", "1987-09-11", EmployeePosition.CEO);

        when(this.employeeRepository.findById(anyInt())).thenReturn(Optional.of(emp));
        when(employeeMapper.employeeToEmployeeDTO(any(Employee.class))).thenReturn(dto);
        when(employeeMapperImpl.employeeToEmployeeDTO(any(Employee.class))).thenReturn(dto);

        EmployeeDTO result = employeeService.getEmployeeById(id);

        assertNotNull(result);
        assertEquals(dto, result);
    }

    // Test para getEmployeeById() con id inválido (no encontrado)
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
