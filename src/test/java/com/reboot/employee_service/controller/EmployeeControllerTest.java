package com.reboot.employee_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reboot.employee_service.dto.EmployeeDTO;
import com.reboot.employee_service.service.EmployeeService;
import com.reboot.employee_service.util.EmployeePosition;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAllEmployees() throws Exception {
        int page = 0, size = 10;

        List<EmployeeDTO> employees = Arrays.asList(
                new EmployeeDTO(1, "John Doe","", "","",45,"H", "1987-11-09", EmployeePosition.CEO),
                new EmployeeDTO(2, "Jane Smith", "","","",45,"H", "1987-11-09", EmployeePosition.CEO)
        );

        Page<EmployeeDTO> employeeDTOPage = new PageImpl<>(employees);

        when(employeeService.getAllEmployee(page,size)).thenReturn(employeeDTOPage);

        mockMvc.perform(get("/employee/getAllEmployees?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEmployee_InvalidId() throws Exception {
        mockMvc.perform(delete("/employee/deleteEmployee/{id}", 0)
                        .header("Auth", "Bearer token"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"ERRORS\": \"Employee ID should be greater than 0\"}"));
    }

    @Test
    public void testDeleteEmployee_ValidId() throws Exception {
        int id = 1;
        EmployeeDTO employee = new EmployeeDTO(1, "John Doe","", "","",45,"H", "1987-11-09", EmployeePosition.CEO);
        when(employeeService.deleteEmployee(id)).thenReturn(employee);

        mockMvc.perform(delete("/employee/deleteEmployee/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateEmployee_InvalidId() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO(1, "John Dox","", "","",45,"H", "1987-11-09", EmployeePosition.CEO);
        mockMvc.perform(put("/employee/updateEmployee/{id}", 0)
                        .header("Auth", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"ERRORS\": \"Employee ID should be greater than 0\"}"));
    }

    @Test
    public void testUpdateEmployee_ValidId() throws Exception {
        int id = 1;
        EmployeeDTO inputDTO = new EmployeeDTO(1, "Jesus","", "Freud","Lin",45,"H", "1987-11-09", EmployeePosition.CEO);
        // When updateEmployee is called with inputDTO and id, it returns the updated employee DTO.
        when(employeeService.updateEmployee(any(EmployeeDTO.class), anyInt())).thenReturn(inputDTO);

        mockMvc.perform(put("/employee/updateEmployee/{id}", id)
                        .header("Auth", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateEmployee_EmptyList() throws Exception {
        List<EmployeeDTO> emptyList = Collections.emptyList();

        mockMvc.perform(post("/employee/createEmployee")
                        .header("Auth", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyList)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"ERRORS\": \"You need to provide at least one employee to be stored\"}"));
    }

    @Test
    public void testCreateEmployee_ValidList() throws Exception {
        EmployeeDTO emp1 = new EmployeeDTO(1, "Alice","", "Cruz","Camarillo",45,"H", "1987-11-09", EmployeePosition.CEO);
        EmployeeDTO emp2 = new EmployeeDTO(3, "John","", "Doe","Hilton",45,"H", "1987-11-09", EmployeePosition.CEO);
        List<EmployeeDTO> createdList = Arrays.asList(emp1, emp2);

        when(employeeService.createEmployee(anyList())).thenReturn(createdList);

        mockMvc.perform(post("/employee/createEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdList)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateEmployee_ErrorList() throws Exception {
        EmployeeDTO emp1 = new EmployeeDTO(1,"","", "Cruz","Camarillo",45,"H", "1987-11-09", EmployeePosition.CEO);

        List<EmployeeDTO> createdList = List.of(emp1);

        when(employeeService.createEmployee(anyList())).thenReturn(createdList);

        mockMvc.perform(post("/employee/createEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdList)))
                .andExpect(status().isBadRequest());
    }
}
