package com.example.CRUD.service;

import com.example.CRUD.model.Employee;
import com.example.CRUD.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> allEmployees=employeeRepository.findAll();
        return allEmployees;
    }


    @Override
    public Optional<Employee> findByFirstName(String firstName) {
        Optional<Employee>employee=employeeRepository.findByFirstName(firstName);
        return employee;

    }

    @Override
    public Optional<Employee> findById(Long id) {
        Optional<Employee>employee=employeeRepository.findById(id);
        return employee;
    }

    @Override
    public Employee createEmployee(String firstName, String lastName, String phoneNumber, String address, LocalDate dateOfBirth) {
        Employee newEmployee=new Employee();
        newEmployee.setFirstName(firstName);
        newEmployee.setLastName(lastName);
        newEmployee.setPhoneNumber(phoneNumber);
        newEmployee.setAddress(address);
        newEmployee.setDateOfBirth(dateOfBirth);
        Employee emp= employeeRepository.save(newEmployee);
        return emp;

    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee, Employee existingEmployee){
        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setDateOfBirth(updatedEmployee.getDateOfBirth());
        return employeeRepository.save(existingEmployee);

    }

    @Override
    public void deleteEmployee(Long id) {
        Employee deleteEmployee=employeeRepository.getById(id);
        employeeRepository.delete(deleteEmployee);
    }
}
