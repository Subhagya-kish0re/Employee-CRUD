package com.example.CRUD.service;

import com.example.CRUD.model.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee>getAllEmployees();

    Optional<Employee> findByFirstName(String firstName);
    Optional<Employee> findById(Long id);
    Employee createEmployee(String firstName, String lastName, String phoneNumber, String address, LocalDate dateOfBirth);
//    void updateEmployee(String firstName, String lastName, String phoneNumber, String address, LocalDate dateOfBirth,Employee existingEmployee);

    void deleteEmployee(Long id);

    Employee updateEmployee(Employee updatedEmployee, Employee existingEmployee);
}
