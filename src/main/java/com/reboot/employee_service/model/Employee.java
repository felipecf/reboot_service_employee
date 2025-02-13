package com.reboot.employee_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reboot.employee_service.util.EmployeePosition;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String middleName;
    private String lastName;
    private String motherSurName;
    private Integer age;
    private String gender;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private Date birthDate;
    private EmployeePosition position;

    public Employee(Integer id, String name, String middleName, String lastName, String motherSurName, Integer age, String gender, Date birthDate, EmployeePosition position) {
        this.id = id;
        this.name = name;
        this.middleName = middleName;
        this.lastName = lastName;
        this.motherSurName = motherSurName;
        this.age = age;
        this.gender = gender;
        this.birthDate = birthDate;
        this.position = position;
    }

    public Employee(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMotherSurName() {
        return motherSurName;
    }

    public void setMotherSurName(String motherSurName) {
        this.motherSurName = motherSurName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }
}
