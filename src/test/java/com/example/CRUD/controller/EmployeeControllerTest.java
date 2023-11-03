package com.example.CRUD.controller;

import com.example.CRUD.model.Employee;
import com.example.CRUD.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testControllerEndpoint() throws Exception {
        // Perform a GET request to "/employees/test"
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/employees/test"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        // Assert that the response content matches the expected "Success" string
        String responseBody = response.getContentAsString();
        assertEquals("Success", responseBody);
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(demoEmployee1());
        employees.add(demoEmployee2());

        // Mock the behavior of the employeeService to return the sample employees
        when(employeeService.getAllEmployees()).thenReturn(employees);

        // Perform a GET request to "/employees/findAll"
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Rahul"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("John"));
    }


    @Test
    public void testGetEmployeeById() throws Exception {
        Long employeeId = 1L;
        Employee employee = demoEmployee1();

        // Mock the behavior of the employeeService to return the sample employee when findById is called with the specified ID
        when(employeeService.findById(employeeId)).thenReturn(Optional.of(employee));

        // Perform a GET request to "/employees/findById" with the employee ID as a query parameter
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/findById")
                        .param("id", String.valueOf(employeeId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Rahul"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Singh"));
    }

    @Test
    public void testGetEmployeeByIdNotFound() throws Exception {
        Long nonExistentEmployeeId = 999L;

        // Mock the behavior of the employeeService to return an empty Optional (employee not found)
        when(employeeService.findById(nonExistentEmployeeId)).thenReturn(Optional.empty());

        // Perform a GET request to "/employees/findById" with the non-existent employee ID
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/findById")
                        .param("id", String.valueOf(nonExistentEmployeeId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }




    @Test
    public void testGetEmployeeByFirstName() throws Exception {
        String firstName = "Rahul";
        Employee employee = demoEmployee1();

        // Mock the behavior of the employeeService to return the sample employee when findByFirstName is called with the specified first name
        when(employeeService.findByFirstName(firstName)).thenReturn(Optional.of(employee));

        // Perform a GET request to "/employees/findByFirstName" with the first name as a query parameter
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/findByFirstName")
                        .param("firstName", firstName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Rahul"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Singh"));
    }

    @Test
    public void testGetEmployeeByFirstNameNotFound() throws Exception {
        String nonExistentFirstName = "NonExistentName";

        // Mock the behavior of the employeeService to return an empty Optional (employee not found)
        when(employeeService.findByFirstName(nonExistentFirstName)).thenReturn(Optional.empty());

        // Perform a GET request to "/employees/findByFirstName" with a non-existent first name
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/findByFirstName")
                        .param("firstName", nonExistentFirstName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testCreateEmployee() throws Exception {
        // Mock the behavior of the employeeService to return the sample employee when createEmployee is called
        when(employeeService.createEmployee(anyString(),anyString(),anyString(),anyString(),any())).thenReturn(demoEmployee1());

        // Perform a POST request to "/employees/create" with employee data as JSON
        mockMvc.perform(MockMvcRequestBuilders.post("/employees/create")
                        .param("firstName", demoEmployee1().getFirstName())
                        .param("lastName", demoEmployee1().getLastName())
                        .param("phoneNumber", demoEmployee1().getPhoneNumber())
                        .param("address", demoEmployee1().getAddress())
                        .param("dateOfBirth", demoEmployee1().getDateOfBirth().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Employee created Sucessfully with Employee Id 1"));
    }

    @Test
    public void testCreateEmployeeFailed() throws Exception {
        // Create a sample employee request data

        // Mock the behavior of the employeeService to return null, indicating a failed creation
        when(employeeService.createEmployee(anyString(),anyString(),anyString(),anyString(),any())).thenReturn(null);

        // Perform a POST request to "/employees/create" with employee data as JSON
        mockMvc.perform(MockMvcRequestBuilders.post("/employees/create")
                        .param("firstName", demoEmployee1().getFirstName())
                        .param("lastName", demoEmployee1().getLastName())
                        .param("phoneNumber", demoEmployee1().getPhoneNumber())
                        .param("address", demoEmployee1().getAddress())
                        .param("dateOfBirth", demoEmployee1().getDateOfBirth().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Failed to create New Employee"));
    }


    @Test
    public void testDeleteEmployeeNotFound() throws Exception {
        // Create a sample employee ID for deletion
        Long employeeId = 1L;

        // Mock the behavior of the employeeService to delete the employee
        when(employeeService.findById(employeeId)).thenReturn(Optional.of(new Employee()));

        // Perform a DELETE request to "/employees/delete" with the employee ID as a query parameter
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/delete")
                        .param("id", String.valueOf(employeeId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Failed to Delete Employee"));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        // Create a sample employee ID for deletion
        Long nonExistentEmployeeId = 999L;

        // Mock the behavior of the employeeService to return an empty Optional (employee not found)
        when(employeeService.findById(nonExistentEmployeeId)).thenReturn(Optional.empty());

        // Perform a DELETE request to "/employees/delete" with the non-existent employee ID
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/delete")
                        .param("id", String.valueOf(nonExistentEmployeeId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Employee deleted Successfully"));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        // Create a sample employee ID for updating
        Long existingId = 1L;
        String updatedFirstName = "UpdatedFirstName";
        String updatedLastName = "UpdatedLastName";
        String updatedPhoneNumber = "9876543210";
        String updatedAddress = "456 Updated St";
        LocalDate updatedDateOfBirth = LocalDate.of(1990, 5, 20);

        // Create a sample existing employee and updated employee
        Employee existingEmployee = new Employee(existingId, "Jignesh", "singh", "1234567890", "Main road,Raipur", LocalDate.of(1990, 5, 15));
        Employee updatedEmployee = new Employee(existingId, updatedFirstName, updatedLastName, updatedPhoneNumber, updatedAddress, updatedDateOfBirth);

        // Mock the behavior of the employeeService to return the existing employee when findById is called
        when(employeeService.findById(any())).thenReturn(Optional.of(existingEmployee));

        // Mock the behavior of the employeeService to return the updated employee when updateEmployee is called
        when(employeeService.updateEmployee(any(), any())).thenReturn(demoEmployee1());

        // Perform a PUT request to "/employees/update" with updated employee data
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/update")
                        .param("existingId", String.valueOf(existingId))
                        .param("updatedFirstName", updatedFirstName)
                        .param("updatedLastName", updatedLastName)
                        .param("updatedPhoneNumber", updatedPhoneNumber)
                        .param("updatedAddress", updatedAddress)
                        .param("updatedDateOfBirth", updatedDateOfBirth.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Employee not found"));
    }

    @Test
    public void testUpdateEmployeeNotFound() throws Exception {
        // Create a sample employee ID for updating
        Long nonExistentEmployeeId = 999L;
        String updatedFirstName = "UpdatedFirstName";
        String updatedLastName = "UpdatedLastName";
        String updatedPhoneNumber = "9876543210";
        String updatedAddress = "456 Updated St";
        LocalDate updatedDateOfBirth = LocalDate.of(1990, 5, 20);

        // Mock the behavior of the employeeService to return an empty Optional (existing employee not found)
        when(employeeService.findById(nonExistentEmployeeId)).thenReturn(Optional.empty());

        // Perform a PUT request to "/employees/update" with an existing employee not found
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/update")
                        .param("existingId", String.valueOf(nonExistentEmployeeId))
                        .param("updatedFirstName", updatedFirstName)
                        .param("updatedLastName", updatedLastName)
                        .param("updatedPhoneNumber", updatedPhoneNumber)
                        .param("updatedAddress", updatedAddress)
                        .param("updatedDateOfBirth", updatedDateOfBirth.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



    private Employee demoEmployee1(){
        Employee employee=new Employee(1L, "Rahul", "Singh", "1234567890", "Housing Main St", LocalDate.of(1990, 5, 15));
        return employee;
    }
    private Employee demoEmployee2(){
        Employee employee=new Employee(2L, "John", "Player", "9876543210", "456 Elm St", LocalDate.of(1985, 7, 20));
        return employee;
    }



}
