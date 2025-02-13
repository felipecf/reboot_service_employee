package com.reboot.employee_service.maping;

import com.reboot.employee_service.dto.EmployeeDTO;
import com.reboot.employee_service.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper( componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {


    @Mapping( target = "employeeId" , source = "id" )
    @Mapping( target = "employeeName" , source = "name" )
    @Mapping( target = "employeeMiddleName" , source = "middleName" )
    @Mapping( target = "employeeLastName" , source = "lastName" )
    @Mapping( target = "employeeMotherSurName" , source = "motherSurName" )
    @Mapping( target = "employeeAge" , source = "age" )
    @Mapping( target = "employeeGender" , source = "gender" )
    @Mapping( target = "employeeBirthDate" , source = "birthDate", dateFormat = "yyyy-MM-dd")
    @Mapping( target = "position" , source = "position" )
    EmployeeDTO employeeToEmployeeDTO(Employee entity);


    @Mapping( source = "employeeDTO.employeeId" , target = "id" )
    @Mapping( source = "employeeDTO.employeeName" , target = "name" )
    @Mapping( source = "employeeDTO.employeeMiddleName" , target = "middleName" )
    @Mapping( source = "employeeDTO.employeeLastName" , target = "lastName" )
    @Mapping( source = "employeeDTO.employeeMotherSurName" , target = "motherSurName" )
    @Mapping( source = "employeeDTO.employeeAge" , target = "age" )
    @Mapping( source = "employeeDTO.employeeGender" , target = "gender" )
    @Mapping( source = "employeeDTO.employeeBirthDate" , target = "birthDate" ,  dateFormat = "yyyy-MM-dd" )
    @Mapping( source = "employeeDTO.position" , target = "position" )
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);

}
