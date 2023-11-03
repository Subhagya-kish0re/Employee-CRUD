package com.example.CRUD.service;

import com.example.CRUD.model.Employee;
import com.example.CRUD.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class EmployeeServiceImplTest {

    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        employees.add(new Employee());

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());
    }

    @Test
    void testFindByFirstName() {
        Employee employee = new Employee();
        employee.setFirstName("Rakesh");

        when(employeeRepository.findByFirstName(anyString())).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.findByFirstName("Rakesh");

        assertTrue(result.isPresent());
        assertEquals("Rakesh", result.get().getFirstName());
    }

    @Test
    void testFindById() {
        Employee employee = new Employee();
        employee.setEmpId(1L);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getEmpId());
    }

//    @Test
//    void testCreateEmployee() {
//        String firstName = "Raj";
//        String lastName = "Deb";
//        String phoneNumber = "3214668899";
//        String address = "Jamshedpur";
//        LocalDate dateOfBirth = LocalDate.of(1990, 5, 15);
//
//        Employee newEmployee = employeeService.createEmployee(firstName, lastName, phoneNumber, address, dateOfBirth);
//
//        assertEquals(firstName, newEmployee.getFirstName());
//        assertEquals(lastName, newEmployee.getLastName());
//        assertEquals(phoneNumber, newEmployee.getPhoneNumber());
//        assertEquals(address, newEmployee.getAddress());
//        assertEquals(dateOfBirth, newEmployee.getDateOfBirth());
//    }


    @Test
    void testCreateEmployee() {
        String firstName = "Raj";
        String lastName = "Deb";
        String phoneNumber = "3214668899";
        String address = "Jamshedpur";
        LocalDate dateOfBirth = LocalDate.of(1990, 5, 15);

        Employee newEmployee = new Employee();
        newEmployee.setFirstName(firstName);
        newEmployee.setLastName(lastName);
        newEmployee.setPhoneNumber(phoneNumber);
        newEmployee.setAddress(address);
        newEmployee.setDateOfBirth(dateOfBirth);

        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(newEmployee);

        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(employeeRepository);

        Employee createdEmployee = employeeService.createEmployee(firstName, lastName, phoneNumber, address, dateOfBirth);

        // Assertions
        assertNotNull(createdEmployee);
        assertEquals(firstName, createdEmployee.getFirstName());
        assertEquals(lastName, createdEmployee.getLastName());
        assertEquals(phoneNumber, createdEmployee.getPhoneNumber());
        assertEquals(address, createdEmployee.getAddress());
        assertEquals(dateOfBirth, createdEmployee.getDateOfBirth());
    }


    @Test
    void testUpdateEmployee() {
        Employee existingEmployee = new Employee();
        existingEmployee.setFirstName("Suresh");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setFirstName("Shivam");

        when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);

        Employee result = employeeService.updateEmployee(updatedEmployee, existingEmployee);

        assertEquals("Shivam", result.getFirstName());
    }

    @Test
    void testDeleteEmployee() {
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setEmpId(employeeId);

        when(employeeRepository.getById(employeeId)).thenReturn(employee);

        assertDoesNotThrow(() -> employeeService.deleteEmployee(employeeId));
    }

}
