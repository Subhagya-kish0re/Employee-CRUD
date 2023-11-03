package com.example.CRUD.controller;

import com.example.CRUD.model.Employee;
import com.example.CRUD.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/test")
    public String testController(){
        return "Success";
    }

    /* Api to get all the employees */
    @GetMapping("/findAll")
    public ResponseEntity<List<Employee>>getAllEmployees(){
        List<Employee> allEmployees=employeeService.getAllEmployees();
            if(allEmployees.isEmpty()){
                return null;
            }else {
                return ResponseEntity.ok(allEmployees);
            }
    }

    /* Api to get employee using employee's id */
    @GetMapping("/findById")
    public ResponseEntity<Employee>getById(@RequestParam Long id){
        Optional<Employee>optionalEmployee=employeeService.findById(id);
        if(optionalEmployee.isPresent()){
            return ResponseEntity.ok(optionalEmployee.get());
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /* Api to get employee using employee's FirstName */
    @GetMapping("/findByFirstName")
    public ResponseEntity<Employee>getByFirstname(@RequestParam String firstName){
        Optional<Employee>employee=employeeService.findByFirstName(firstName);
        if(employee.isPresent()){
            return ResponseEntity.ok(employee.get());
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    /* Api to create new employee */
    @PostMapping("/create")
    public ResponseEntity<String>createEmployee(@Valid @RequestParam String firstName,@RequestParam String lastName,@RequestParam String phoneNumber,@RequestParam String address,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth){
        Employee newEmployee=employeeService.createEmployee(firstName,lastName,phoneNumber,address,dateOfBirth);
        if(newEmployee!=null){
            return ResponseEntity.ok("Employee created Sucessfully with Employee Id "+newEmployee.getEmpId());
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create New Employee");
        }

    }

    /* Api to get delete existing employee by using employee Id */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEmployee(@Valid @RequestParam Long id) {
        employeeService.deleteEmployee(id);
        Optional<Employee> checkEmployee=employeeService.findById(id);
        if(checkEmployee.isPresent()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Delete Employee");
        }else{
            return ResponseEntity.ok("Employee deleted Successfully");
        }
    }

    /* Api to Update Existing  employee */
    @PutMapping("/update")
    public ResponseEntity<String>updateExistingEmployee( @Valid @RequestParam Long existingId,@RequestParam String updatedFirstName,@RequestParam String updatedLastName,@RequestParam String updatedPhoneNumber,@RequestParam String updatedAddress,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate updatedDateOfBirth){
        Optional<Employee> existingEmployee = employeeService.findById(existingId);

        if (!existingEmployee.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Existing Employee not found");
        }

        Employee updateEmployee=new Employee();
        updateEmployee.setFirstName(updatedFirstName);
        updateEmployee.setLastName(updatedLastName);
        updateEmployee.setPhoneNumber(updatedPhoneNumber);
        updateEmployee.setAddress(updatedAddress);
        updateEmployee.setDateOfBirth(updatedDateOfBirth);

            Employee newUpdatedEmployee= employeeService.updateEmployee(updateEmployee,existingEmployee.get());
            if(newUpdatedEmployee.getFirstName()==updatedFirstName){
                return ResponseEntity.ok("Employee updated successfully");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            }

    }

}
