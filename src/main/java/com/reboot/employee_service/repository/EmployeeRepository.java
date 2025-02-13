package com.reboot.employee_service.repository;

import com.reboot.employee_service.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    /*
    * We can use personalized queries and pass data as an input, like this
    * */

    @Query("SELECT e.id, e.age, e.position FROM Employee e WHERE e.age > :age")
    List<Employee> findEmployeeGreaterThan(int age);
}
