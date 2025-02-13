package com.reboot.employee_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reboot.employee_service.util.EmployeePosition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class EmployeeDTO {

    @Schema(hidden = true)
    private Integer employeeId;

    @Schema(example = "John", description = "Employee name" , required = true)
    private String employeeName;

    @Schema(example = "Stan", description = "Employee middle name" )
    private String employeeMiddleName;

    @Schema(example = "McCormick", description = "Employee last name" , required = true)
    private String employeeLastName;

    @Schema(example = "Lopez", description = "Employee mother surname" )
    private String employeeMotherSurName;

    @Schema(example = "23", description = "Employee age" )
    private Integer employeeAge;

    @Schema(example = "H", description = "Employee gender" )
    private String employeeGender;

    @Schema(example = "1999-11-21", description = "Date of birth of the employee" , required = true)
    @JsonFormat(pattern="yyyy-MM-dd")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String employeeBirthDate;

    @Schema(example = "INTERN or 7", description = "Employee position, you can use the FIELD or the ID" , required = true)
    private EmployeePosition position;

    public EmployeeDTO(Integer employeeId, String employeeName, String employeeMiddleName, String employeeLastName, String employeeMotherSurName, Integer employeeAge, String employeeGender, String employeeBirthDate, EmployeePosition position) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeMiddleName = employeeMiddleName;
        this.employeeLastName = employeeLastName;
        this.employeeMotherSurName = employeeMotherSurName;
        this.employeeAge = employeeAge;
        this.employeeGender = employeeGender;
        this.employeeBirthDate = employeeBirthDate;
        this.position = position;
    }

    public EmployeeDTO() {

    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeMiddleName() {
        return employeeMiddleName;
    }

    public void setEmployeeMiddleName(String employeeMiddleName) {
        this.employeeMiddleName = employeeMiddleName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getEmployeeMotherSurName() {
        return employeeMotherSurName;
    }

    public void setEmployeeMotherSurName(String employeeMotherSurName) {
        this.employeeMotherSurName = employeeMotherSurName;
    }

    public Integer getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(Integer employeeAge) {
        this.employeeAge = employeeAge;
    }

    public String getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(String employeeGender) {
        this.employeeGender = employeeGender;
    }

    public String getEmployeeBirthDate() {
        return employeeBirthDate;
    }

    public void setEmployeeBirthDate(String employeeBirthDate) {
        this.employeeBirthDate = employeeBirthDate;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDTO that = (EmployeeDTO) o;
        return Objects.equals(employeeId, that.employeeId) && Objects.equals(employeeName, that.employeeName) && Objects.equals(employeeMiddleName, that.employeeMiddleName) && Objects.equals(employeeLastName, that.employeeLastName) && Objects.equals(employeeMotherSurName, that.employeeMotherSurName) && Objects.equals(employeeAge, that.employeeAge) && Objects.equals(employeeGender, that.employeeGender) && Objects.equals(employeeBirthDate, that.employeeBirthDate) && position == that.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, employeeName, employeeMiddleName, employeeLastName, employeeMotherSurName, employeeAge, employeeGender, employeeBirthDate, position);
    }
}
